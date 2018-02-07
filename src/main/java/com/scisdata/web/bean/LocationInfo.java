package com.scisdata.web.bean;

public class LocationInfo {

    private WifiEquipmentInfo wifiEquipmentInfo;
    private Double distance;

    public LocationInfo(WifiEquipmentInfo wifiEquipmentInfo, Double distance) {
        this.wifiEquipmentInfo = wifiEquipmentInfo;
        this.distance = distance;
    }

    public WifiEquipmentInfo getWifiEquipmentInfo() {
        return wifiEquipmentInfo;
    }

    public void setWifiEquipmentInfo(WifiEquipmentInfo point) {
        this.wifiEquipmentInfo = point;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
