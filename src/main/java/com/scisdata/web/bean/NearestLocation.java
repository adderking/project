package com.scisdata.web.bean;

public class NearestLocation {

    private LocationInfo northNearest;
    private LocationInfo southNearest;
    private LocationInfo eastNearest;
    private LocationInfo westNearest;

    public NearestLocation(LocationInfo northNearest, LocationInfo southNearest, LocationInfo eastNearest, LocationInfo westNearest) {
        this.northNearest = northNearest;
        this.southNearest = southNearest;
        this.eastNearest = eastNearest;
        this.westNearest = westNearest;
    }

    public LocationInfo getNorthNearest() {
        return northNearest;
    }

    public void setNorthNearest(LocationInfo northNearest) {
        this.northNearest = northNearest;
    }

    public LocationInfo getSouthNearest() {
        return southNearest;
    }

    public void setSouthNearest(LocationInfo southNearest) {
        this.southNearest = southNearest;
    }

    public LocationInfo getEastNearest() {
        return eastNearest;
    }

    public void setEastNearest(LocationInfo eastNearest) {
        this.eastNearest = eastNearest;
    }

    public LocationInfo getWestNearest() {
        return westNearest;
    }

    public void setWestNearest(LocationInfo westNearest) {
        this.westNearest = westNearest;
    }

}
