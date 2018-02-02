package com.scisdata.web.bean;

import com.scisdata.web.basedao.interfaces.DBTable;

import java.io.Serializable;

/**
 * created by kingcobra
 *
 */
@DBTable("wifiEquipmentInfo")
public class WifiEquipmentInfo implements Serializable {

    private static final long serialVersionUID = -4729063899359875895L;
    public long primaryId;
    public String equipmentId;
    public String equipmentLocation;
    public double latitude;
    public double langitude;
    public int status;
    public String equipmentMac;
    public String locationName;
    public String locationType;
    public String locationSubType;
    public String locationCode;

    public String getEquipmentMac() {
        return equipmentMac;
    }

    public void setEquipmentMac(String equipmentMac) {
        this.equipmentMac = equipmentMac;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationSubType() {
        return locationSubType;
    }

    public void setLocationSubType(String locationSubType) {
        this.locationSubType = locationSubType;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
