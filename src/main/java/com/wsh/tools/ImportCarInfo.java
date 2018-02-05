package com.wsh.tools;

import com.wsh.tools.utils.ConnectionUtil;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ImportCarInfo {

    private Map<String, String> carPlate2carIdMap = new HashMap<>();

    public ImportCarInfo() throws SQLException {
        Connection conn = ConnectionUtil.getInstance().getConnection();
        cacheCarInfo(conn);
        conn.close();
    }

    public void cacheCarInfo(Connection conn) throws SQLException {
        String sql = "SELECT primaryId, carPlate FROM carinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            String carPlate = resultSet.getString(2);
            String carId = resultSet.getString(1);
            carPlate2carIdMap.put(carPlate, carId);
        }
        resultSet.close();
        pst.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionUtil.getInstance().getConnection();
        String equipmentId = "dx00074";
        String carPlateId = "d60abd3f-db9e-4e1f-aeaf-3aacb5b1ea21";
        String equipmentLocation = "";
        int year = 2018;
        int month = 0;
        int date = 31;
        int hour = 15;
        int minute = 43;
        int second = 27;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute, second);
        Timestamp startTime = new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.SECOND,88);
        Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
        Timestamp createTime = new Timestamp(new Date().getTime());
        Double blatitude = 0d;
        Double blangitude = 0d;
        String sql = "SELECT equipmentLocation, blatitude, blangitude FROM videoEquipmentInfo WHERE equipmentId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, equipmentId);
        ResultSet resultSet = pst.executeQuery();
        if (resultSet.next()) {
            equipmentLocation = resultSet.getString(1);
            blatitude = resultSet.getDouble(2);
            blangitude = resultSet.getDouble(3);
        }
        sql = "INSERT INTO cartrace (primaryId, carPlateId, equipmentId, equipmentLocation, startTime, latitude, langitude, createtime, endTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pst = conn.prepareStatement(sql);
        pst.setString(1, UUID.randomUUID().toString());
        pst.setString(2, carPlateId);
        pst.setString(3, equipmentId);
        pst.setString(4, equipmentLocation);
        pst.setTimestamp(5, startTime);
        pst.setDouble(6, blatitude);
        pst.setDouble(7, blangitude);
        pst.setTimestamp(8, createTime);
        pst.setTimestamp(9, endTime);
        pst.execute();
        pst.close();
        conn.close();
    }

}
