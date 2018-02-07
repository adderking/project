package com.wsh.tools;

import com.wsh.tools.utils.ConnectionUtil;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class GenerateDdata {

    public static void main(String[] args)  throws SQLException {
        generateCarData();
    }

    private static void generateWifiMacData() throws SQLException {
        Connection conn = ConnectionUtil.getInstance().getConnection();
        String equipmentId = "72300510494885E318669";
        String macId = "7b2b05bf-b4cf-4aba-a449-52451eb8cac4";
        String equipmentLocation = "";
        int year = 2019;
        int month = 0;
        int date = 31;
        int hour = 15;
        int minute = 43;
        int second = 31;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, hour, minute, second);
        Timestamp startTime = new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.SECOND,88);
        Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
        Timestamp createTime = new Timestamp(new Date().getTime());
        Double latitude = 0d;
        Double langitude = 0d;
        String sql = "SELECT equipmentLocation, latitude, langitude FROM wifiEquipmentInfo WHERE equipmentId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, equipmentId);
        ResultSet resultSet = pst.executeQuery();
        if (resultSet.next()) {
            equipmentLocation = resultSet.getString(1);
            latitude = resultSet.getDouble(2);
            langitude = resultSet.getDouble(3);
        }
        sql = "INSERT INTO mactrace (primaryId, macId, equipmentId, equipmentLocation, startTime, endTime, latitude, langitude, createtime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pst = conn.prepareStatement(sql);
        pst.setString(1, UUID.randomUUID().toString());
        pst.setString(2, macId);
        pst.setString(3, equipmentId);
        pst.setString(4, equipmentLocation);
        pst.setTimestamp(5, startTime);
        pst.setTimestamp(6, endTime);
        pst.setDouble(7, latitude);
        pst.setDouble(8, langitude);
        pst.setTimestamp(9, createTime);
        pst.execute();
        pst.close();
        conn.close();
    }

    private static void generateCarData() throws SQLException {
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
