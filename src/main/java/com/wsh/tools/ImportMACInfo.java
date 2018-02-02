package com.wsh.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scisdata.web.bean.ControlResult;
import com.scisdata.web.bean.ControlTask;
import com.scisdata.web.bean.WifiEquipmentInfo;
import com.wsh.tools.utils.ConnectionHelper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Component("importMACInfo")
public class ImportMACInfo {

    private static final int CONTROL_NOT_BEGIN = 0;
    private static final int CONTROL_BEGIN = 1;
    private static final int CONTROL_FINISH = 2;
    private static final int CONTROL_POINT = 0;
    private static final int CONTROL_AREA = 1;
    private static final int CONTROL_WIFI_TASK = 0;
    private Map<String, WifiEquipmentInfo> equipmentId2EquipmentInfoMap = new HashMap<String, WifiEquipmentInfo>();
    private Map<String, String> macAddress2macIdMap = new HashMap<>();
    private List<ControlTask> pointControlTask = new ArrayList<>();
    private List<ControlTask> areaControlTask = new ArrayList<>();
    private static final String SELECT_BEGIN_CONTROL_TASKS_BY_CONTROL_TYPE = "SELECT ID, taskName, taskTarget, startTime, endTime, enqumentID, createTime, taskStatus, taskType, controlType FROM controltask WHERE taskStatus = "+ CONTROL_BEGIN +" AND controlType = ?";
    private static final String INSERT_MAC_TRACE_SQL = "INSERT INTO mactrace (primaryId, macId, equipmentId, equipmentLocation, startTime, endTime, latitude, langitude, createtime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CONTROL_TASK_STATUS_FROM_NOTBEGIN_TO_BEGIN_SQL = "UPDATE controltask SET taskStatus = " + CONTROL_BEGIN + " WHERE taskStatus = ? AND startTime < ?";
    private static final String UPDATE_CONTROL_TASK_STATUS_FROM_BEGIN_TO_FINISH_SQL = "UPDATE controltask SET taskStatus = " + CONTROL_FINISH + " WHERE taskStatus = ? AND endTime < ?";

