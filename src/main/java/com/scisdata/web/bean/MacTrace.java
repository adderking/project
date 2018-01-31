package com.scisdata.web.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fangshilei on 18/1/26.
 */
public class MacTrace implements Serializable {
    private long primaryId;
    private long macId;
    private String equipmentId;
    private String equipmentLocation;
    private Date startTime;
    private Date endTime;
    private double latitude;
    private double langitude;
    private Date createtime;
    private double equipmentLatitude;
    private double equipmentLangitude;

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public long getMacId() {
        return macId;
    }

    public void setMacId(long macId) {
        this.macId = macId;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public double getEquipmentLatitude() {
        return equipmentLatitude;
    }

    public void setEquipmentLatitude(double equipmentLatitude) {
        this.equipmentLatitude = equipmentLatitude;
    }

    public double getEquipmentLangitude() {
        return equipmentLangitude;
    }

    public void setEquipmentLangitude(double equipmentLangitude) {
        this.equipmentLangitude = equipmentLangitude;
    }
}
