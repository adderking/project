package com.scisdata.web.bean;

import com.zxm.load.utils.MapCaculateUtil;
import com.zxm.load.Direction;

public class GeoPoint {

    // 纬度
    private Double latitude;
    // 经度
    private Double longitude;

    public GeoPoint(Double latitude, Double langitude) {
        this.latitude = latitude;
        this.longitude = langitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLangitude() {
        return longitude;
    }

    public void setLangitude(Double langitude) {
        this.longitude = langitude;
    }


    public Direction getPointDirection(GeoPoint point) {

        double latitudeDiff = this.latitude - point.latitude;   // 纬度 判断南北 数值大的在北方
        double longitudeDiff = this.longitude - point.longitude;// 经度 判断东西 数值大的在东面
        double angle = MapCaculateUtil.getPointAngle(point, this);
        if(longitudeDiff > 0) {
            // 西侧
            if(latitudeDiff > 0) {
                // 南侧
                if(angle>=45) {
                    return Direction.WEST;
                } else {
                    return Direction.SOUTH;
                }
            } else {
                // 北侧
                if(angle>=45) {
                    return Direction.WEST;
                } else {
                    return Direction.NORTH;
                }
            }
        } else {
            // 东侧
            if(latitudeDiff > 0) {
                // 南侧
                if(angle>=45) {
                    return Direction.EAST;
                } else {
                    return Direction.SOUTH;
                }
            } else {
                // 北侧
                if(angle>=45) {
                    return Direction.EAST;
                } else {
                    return Direction.NORTH;
                }
            }
        }
    }

    public boolean isLocatedInNorth(GeoPoint Y) {
        Double latitude_X = this.getLatitude();
        Double latitude_Y = Y.getLatitude();
        if (latitude_X > latitude_Y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLocatedInSouth(GeoPoint Y) {
        Double latitude_X = this.getLatitude();
        Double latitude_Y = Y.getLatitude();
        if (latitude_X < latitude_Y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLocatedInEast(GeoPoint Y) {
        Double langitude_X = this.getLangitude();
        Double langitude_Y = Y.getLangitude();
        if (langitude_X > langitude_Y) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLocatedInWest(GeoPoint Y) {
        Double langitude_X = this.getLangitude();
        Double langitude_Y = Y.getLangitude();
        if (langitude_X < langitude_Y) {
            return true;
        } else {
            return false;
        }
    }

}
