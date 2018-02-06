package com.scisdata.web.bean;

public class GeoPoint {

    private Double latitude;
    private Double langitude;

    public GeoPoint(Double latitude, Double langitude) {
        this.latitude = latitude;
        this.langitude = langitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLangitude() {
        return langitude;
    }

    public void setLangitude(Double langitude) {
        this.langitude = langitude;
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
