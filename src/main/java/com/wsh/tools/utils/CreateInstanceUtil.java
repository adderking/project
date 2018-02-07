package com.wsh.tools.utils;

import com.scisdata.web.bean.CarTrace;
import com.scisdata.web.bean.MacTrace;
import com.scisdata.web.bean.VideoEquipmentInfo;
import com.scisdata.web.bean.WifiEquipmentInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CreateInstanceUtil {

    public static WifiEquipmentInfo createWifiEquipmentInfoInstance(ResultSet resultSet) throws SQLException {
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

    public static VideoEquipmentInfo createVideoEquipmentInfoInstance(ResultSet resultSet) throws SQLException {
        Long primaryId = resultSet.getLong(1);
        String equipmentId = resultSet.getString(2);
        String equipmentLocation = resultSet.getString(3);
        Double latitude = resultSet.getDouble(4);
        Double langitude = resultSet.getDouble(5);
        String direction = resultSet.getString(6);
        Integer channel = resultSet.getInt(7);
        String area = resultSet.getString(8);
        Double blatitude = resultSet.getDouble(9);
        Double blangitude = resultSet.getDouble(10);
        VideoEquipmentInfo videoEquipmentInfo = new VideoEquipmentInfo();
        videoEquipmentInfo.setPrimaryId(primaryId);
        videoEquipmentInfo.setEquipmentId(equipmentId);
        videoEquipmentInfo.setEquipmentLocation(equipmentLocation);
        videoEquipmentInfo.setLatitude(latitude);
        videoEquipmentInfo.setLangitude(langitude);
        videoEquipmentInfo.setDirection(direction);
        videoEquipmentInfo.setChannel(channel);
        videoEquipmentInfo.setArea(area);
        videoEquipmentInfo.setOriginLatitude(blatitude);
        videoEquipmentInfo.setOriginLangitude(blangitude);
        return videoEquipmentInfo;
    }

    public static MacTrace createMacTraceInstance(ResultSet resultSet) throws SQLException {
        String primaryId = resultSet.getString(1);
        String macId = resultSet.getString(2);
        String equipmentId = resultSet.getString(3);
        String equipmentLocation = resultSet.getString(4);
        Timestamp startTime = resultSet.getTimestamp(5);
        Timestamp endTime = resultSet.getTimestamp(6);
        Double latitude = resultSet.getDouble(7);
        Double langitude = resultSet.getDouble(8);
        Timestamp createTime = resultSet.getTimestamp(9);
        MacTrace macTrace = new MacTrace();
        macTrace.setPrimaryId(primaryId);
        macTrace.setMacId(macId);
        macTrace.setEquipmentId(equipmentId);
        macTrace.setEquipmentLocation(equipmentLocation);
        macTrace.setStartTime(startTime);
        macTrace.setEndTime(endTime);
        macTrace.setLatitude(latitude);
        macTrace.setLangitude(langitude);
        macTrace.setCreatetime(createTime);
        return macTrace;
    }

    public static CarTrace createCarTraceInstance(ResultSet resultSet) throws SQLException {
        String primaryId = resultSet.getString(1);
        String carPlateId = resultSet.getString(2);
        String equipmentId = resultSet.getString(3);
        String equipmentLocation = resultSet.getString(4);
        Timestamp startTime = resultSet.getTimestamp(5);
        Double latitude = resultSet.getDouble(6);
        Double langitude = resultSet.getDouble(7);
        Timestamp createTime = resultSet.getTimestamp(8);
        Timestamp endTime = resultSet.getTimestamp(9);
        CarTrace carTrace = new CarTrace();
        carTrace.setPrimaryId(primaryId);
        carTrace.setCarPlateId(carPlateId);
        carTrace.setEquipmentId(equipmentId);
        carTrace.setEquipmentLocation(equipmentLocation);
        carTrace.setStartTime(startTime);
        carTrace.setLatitude(latitude);
        carTrace.setLangitude(langitude);
        carTrace.setCreateTime(createTime);
        carTrace.setEndTime(endTime);
        return carTrace;
    }

}
