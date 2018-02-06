package com.wsh.tools.utils;

import com.scisdata.web.bean.GeoPoint;

public class MapCaculateUtil {

    static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI= 6.28318530712; // 2*PI
    static double DEF_PI180= 0.01745329252; // PI/180.0
    static double DEF_R =6370693.5; // radius of earth
    //适用于近距离
    public static double GetShortDistance(GeoPoint X, GeoPoint Y)
    {
        double lon1 = X.getLangitude();
        double lat1 = X.getLatitude();
        double lon2 = Y.getLangitude();
        double lat2 = Y.getLatitude();
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    public static void main(String[] args) {
        double mLat1 = 39.90923; // point1纬度
        double mLon1 = 116.357428; // point1经度
        double mLat2 = 39.90923;// point2纬度
        double mLon2 = 116.397428;// point2经度
        double distance = MapCaculateUtil.GetShortDistance(new GeoPoint(mLat1, mLon1), new GeoPoint(mLat2, mLon2));
        System.out.println(distance);
    }

}
