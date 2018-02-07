package com.wsh.tools;

import com.scisdata.web.bean.*;
import com.scisdata.web.util.DateUtils;
import com.wsh.tools.utils.ConnectionUtil;
import com.wsh.tools.utils.CreateInstanceUtil;
import com.wsh.tools.utils.MapCaculateUtil;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class CarMacRelation {

    private static final int CARSPEED = 17; //单位是 m/s
    private static final String FORMATER = "yyyy-MM-dd HH:mm:ss";
    private static final int WIFIRADIUS = 300; //wifi采集设备采集数据范围（半径，单位米）
    private static final int errorTime = (int) (WIFIRADIUS / CARSPEED); //误差时间
    private static final int DISTANCE = 1000; //最近wifi采集点到给定设备点的最大距离，超过此距离的最近wifi采集点不做计算
    private enum DIRECTION {FROM, TO} //与车辆行进方向相关和wifi采集点相对车辆采集点的方位相关。例如车辆行进方向为南向北，则位于车辆采集点南边的wifi采集设备取FROM，位于车辆采集点北边的wifi采集设备取TO
    private List<WifiEquipmentInfo> wifiEquipmentInfoList = new ArrayList<>();//缓存wifi采集设备列表信息
    private List<VideoEquipmentInfo> videoEquipmentInfoList = new ArrayList<>();//缓存车辆采集设备列表信息
    private Map<String, WifiEquipmentInfo> wifiEquipmentId2WifiEquipmentInfoMap = new HashMap<>();//缓存wifi采集设备id到设备信息的映射
    private Map<String, VideoEquipmentInfo> videoEquipmentId2VideoEquipmentInfoMap = new HashMap<>();//缓存车辆采集设备id到设备信息的映射
    private Map<String, NearestLocation> videoEquipmentId2NearestWifiLocationMap = new HashMap<>();//缓存各车辆采集设备和距离它最近的wifi采集点的映射信息
    private Map<String, Map<String, List<MacTrace>>> equipmentId2MacTimeLineMap = new HashMap<>();//缓存各wifi采集设备和按照时间分组后的mac轨迹信息的映射关系
    private Map<String, Map<String, List<CarTrace>>> equipmentId2CarTimeLineMap = new HashMap<>();//缓存各车辆采集设备和按照时间分组后的车辆轨迹信息的映射关系
    private Map<String, Integer> carMacBundleApperenceTimes = new HashMap<>();//键为：绑定的车辆和mac（carPlateId_macId ），值为：该绑定关系出现的次数

    public CarMacRelation() throws SQLException {
        Connection conn = ConnectionUtil.getInstance().getConnection();
        //缓存wifi采集设备信息
        cacheWifiEqipmentInfo(conn);
        //缓存车辆采集设备信息
        cacheVideoEquipmentInfo(conn);
        //缓存各wifi采集设备和按照时间分组后的mac轨迹信息的映射关系
        cacheEquipment2TimeLineMacTraceInstancesMap(conn);
        //缓存各车辆采集设备和按照时间分组后的车辆轨迹信息的映射关系
        cacheEquipment2TimeLineCarTraceInstancesMap(conn);
        //缓存各车辆采集设备和距离它最近的wifi采集点的映射信息（东南西北各一个最近wifi采集点）
        cacheNearestWifiEquipmentLocationOfEachDirectionForVideoEquipment();
        conn.close();
    }

    //缓存wifi采集设备信息
    private void cacheWifiEqipmentInfo(Connection conn) throws SQLException {
        String sql = "SELECT primaryId, equipmentId, equipmentLocation, latitude, langitude, status FROM wifiequipmentinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            WifiEquipmentInfo wifiEquipmentInfoInstance = CreateInstanceUtil.createWifiEquipmentInfoInstance(resultSet);
            wifiEquipmentInfoList.add(wifiEquipmentInfoInstance);
            wifiEquipmentId2WifiEquipmentInfoMap.put(wifiEquipmentInfoInstance.getEquipmentId(), wifiEquipmentInfoInstance);
        }
        resultSet.close();
        pst.close();
    }

    //缓存车辆采集设备信息
    private void cacheVideoEquipmentInfo(Connection conn) throws SQLException {
        String sql = "SELECT primaryId, equipmentId, equipmentLocation, latitude, langitude, direction, channel, area, blatitude, blangitude FROM videoequipmentinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            VideoEquipmentInfo videoEquipmentInfoInstance = CreateInstanceUtil.createVideoEquipmentInfoInstance(resultSet);
            videoEquipmentInfoList.add(videoEquipmentInfoInstance);
            videoEquipmentId2VideoEquipmentInfoMap.put(videoEquipmentInfoInstance.getEquipmentId(), videoEquipmentInfoInstance);
        }
        resultSet.close();
        pst.close();
    }

    //缓存各wifi采集设备和按照时间分组后的mac轨迹信息的映射关系
    private void cacheEquipment2TimeLineMacTraceInstancesMap(Connection conn) throws SQLException {
        for (WifiEquipmentInfo wifiEquipmentInfo : wifiEquipmentInfoList) {
            //缓存各wifi采集设备和按照时间分组后的mac轨迹信息的映射关系
            cacheEachEquipmentId2TimeLineMacTraceInstancesMap(conn, wifiEquipmentInfo.getEquipmentId());
        }
    }

    //缓存各车辆采集设备和按照时间分组后的车辆轨迹信息的映射关系
    private void cacheEquipment2TimeLineCarTraceInstancesMap(Connection conn) throws SQLException {
        for (VideoEquipmentInfo videoEquipmentInfo : videoEquipmentInfoList) {
            //缓存各车辆采集设备和按照时间分组后的车辆轨迹信息的映射关系
            cacheEachEquipmentId2TimeLineCarTraceInstancesMap(conn, videoEquipmentInfo.getEquipmentId());
        }
    }

    private void cacheEachEquipmentId2TimeLineMacTraceInstancesMap(Connection conn, String equipmentId) throws SQLException {
        //key为各个时间点，精确到秒（格式为：yyyy-MM-dd HH:mm:ss）, value为该时间点下所有采集到的mac轨迹信息的集合
        Map<String, List<MacTrace>> timePoint2MacTraceList = new HashMap<>();
        //查询给定wifi采集设备采集到的所有mac轨迹信息，采集结果按照开始时间升序排列
        String sql = "SELECT primaryId, macId, equipmentId, equipmentLocation, startTime, endTime, latitude, langitude, createTime FROM mactrace WHERE equipmentId = ? ORDER BY startTime asc";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, equipmentId);
        ResultSet resultSet = pst.executeQuery();
        //将同一时刻的macTrace放到一个集合中
        String timePoint = null;
        while (resultSet.next()) {
            final MacTrace macTraceInstance = CreateInstanceUtil.createMacTraceInstance(resultSet);
            Date startTime = macTraceInstance.getStartTime();
            String startTimePoint = DateUtils.formatDateByFormat(startTime, FORMATER);
            if (timePoint == null || !timePoint.equals(startTimePoint)) {
                List<MacTrace> macTraceList = new ArrayList<>();
                macTraceList.add(macTraceInstance);
                timePoint2MacTraceList.put(startTimePoint, macTraceList);
            } else {
                List<MacTrace> macTraceList = timePoint2MacTraceList.get(startTimePoint);
                macTraceList.add(macTraceInstance);
            }
        }
        resultSet.close();
        pst.close();
        //将该wifi采集设备下各时间点的macTrace分组信息进行缓存
        equipmentId2MacTimeLineMap.put(equipmentId, timePoint2MacTraceList);
    }

    private void cacheEachEquipmentId2TimeLineCarTraceInstancesMap(Connection conn, String equipmentId) throws SQLException {
        //key为各个时间点，精确到秒（格式为：yyyy-MM-dd HH:mm:ss）, value为该时间点下所有采集到的车辆轨迹信息的集合
        Map<String, List<CarTrace>> timePoint2CarTraceList = new HashMap<>();
        //查询给定车辆采集设备采集到的所有车辆轨迹信息，采集结果按照开始时间升序排列
        String sql = "SELECT primaryId, carPlateId, equipmentId, equipmentLocation, startTime, latitude, langitude, createTime, endTime FROM cartrace WHERE equipmentId = ? ORDER BY startTime asc";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, equipmentId);
        ResultSet resultSet = pst.executeQuery();
        //将同一时刻的carTrace放到一个集合中
        String timePoint = null;
        while (resultSet.next()) {
            final CarTrace carTraceInstance = CreateInstanceUtil.createCarTraceInstance(resultSet);
            Date startTime = carTraceInstance.getStartTime();
            String startTimeStr = DateUtils.formatDateByFormat(startTime, FORMATER);
            if (timePoint == null || !timePoint.equals(startTimeStr)) {
                List<CarTrace> carTraceList = new ArrayList<>();
                carTraceList.add(carTraceInstance);
                timePoint2CarTraceList.put(startTimeStr, carTraceList);
            } else {
                List<CarTrace> carTraceList = timePoint2CarTraceList.get(startTimeStr);
                carTraceList.add(carTraceInstance);
            }
        }
        resultSet.close();
        pst.close();
        //将该车辆采集设备下各时间点的carTrace分组信息进行缓存
        equipmentId2CarTimeLineMap.put(equipmentId, timePoint2CarTraceList);
    }

    //缓存各车辆采集设备和距离它最近的wifi采集点的映射信息（东南西北各一个最近wifi采集点）
    private void cacheNearestWifiEquipmentLocationOfEachDirectionForVideoEquipment() {
        for (VideoEquipmentInfo videoEquipmentInfo : videoEquipmentInfoList) {
            //在东南西北各方位分别找到一个与给定车辆采集设备最近的wifi采集设备，并将映射关系进行缓存
            videoEquipmentId2NearestWifiLocationMap.put(videoEquipmentInfo.getEquipmentId(), getNearestWifiEquipmentOfEachDirectionForGivenVideoEquipment(videoEquipmentInfo));
        }
    }

    private NearestLocation getNearestWifiEquipmentOfEachDirectionForGivenVideoEquipment(VideoEquipmentInfo videoEquipmentInfo) {
        GeoPoint videoEquipmentPoint = new GeoPoint(videoEquipmentInfo.getOriginLatitude(), videoEquipmentInfo.getOriginLangitude());
        LocationInfo nearestNorthInfo = null;
        LocationInfo nearestSouthInfo = null;
        LocationInfo nearestEastInfo = null;
        LocationInfo nearestWestInfo = null;
        //遍历wifi采集设备列表，寻找各方位与给定车辆采集设备最近的wifi采集设备
        for (WifiEquipmentInfo wifiEquipmentInfo : wifiEquipmentInfoList) {
            GeoPoint wifiEquipmentPoint = new GeoPoint(wifiEquipmentInfo.getLatitude(), wifiEquipmentInfo.getLangitude());
            //计算当前wifi采集设备与给定车辆采集设备的距离
            Double distance = MapCaculateUtil.GetShortDistance(videoEquipmentPoint, wifiEquipmentPoint);
            //当距离超过设定的阈值时，认为该wifi采集设备超出设定范围，不做计算
            if (distance > DISTANCE) {
                continue;
            }

            // 1、判断当前wifi采集设备在给定车辆采集设备的哪个方位
            // 2、若该方位下最近wifi采集设备为空，则将该方位下最近wifi采集设备设为当前的wifi采集设备
            // 3、若不为空，则比较当前wifi采集设备是否比当前该方位下最近的wifi采集设备距离给定车辆采集设备更近
            //   3.1、是，则更新该方位下最近的wifi采集设备为当前wifi采集设备
            //   3.2、否，不做处理
            if (wifiEquipmentPoint.isLocatedInNorth(videoEquipmentPoint)) {
                if (null == nearestNorthInfo) {
                    nearestNorthInfo = new LocationInfo(wifiEquipmentInfo, distance);
                } else if (distance < nearestNorthInfo.getDistance()) {
                    nearestNorthInfo.setWifiEquipmentInfo(wifiEquipmentInfo);
                    nearestNorthInfo.setDistance(distance);
                }
            } else if (wifiEquipmentPoint.isLocatedInSouth(videoEquipmentPoint)) {
                if (null == nearestSouthInfo) {
                    nearestSouthInfo = new LocationInfo(wifiEquipmentInfo, distance);
                } else if (distance < nearestSouthInfo.getDistance()) {
                    nearestSouthInfo.setWifiEquipmentInfo(wifiEquipmentInfo);
                    nearestSouthInfo.setDistance(distance);
                }
            } else if (wifiEquipmentPoint.isLocatedInEast(videoEquipmentPoint)) {
                if (null == nearestEastInfo) {
                    nearestEastInfo = new LocationInfo(wifiEquipmentInfo, distance);
                } else if (distance < nearestEastInfo.getDistance()) {
                    nearestEastInfo.setWifiEquipmentInfo(wifiEquipmentInfo);
                    nearestEastInfo.setDistance(distance);
                }
            } else if (wifiEquipmentPoint.isLocatedInWest(videoEquipmentPoint)) {
                if (null == nearestWestInfo) {
                    nearestWestInfo = new LocationInfo(wifiEquipmentInfo, distance);
                } else if (distance < nearestWestInfo.getDistance()) {
                    nearestWestInfo.setWifiEquipmentInfo(wifiEquipmentInfo);
                    nearestWestInfo.setDistance(distance);
                }
            }
        }
        return new NearestLocation(nearestNorthInfo, nearestSouthInfo, nearestEastInfo, nearestWestInfo);
    }

    //分析思路：
    //某一车辆设备点 --> 与之最近的wifi设备点（东南西北各找一个，由于有距离限制，部分方向可能没有最近wifi设备点）
    //某一车辆设备点 --> 该设备点采集到的按时间分组的车辆轨迹信息集合
    //某一wifi设备点 --> 该设备点采集到的按时间分组的mac轨迹信息集合
    //遍历各个车辆时间分组，并依据设备采集的行进方向，与各个最近wifi采集点的距离，车辆行进速度 -->
    //    依据车辆时间点，设备采集的行进方向，与各个最近wifi采集点的距离，wifi采集设备覆盖范围，车辆行进速度 -->
    //        判断出车辆路过各最近wifi采集点可能的时间范围 --> 找到各最近wifi采集点在此时间范围收集到的mac轨迹信息 -->
    //           将所有车辆与所有mac信息依据笛卡尔积的形式进行绑定，并更新绑定计数器carMacBundleApperenceTimes中key对应的value的值。
    private void analysis() {
        Set<String> videoEquipmentIds = videoEquipmentId2NearestWifiLocationMap.keySet();
        for (String videoEquipmentId : videoEquipmentIds) {
            if (videoEquipmentId != null) {
                VideoEquipmentInfo videoEquipmentInfo = videoEquipmentId2VideoEquipmentInfoMap.get(videoEquipmentId);
                GeoPoint videoEquipmentPoint = new GeoPoint(videoEquipmentInfo.getOriginLatitude(), videoEquipmentInfo.getOriginLangitude());
                Map<String, List<CarTrace>> timePoint2CarTracesMap = equipmentId2CarTimeLineMap.get(videoEquipmentId);
                NearestLocation nearestLocation = videoEquipmentId2NearestWifiLocationMap.get(videoEquipmentId);
                Set<String> timePoints = timePoint2CarTracesMap.keySet();
                for (String timePoint : timePoints) {
                    List<CarTrace> carTraces = timePoint2CarTracesMap.get(timePoint);
                    String direction = videoEquipmentInfo.getDirection();
                    if (direction.equals("北向南")) {
                        // 如果设备采集方向为北向南
                        // 则位于车辆采集点以北的wifi采集点，bundle方法中direction参数值为FROM
                        // 位于车辆采集点以南的wifi采集点，bundle方法中direction参数值为TO
                        // 与车辆行进方向相匹配
                        LocationInfo northNearest = nearestLocation.getNorthNearest();
                        if (null != northNearest) {
                            bundle(northNearest, timePoint, carTraces, DIRECTION.FROM.name());
                        }
                        LocationInfo southNearest = nearestLocation.getSouthNearest();
                        if (null != southNearest) {
                            bundle(southNearest, timePoint, carTraces, DIRECTION.TO.name());
                        }
                        // 应为设备采集的行进方向为南北，则东西最近点要看是在车辆采集点南北的哪个方向，再来进行计算
                        LocationInfo eastNearest = nearestLocation.getEastNearest();
                        if (null != eastNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(eastNearest.getWifiEquipmentInfo().getLatitude(), eastNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInNorth(videoEquipmentPoint)) {
                                bundle(eastNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(eastNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                        LocationInfo westNearest = nearestLocation.getWestNearest();
                        if (null != westNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(westNearest.getWifiEquipmentInfo().getLatitude(), westNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInNorth(videoEquipmentPoint)) {
                                bundle(westNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(westNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }

                    } else if (direction.equals("南向北")) {
                        LocationInfo southNearest = nearestLocation.getSouthNearest();
                        if (null != southNearest) {
                            bundle(southNearest, timePoint, carTraces, DIRECTION.FROM.name());
                        }
                        LocationInfo northNearest = nearestLocation.getNorthNearest();
                        if (null != northNearest) {
                            bundle(northNearest, timePoint, carTraces, DIRECTION.TO.name());
                        }
                        LocationInfo eastNearest = nearestLocation.getEastNearest();
                        if (null != eastNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(eastNearest.getWifiEquipmentInfo().getLatitude(), eastNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInSouth(videoEquipmentPoint)) {
                                bundle(eastNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(eastNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                        LocationInfo westNearest = nearestLocation.getWestNearest();
                        if (null != westNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(westNearest.getWifiEquipmentInfo().getLatitude(), westNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInSouth(videoEquipmentPoint)) {
                                bundle(westNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(westNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                    } else if (direction.equals("东向西")) {
                        LocationInfo eastNearest = nearestLocation.getEastNearest();
                        if (null != eastNearest) {
                            bundle(eastNearest, timePoint, carTraces, DIRECTION.FROM.name());
                        }
                        LocationInfo westNearest = nearestLocation.getWestNearest();
                        if (null != westNearest) {
                            bundle(westNearest, timePoint, carTraces, DIRECTION.TO.name());
                        }
                        LocationInfo northNearest = nearestLocation.getNorthNearest();
                        if (null != northNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(northNearest.getWifiEquipmentInfo().getLatitude(), northNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInEast(videoEquipmentPoint)) {
                                bundle(northNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(northNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                        LocationInfo southNearest = nearestLocation.getSouthNearest();
                        if (null != southNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(southNearest.getWifiEquipmentInfo().getLatitude(), southNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInEast(videoEquipmentPoint)) {
                                bundle(southNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(southNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                    } else if (direction.equals("西向东")) {
                        LocationInfo westNearest = nearestLocation.getWestNearest();
                        if (null != westNearest) {
                            bundle(westNearest, timePoint, carTraces, DIRECTION.FROM.name());
                        }
                        LocationInfo eastNearest = nearestLocation.getEastNearest();
                        if (null != eastNearest) {
                            bundle(eastNearest, timePoint, carTraces, DIRECTION.TO.name());
                        }
                        LocationInfo northNearest = nearestLocation.getNorthNearest();
                        if (null != northNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(northNearest.getWifiEquipmentInfo().getLatitude(), northNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInWest(videoEquipmentPoint)) {
                                bundle(northNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(northNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                        LocationInfo southNearest = nearestLocation.getSouthNearest();
                        if (null != southNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(southNearest.getWifiEquipmentInfo().getLatitude(), southNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInWest(videoEquipmentPoint)) {
                                bundle(southNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(southNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                    }
                }
            }
        }
    }

    private void bundle (LocationInfo locationInfo, String timePoint, List<CarTrace> carTraces, String direction) {
        // 根据距离计算时间差
        int timeGap = (int) (locationInfo.getDistance() / CARSPEED);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.formatDate(timePoint, FORMATER));
        // 如果wifi采集点在车辆采集点来的方向，则应减去时间间隔
        if (direction.equals(DIRECTION.FROM.name())) {
            calendar.add(Calendar.SECOND, -timeGap);
        } else {
            // 如果wifi采集点在车辆采集点去的方向，则应加上时间间隔
            calendar.add(Calendar.SECOND, timeGap);
        }
        // 因为wifi采集点有覆盖范围，由此会产生时间误差，此处对误差进行处理，设立一个时间范围
        calendar.add(Calendar.SECOND, errorTime);
        String maxTimePoint = DateUtils.formatDateByFormat(calendar.getTime(), FORMATER);
        calendar.add(Calendar.SECOND, - (2 * errorTime));
        String minTimePoint = DateUtils.formatDateByFormat(calendar.getTime(), FORMATER);
        Map<String, List<MacTrace>> timePoint2MacTracesMap = equipmentId2MacTimeLineMap.get(locationInfo.getWifiEquipmentInfo().getEquipmentId());
        Set<String> macTimePoints = timePoint2MacTracesMap.keySet();
        for (String macTimePoint : macTimePoints) {
            //看该wifi设备采集到的mac轨迹哪些在上面计算出的时间范围内
            boolean isInTimeZone = isInTimeZone(macTimePoint, minTimePoint, maxTimePoint);
            if (isInTimeZone) {
                List<MacTrace> macTraces = timePoint2MacTracesMap.get(macTimePoint);
                //将在时间范围内的mac与车辆进行绑定
                bundleCarMac(carTraces, macTraces);
            }
        }
    }

    private boolean isInTimeZone(String timePoint, String minTimePoint, String maxTimePoint) {
        if (timePoint.compareTo(minTimePoint) >= 0 && timePoint.compareTo(maxTimePoint) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    private void bundleCarMac(List<CarTrace> carTraces, List<MacTrace> macTraces) {
        for (CarTrace carTrace : carTraces) {
            String carPlateId = carTrace.getCarPlateId();
            for (MacTrace macTrace : macTraces) {
                String macTraceId = macTrace.getMacId();
                String bundle = carPlateId + "_" + macTraceId;
                //更新相同车辆mac绑定出现的次数
                if (carMacBundleApperenceTimes.containsKey(bundle)) {
                    carMacBundleApperenceTimes.put(bundle, carMacBundleApperenceTimes.get(bundle) + 1);
                } else {
                    carMacBundleApperenceTimes.put(bundle, 1);
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        CarMacRelation carMacRelation = new CarMacRelation();
        System.out.println(1);
    }

}
