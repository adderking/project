package com.zxm.load;

import com.scisdata.web.bean.CarTrace;
import com.scisdata.web.bean.MacTrace;
import com.scisdata.web.bean.VideoEquipmentInfo;
import com.zxm.load.utils.*;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CarMacPairBinder {

    private static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat sdf = new SimpleDateFormat(FORMATTER);
    private RelationComputer computer = new RelationComputer();
    private Map<String, VideoEquipmentInfo> videoEquipmentInfoMap;
    private Map<String, MacTraceCache> macTraceCache;
    private String basePath;
//    private JedisTools jedis;

    public CarMacPairBinder() {
        videoEquipmentInfoMap = new HashMap<>();
        List<VideoEquipmentInfo> videoEquipmentInfos = computer.videoEquipmentInfos;
        for(VideoEquipmentInfo videoEquipmentInfo: videoEquipmentInfos) {
            videoEquipmentInfoMap.put(videoEquipmentInfo.getEquipmentId(), videoEquipmentInfo);
        }
        macTraceCache = new HashMap<>();
        this.basePath = SystemConfig.getString(Constants.MAC_TRACE_BASE_PATH);
//        this.jedis = new JedisTools();
    }

    /**
     * 方法主入口
     * @param startDate
     * @throws ParseException
     */
    public void execute(String startDate) throws ParseException {

        String sql = "SELECT primaryId, carPlateId, equipmentId, equipmentLocation, " +
                "startTime, latitude, langitude, createTime, endTime FROM cartrace " +
                " where startTime > '"+startDate+"' ORDER BY startTime asc";

        Connection conn;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtil.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CarTrace carTraceInstance = CreateInstanceUtil.createCarTraceInstance(rs);
                bindCarWithMac(carTraceInstance);
            }
            closeCache();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {
                rs.close();
                if(pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 遍历每一个采集设备，根据时间区间查询对应mac数据
     * @param carTrace
     * @throws IOException
     */
    public void bindCarWithMac(CarTrace carTrace) throws IOException {
        Map<String, DistanceRange> rangeMap =
                computer.getRanges(videoEquipmentInfoMap.get(carTrace.getEquipmentId()));
        Iterator<String> it = rangeMap.keySet().iterator();
        // for test
        if("dx00074".equals(carTrace.getEquipmentId())) {
            System.out.println("dd");
        }

        while(it.hasNext()) {
            String key = it.next();
            DistanceRange range = rangeMap.get(key);
            // 根据距离计算时间差
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(carTrace.getStartTime());
            // 因为wifi采集点有覆盖范围，由此会产生时间误差，此处对误差进行处理，设立一个时间范围
            calendar.add(Calendar.SECOND, range.getX());
            Date minTimePoint = calendar.getTime();
            calendar.add(Calendar.SECOND, -range.getX()+range.getY());
            Date maxTimePoint = calendar.getTime();
            List<MacTrace> macTraces = getMatchedMacTrace(key.split("_")[1], minTimePoint, maxTimePoint);
            bindCarWithMac(carTrace, macTraces);
        }
    }

    /**
     * 查询所有匹配的mac地址信息
     * @param equipmentId
     * @param startTime
     * @param endTime
     * @return
     * @throws IOException
     */
    private List<MacTrace> getMatchedMacTrace(String equipmentId, Date startTime, Date endTime) throws IOException {
        MacTraceCache cache = macTraceCache.get(equipmentId);
        if(cache == null) {
            cache = new MacTraceCache(equipmentId);
            macTraceCache.put(equipmentId, cache);
        }

        List<MacTrace> macTraces = null;
        try {
            macTraces = cache.getMacTraces(startTime, endTime);
//            for(MacTrace macTrace : macTraces) {
//                System.out.println(macTrace.getMacId() + " " + macTrace.getStartTime() + " " + macTrace.getEquipmentId());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return macTraces;
    }

    /**
     * 绑定车辆与mac地址信息，并存储入redis中
     * @param carTrace
     * @param macTraces
     * @throws IOException
     */
    public void bindCarWithMac(CarTrace carTrace, List<MacTrace> macTraces) throws IOException {
//        String filePath = SystemConfig.getString(Constants.MAC_CAR_PAIR_FILE_PATH);
        HashMap<String, Integer> counts = new HashMap<>();
        for(MacTrace macTrace : macTraces) {
            String key = carTrace.getCarPlateId() + "_" + macTrace.getMacId();
            if(counts.containsKey(key)) {
                counts.put(key, counts.get(key)+1);
            } else {
                counts.put(key, 1);
            }
        }
        // save
        JedisTools.addCarMacPair(counts);
    }

    private void closeCache() {
        for(MacTraceCache cache : macTraceCache.values()) {
            cache.close();
        }
    }

}
