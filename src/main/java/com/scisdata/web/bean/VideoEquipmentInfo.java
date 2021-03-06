package com.scisdata.web.bean;

import com.scisdata.web.basedao.interfaces.DBTable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 113系统设备点位信息
 */
@DBTable("videoEquipmentInfo")
public class VideoEquipmentInfo implements Serializable {

    private static final long serialVersionUID = -3416937975535052578L;
    private long primaryId;
    public String equipmentId;
    public String equipmentLocation;
    public double originLatitude;
    public double originLangitude;
    public double latitude;
    public double langitude;

    public String direction;
    public int channel;
    public String area;

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentLocation() {
        return equipmentLocation;
    }

    public void setEquipmentLocation(String equipmentLocation) {
        this.equipmentLocation = equipmentLocation;
    }

    public double getOriginLatitude() {
        return originLatitude;
    }

    public void setOriginLatitude(double originLatitude) {
        this.originLatitude = originLatitude;
    }

    public double getOriginLangitude() {
        return originLangitude;
    }

    public void setOriginLangitude(double originLangitude) {
        this.originLangitude = originLangitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLangitude() {
        return langitude;
    }

    public void setLangitude(double langitude) {
        this.langitude = langitude;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