    public ImportMACInfo() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionHelper.createConnection();
        cacheEquipmentInfo(conn);
        cacheMacInfo(conn);
        conn.close();
    }

    private static void updateControlTaskStatus() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionHelper.createConnection();
        updateTaskStatusFromNotBeginToBegin(conn);
        updateTaskStatusFromBeginToFinish(conn);
        conn.close();
    }

    private static void updateTaskStatusFromNotBeginToBegin(Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(UPDATE_CONTROL_TASK_STATUS_FROM_NOTBEGIN_TO_BEGIN_SQL);
        pst.setInt(1, CONTROL_NOT_BEGIN);
        pst.setTimestamp(2, new Timestamp(new Date().getTime()));
        pst.executeUpdate();
        pst.close();
    }

    private static void updateTaskStatusFromBeginToFinish(Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(UPDATE_CONTROL_TASK_STATUS_FROM_BEGIN_TO_FINISH_SQL);
        pst.setInt(1, CONTROL_BEGIN);
        pst.setTimestamp(2, new Timestamp(new Date().getTime()));
        pst.executeUpdate();
        pst.close();
    }

    public void cacheEquipmentInfo(Connection conn) throws SQLException {
        String sql = "SELECT primaryId, equipmentId, equipmentLocation, latitude, langitude, status FROM wifiequipmentinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            WifiEquipmentInfo wifiEquipmentInfoInstance = createWifiEquipmentInfoInstance(resultSet);
            equipmentId2EquipmentInfoMap.put(wifiEquipmentInfoInstance.getEquipmentId(), wifiEquipmentInfoInstance);
        }
        resultSet.close();
        pst.close();
    }

    public void cacheMacInfo(Connection conn) throws SQLException {
        String sql = "SELECT primaryId, macAddress FROM macinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            String macAddress = resultSet.getString(2);
            String macId = resultSet.getString(1);
            macAddress2macIdMap.put(macAddress, macId);
        }
        resultSet.close();
        pst.close();
    }

    public void cacheControlTasks() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionHelper.createConnection();
        cacheControlPointTasks(conn);
        cacheControlAreaTasks(conn);
        conn.close();
    }

    public void cacheControlPointTasks(Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(SELECT_BEGIN_CONTROL_TASKS_BY_CONTROL_TYPE);
        pst.setInt(1, CONTROL_POINT);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            pointControlTask.add(createControlTaskInstance(resultSet));
        }
        resultSet.close();
        pst.close();
    }

    public void cacheControlAreaTasks(Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(SELECT_BEGIN_CONTROL_TASKS_BY_CONTROL_TYPE);
        pst.setInt(1, CONTROL_AREA);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            areaControlTask.add(createControlTaskInstance(resultSet));
        }
        resultSet.close();
        pst.close();
    }

    public ControlTask createControlTaskInstance(ResultSet resultSet) throws SQLException {
        Long ID = resultSet.getLong(1);
        String taskName = resultSet.getString(2);
        String taskTarget = resultSet.getString(3);
        Timestamp startTime = resultSet.getTimestamp(4);
        Timestamp endTime = resultSet.getTimestamp(5);
        String enqumentID = resultSet.getString(6);
        Timestamp createTime = resultSet.getTimestamp(7);
        long taskStatus = resultSet.getLong(8);
        long taskType = resultSet.getLong(9);
        long controlType = resultSet.getLong(10);
        ControlTask controlTask = new ControlTask();
        controlTask.setID(ID);
        controlTask.setTaskName(taskName);
        controlTask.setTaskTarget(taskTarget);
        controlTask.setStartTime(startTime);
        controlTask.setEndTime(endTime);
        controlTask.setEnqumentID(enqumentID);
        controlTask.setCreateTime(createTime);
        controlTask.setTaskStatus(taskStatus);
        controlTask.setTaskType(taskType);
        controlTask.setControlType(controlType);
        return controlTask;
    }

    public WifiEquipmentInfo createWifiEquipmentInfoInstance(ResultSet resultSet) throws SQLException {
        Long primaryId = resultSet.getLong(1);
        String equipmentId = resultSet.getString(2);
        String equipmentLocation = resultSet.getString(3);
        Double latitude = resultSet.getDouble(4);
        Double langitude = resultSet.getDouble(5);
        Integer status = resultSet.getInt(6);
        WifiEquipmentInfo wifiEquipmentInfo = new WifiEquipmentInfo();
        wifiEquipmentInfo.setPrimaryId(primaryId);
        wifiEquipmentInfo.setEquipmentId(equipmentId);
        wifiEquipmentInfo.setEquipmentLocation(equipmentLocation);
        wifiEquipmentInfo.setLatitude(latitude);
        wifiEquipmentInfo.setLangitude(langitude);
        wifiEquipmentInfo.setStatus(status);
        return wifiEquipmentInfo;
    }

    private JSONArray parseFile(String filePath) throws IOException, SQLException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        String rawData = new String(bytes);
        fis.close();
        JSONArray jsonArray = JSONArray.parseArray(rawData);
        Connection conn = ConnectionHelper.createConnection();
        conn.setAutoCommit(false);
        PreparedStatement pst = conn.prepareStatement(INSERT_MAC_TRACE_SQL);
        JSONArray extractedFieldsArray = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            handleEachOriginMacTraceObj(pst,jsonArray.getJSONObject(i), extractedFieldsArray);
        }
        pst.executeBatch();
        conn.commit();
        pst.close();
        conn.close();
        return extractedFieldsArray;
    }

    private void handleEachOriginMacTraceObj(PreparedStatement pst, JSONObject originMacTraceObj, JSONArray extractedFieldsArray) throws SQLException, ClassNotFoundException {
        JSONObject extractedMacTraceObj = extractUsefulFields(originMacTraceObj);
        String macAddress = extractedMacTraceObj.getString("macAddress");
        String equipmentId = extractedMacTraceObj.getString("equipmentId");
        insertMacAddressIntoMacInfoTable(macAddress);
        String macTracePrimaryId = prepareBatchInsertMacTraceIntoMacTraceTable(pst, extractedMacTraceObj);
        checkControlTasks(macAddress, equipmentId, macTracePrimaryId);
        extractedFieldsArray.add(extractedMacTraceObj);
    }

    private void insertMacAddressIntoMacInfoTable(String macAddress) throws SQLException, ClassNotFoundException {
        if (!isMacAddressExist(macAddress)) {
            String macId = insertMacInfo(macAddress);
            updateMacAddress2macIdMap(macAddress, macId);
        }
    }

    private String prepareBatchInsertMacTraceIntoMacTraceTable(PreparedStatement pst, JSONObject extractedMacTraceObj) throws SQLException {
        String primaryId = UUID.randomUUID().toString();
        pst.setString(1, primaryId);
        pst.setString(2, macAddress2macIdMap.get(extractedMacTraceObj.getString("macAddress")));
        pst.setString(3, extractedMacTraceObj.getString("equipmentId"));
        pst.setString(4, extractedMacTraceObj.getString("equipmentLocation"));
        pst.setTimestamp(5, new Timestamp(extractedMacTraceObj.getLong("startTime") * 1000));
        pst.setTimestamp(6, new Timestamp(extractedMacTraceObj.getLong("endTime") * 1000));
        pst.setDouble(7, extractedMacTraceObj.getDouble("latitude"));
        pst.setDouble(8, extractedMacTraceObj.getDouble("langitude"));
        pst.setTimestamp(9, new Timestamp(new Date().getTime()));
        pst.addBatch();
        return primaryId;
    }

    private void checkControlTasks(String macAddress, String equipmentId, String macTracePrimaryId) throws SQLException, ClassNotFoundException {
        List<ControlResult> controlResults = new ArrayList<>();
        checkPointControlTasks(macAddress, equipmentId, macTracePrimaryId, controlResults);
        checkAreaControlTasks(macAddress, macTracePrimaryId, controlResults);
        insertControlResults(controlResults);
    }

    private void checkPointControlTasks(String macAddress, String equipmentId, String macTracePrimaryId, List<ControlResult> controlResults) {
        for (ControlTask controlTask : pointControlTask) {
            String controledMacAddress = controlTask.getTaskTarget();
            String controledEquipmentId = controlTask.getEnqumentID();
            if (macAddress.equals(controledMacAddress) && equipmentId.equals(controledEquipmentId)) {
                controlResults.add(new ControlResult(macTracePrimaryId, controlTask.getID()));
            }
        }
    }

    private void checkAreaControlTasks(String macAddress, String macTracePrimaryId, List<ControlResult> controlResults) {
        for (ControlTask controlTask : areaControlTask) {
            String controledMacAddress = controlTask.getTaskTarget();
            if (macAddress.equals(controledMacAddress)) {
                controlResults.add(new ControlResult(macTracePrimaryId, controlTask.getID()));
            }
        }
    }

    private void insertControlResults(List<ControlResult> controlResults) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionHelper.createConnection();
        conn.setAutoCommit(false);
        String sql = "INSERT INTO controlresult (traceId, taskId) VALUES (?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        for (ControlResult controlResult : controlResults) {
            pst.setString(1, controlResult.getTraceId());
            pst.setLong(2, controlResult.getTaskId());
            pst.addBatch();
        }
        pst.executeBatch();
        pst.close();
        conn.close();
    }

    private boolean isMacAddressExist(String macAddress) {
        return macAddress2macIdMap.containsKey(macAddress);
    }

    private String insertMacInfo(String macAddress) throws SQLException, ClassNotFoundException {
        String macId = UUID.randomUUID().toString();
        Connection conn = ConnectionHelper.createConnection();
        String sql = "INSERT INTO macinfo (primaryId, macAddress) VALUES (?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, macId);
        pst.setString(2, macAddress);
        pst.execute();
        pst.close();
        conn.close();
        return macId;
    }

    private void updateMacAddress2macIdMap(String macId, String macAddress) {
        macAddress2macIdMap.put(macId, macAddress);
    }

    private JSONObject extractUsefulFields(JSONObject jsonObject) {

        String macAddress = jsonObject.getString("MAC");
        String equipmentId = jsonObject.getString("DEVICENUM");
        Long startTime = jsonObject.getLong("START_TIME");
        Long endTime = jsonObject.getLong("END_TIME");
        WifiEquipmentInfo equipmentInfo = equipmentId2EquipmentInfoMap.get(equipmentId);
        String equipmentLocation = equipmentInfo.getEquipmentLocation();
        Double latitude = jsonObject.getDouble("XPOINT");
        Double langitude = jsonObject.getDouble("YPOINT");
        Double consultLatitude = jsonObject.getDouble("CONSULT_XPOINT");
        Double consultLangitude = jsonObject.getDouble("CONSULT_YPOINT");
        if (null == latitude) {
            latitude = equipmentInfo.getLatitude();
        }
        if (null == langitude) {
            langitude = equipmentInfo.getLangitude();
        }
        if (null == consultLatitude) {
            consultLatitude = new Double(0);
        }
        if (null == consultLangitude) {
            consultLangitude = new Double(0);
        }

        JSONObject obj = new JSONObject();
        obj.put("macAddress", macAddress);
        obj.put("equipmentId", equipmentId);
        obj.put("equipmentLocation", equipmentLocation);
        obj.put("startTime", startTime);
        obj.put("endTime", endTime);
        obj.put("latitude", latitude);
        obj.put("langitude", langitude);
        obj.put("consultLatitude", consultLangitude);
        obj.put("consultLangitude", consultLangitude);

        return obj;
    }

    public void writeToFile(String filename, JSONArray jsonArray) throws IOException {
        FileWriter fr = new FileWriter(new File(filename));
        BufferedWriter bw = new BufferedWriter(fr);
        bw.write(JSON.toJSONString(jsonArray));
        bw.close();
        fr.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
//        String filename = "D:\\01 系统资源\\Desktop\\样例数据\\20180125021009901_139_110115_723005104_001.log";
//        ImportMACInfo importMACInfo = new ImportMACInfo();
//        JSONArray jsonArray = importMACInfo.parseFile(filename);
//        importMACInfo.writeToFile("D:\\text.txt", jsonArray);
        System.out.println(new Timestamp(1516817383000l));
    }

}
