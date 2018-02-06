package com.wsh.tools;

import com.scisdata.web.bean.*;
import com.scisdata.web.enumeration.CarDirection;
import com.scisdata.web.util.DateUtils;
import com.wsh.tools.utils.ConnectionUtil;
import com.wsh.tools.utils.CreateInstanceUtil;
import com.wsh.tools.utils.MapCaculateUtil;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class CarMacRelation {

    private static final int CARSPEED = 17; //单位是 m/s
    private static final String FORMATER = "yyyy-MM-dd HH:mm:ss";
    private static final int WIFIRADIUS = 300;
    private static final int errorTime = (int) (WIFIRADIUS / CARSPEED);
    private static final int DISTANCE = 1000;
    private enum DIRECTION {FROM, TO}
    private List<WifiEquipmentInfo> wifiEquipmentInfoList = new ArrayList<>();
    private List<VideoEquipmentInfo> videoEquipmentInfoList = new ArrayList<>();
    private Map<String, WifiEquipmentInfo> wifiEquipmentId2WifiEquipmentInfoMap = new HashMap<>();
    private Map<String, VideoEquipmentInfo> videoEquipmentId2VideoEquipmentInfoMap = new HashMap<>();
    private Map<String, NearestLocation> videoEquipmentId2NearestWifiLocationMap = new HashMap<>();
    private Map<String, Map<String, List<MacTrace>>> equipmentId2MacTimeLineMap = new HashMap<>();
    private Map<String, Map<String, List<CarTrace>>> equipmentId2CarTimeLineMap = new HashMap<>();
    private Map<String, Integer> carMacBundleApperenceTimes = new HashMap<>();

    public CarMacRelation() throws SQLException {
        Connection conn = ConnectionUtil.getInstance().getConnection();
        cacheWifiEqipmentInfo(conn);
        cacheVideoEquipmentInfo(conn);
        cacheEquipment2TimeLineMacTraceInstancesMap(conn);
        cacheEquipment2TimeLineCarTraceInstancesMap(conn);
        cacheNearestWifiEquipmentLocationOfEachDirectionForVideoEquipment();
        conn.close();
    }

    private void cacheWifiEqipmentInfo(Connection conn) throws SQLException {
        String sql = "SELECT primaryId, equipmentId, equipmentLocation, latitude, langitude, status FROM wifiequipmentinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            WifiEquipmentInfo wifiEquipmentInfoInstance = CreateInstanceUtil.createWifiEquipmentInfoInstance(resultSet);
            wifiEquipmentInfoList.add(wifiEquipmentInfoInstance);
            wifiEquipmentId2WifiEquipmentInfoMap.put(wifiEquipmentInfoInstance.getEquipmentId(), wifiEquipmentInfoInstance);
        }
        resultSet.close();
        pst.close();
    }

    private void cacheVideoEquipmentInfo(Connection conn) throws SQLException {
        String sql = "SELECT primaryId, equipmentId, equipmentLocation, latitude, langitude, direction, channel, area, blatitude, blangitude FROM videoequipmentinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            VideoEquipmentInfo videoEquipmentInfoInstance = CreateInstanceUtil.createVideoEquipmentInfoInstance(resultSet);
            videoEquipmentInfoList.add(videoEquipmentInfoInstance);
            videoEquipmentId2VideoEquipmentInfoMap.put(videoEquipmentInfoInstance.getEquipmentId(), videoEquipmentInfoInstance);
        }
        resultSet.close();
        pst.close();
    }

    private void cacheEquipment2TimeLineMacTraceInstancesMap(Connection conn) throws SQLException {
        for (WifiEquipmentInfo wifiEquipmentInfo : wifiEquipmentInfoList) {
            cacheEachEquipmentId2TimeLineMacTraceInstancesMap(conn, wifiEquipmentInfo.getEquipmentId());
        }
    }

    private void cacheEquipment2TimeLineCarTraceInstancesMap(Connection conn) throws SQLException {
        for (VideoEquipmentInfo videoEquipmentInfo : videoEquipmentInfoList) {
            cacheEachEquipmentId2TimeLineCarTraceInstancesMap(conn, videoEquipmentInfo.getEquipmentId());
        }
    }

    private void cacheEachEquipmentId2TimeLineMacTraceInstancesMap(Connection conn, String equipmentId) throws SQLException {
        Map<String, List<MacTrace>> timePoint2MacTraceList = new HashMap<>();
        String sql = "SELECT primaryId, macId, equipmentId, equipmentLocation, startTime, endTime, latitude, langitude, createTime FROM mactrace WHERE equipmentId = ? ORDER BY startTime asc";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, equipmentId);
        ResultSet resultSet = pst.executeQuery();
        String timePoint = null;
        while (resultSet.next()) {
            final MacTrace macTraceInstance = CreateInstanceUtil.createMacTraceInstance(resultSet);
            Date startTime = macTraceInstance.getStartTime();
            String startTimeStr = DateUtils.formatDateByFormat(startTime, FORMATER);
            if (timePoint == null || !timePoint.equals(startTimeStr)) {
                List<MacTrace> macTraceList = new ArrayList<>();
                macTraceList.add(macTraceInstance);
                timePoint2MacTraceList.put(startTimeStr, macTraceList);
            } else {
                List<MacTrace> macTraceList = timePoint2MacTraceList.get(startTimeStr);
                macTraceList.add(macTraceInstance);
            }
        }
        resultSet.close();
        pst.close();
        equipmentId2MacTimeLineMap.put(equipmentId, timePoint2MacTraceList);
    }

    private void cacheEachEquipmentId2TimeLineCarTraceInstancesMap(Connection conn, String equipmentId) throws SQLException {
        Map<String, List<CarTrace>> timePoint2CarTraceList = new HashMap<>();
        String sql = "SELECT primaryId, carPlateId, equipmentId, equipmentLocation, startTime, latitude, langitude, createTime, endTime FROM cartrace WHERE equipmentId = ? ORDER BY startTime asc";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, equipmentId);
        ResultSet resultSet = pst.executeQuery();
        String timePoint = null;
        while (resultSet.next()) {
            final CarTrace carTraceInstance = CreateInstanceUtil.createCarTraceInstance(resultSet);
            Date startTime = carTraceInstance.getStartTime();
            String startTimeStr = DateUtils.formatDateByFormat(startTime, FORMATER);
            if (timePoint == null || !timePoint.equals(startTimeStr)) {
                List<CarTrace> carTraceList = new ArrayList<>();
                carTraceList.add(carTraceInstance);
                timePoint2CarTraceList.put(startTimeStr, carTraceList);
            } else {
                List<CarTrace> carTraceList = timePoint2CarTraceList.get(startTimeStr);
                carTraceList.add(carTraceInstance);
            }
        }
        resultSet.close();
        pst.close();
        equipmentId2CarTimeLineMap.put(equipmentId, timePoint2CarTraceList);
    }

    private void cacheNearestWifiEquipmentLocationOfEachDirectionForVideoEquipment() {
        for (VideoEquipmentInfo videoEquipmentInfo : videoEquipmentInfoList) {
            videoEquipmentId2NearestWifiLocationMap.put(videoEquipmentInfo.getEquipmentId(), getNearestWifiEquipmentOfEachDirectionForGivenVideoEquipment(videoEquipmentInfo));
        }
    }

    private NearestLocation getNearestWifiEquipmentOfEachDirectionForGivenVideoEquipment(VideoEquipmentInfo videoEquipmentInfo) {
        GeoPoint videoEquipmentPoint = new GeoPoint(videoEquipmentInfo.getOriginLatitude(), videoEquipmentInfo.getOriginLangitude());
        LocationInfo nearestNorthInfo = null;
        LocationInfo nearestSouthInfo = null;
        LocationInfo nearestEastInfo = null;
        LocationInfo nearestWestInfo = null;
        for (WifiEquipmentInfo wifiEquipmentInfo : wifiEquipmentInfoList) {
            GeoPoint wifiEquipmentPoint = new GeoPoint(wifiEquipmentInfo.getLatitude(), wifiEquipmentInfo.getLangitude());
            Double distance = MapCaculateUtil.GetShortDistance(videoEquipmentPoint, wifiEquipmentPoint);
            if (distance > DISTANCE) {
                continue;
            }
            if (wifiEquipmentPoint.isLocatedInNorth(videoEquipmentPoint)) {
                if (null == nearestNorthInfo) {
                    nearestNorthInfo = new LocationInfo(wifiEquipmentInfo, distance);
                } else if (distance < nearestNorthInfo.getDistance()) {
                    nearestNorthInfo.setDistance(distance);
                }
            } else if (wifiEquipmentPoint.isLocatedInSouth(videoEquipmentPoint)) {
                if (null == nearestSouthInfo) {
                    nearestSouthInfo = new LocationInfo(wifiEquipmentInfo, distance);
                } else if (distance < nearestSouthInfo.getDistance()) {
                    nearestSouthInfo.setDistance(distance);
                }
            } else if (wifiEquipmentPoint.isLocatedInEast(videoEquipmentPoint)) {
                if (null == nearestEastInfo) {
                    nearestEastInfo = new LocationInfo(wifiEquipmentInfo, distance);
                } else if (distance < nearestEastInfo.getDistance()) {
                    nearestEastInfo.setDistance(distance);
                }
            } else if (wifiEquipmentPoint.isLocatedInWest(videoEquipmentPoint)) {
                if (null == nearestWestInfo) {
                    nearestWestInfo = new LocationInfo(wifiEquipmentInfo, distance);
                } else if (distance < nearestWestInfo.getDistance()) {
                    nearestWestInfo.setDistance(distance);
                }
            }
        }
        return new NearestLocation(nearestNorthInfo, nearestSouthInfo, nearestEastInfo, nearestWestInfo);
    }

    private void analysis() {
        Set<String> videoEquipmentIds = videoEquipmentId2NearestWifiLocationMap.keySet();
        for (String videoEquipmentId : videoEquipmentIds) {
            if (videoEquipmentId != null) {
                VideoEquipmentInfo videoEquipmentInfo = videoEquipmentId2VideoEquipmentInfoMap.get(videoEquipmentId);
                GeoPoint videoEquipmentPoint = new GeoPoint(videoEquipmentInfo.getOriginLatitude(), videoEquipmentInfo.getOriginLangitude());
                Map<String, List<CarTrace>> timePoint2CarTracesMap = equipmentId2CarTimeLineMap.get(videoEquipmentId);
                NearestLocation nearestLocation = videoEquipmentId2NearestWifiLocationMap.get(videoEquipmentId);
                Set<String> timePoints = timePoint2CarTracesMap.keySet();
                for (String timePoint : timePoints) {
                    List<CarTrace> carTraces = timePoint2CarTracesMap.get(timePoint);
                    String direction = videoEquipmentInfo.getDirection();
                    if (direction.equals(CarDirection.北向南)) {
                        LocationInfo northNearest = nearestLocation.getNorthNearest();
                        if (null != northNearest) {
                            bundle(northNearest, timePoint, carTraces, DIRECTION.FROM.name());
                        }
                        LocationInfo southNearest = nearestLocation.getSouthNearest();
                        if (null != southNearest) {
                            bundle(southNearest, timePoint, carTraces, DIRECTION.TO.name());
                        }
                        LocationInfo eastNearest = nearestLocation.getEastNearest();
                        if (null != eastNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(eastNearest.getWifiEquipmentInfo().getLatitude(), eastNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInNorth(videoEquipmentPoint)) {
                                bundle(eastNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(eastNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                        LocationInfo westNearest = nearestLocation.getWestNearest();
                        if (null != westNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(westNearest.getWifiEquipmentInfo().getLatitude(), westNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInNorth(videoEquipmentPoint)) {
                                bundle(westNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(westNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }

                    } else if (direction.equals(CarDirection.南向北)) {
                        LocationInfo southNearest = nearestLocation.getSouthNearest();
                        if (null != southNearest) {
                            bundle(southNearest, timePoint, carTraces, DIRECTION.FROM.name());
                        }
                        LocationInfo northNearest = nearestLocation.getNorthNearest();
                        if (null != northNearest) {
                            bundle(northNearest, timePoint, carTraces, DIRECTION.TO.name());
                        }
                        LocationInfo eastNearest = nearestLocation.getEastNearest();
                        if (null != eastNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(eastNearest.getWifiEquipmentInfo().getLatitude(), eastNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInSouth(videoEquipmentPoint)) {
                                bundle(eastNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(eastNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                        LocationInfo westNearest = nearestLocation.getWestNearest();
                        if (null != westNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(westNearest.getWifiEquipmentInfo().getLatitude(), westNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInSouth(videoEquipmentPoint)) {
                                bundle(westNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(westNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                    } else if (direction.equals(CarDirection.东向西)) {
                        LocationInfo eastNearest = nearestLocation.getEastNearest();
                        if (null != eastNearest) {
                            bundle(eastNearest, timePoint, carTraces, DIRECTION.FROM.name());
                        }
                        LocationInfo westNearest = nearestLocation.getWestNearest();
                        if (null != westNearest) {
                            bundle(westNearest, timePoint, carTraces, DIRECTION.TO.name());
                        }
                        LocationInfo northNearest = nearestLocation.getNorthNearest();
                        if (null != northNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(northNearest.getWifiEquipmentInfo().getLatitude(), northNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInEast(videoEquipmentPoint)) {
                                bundle(northNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(northNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                        LocationInfo southNearest = nearestLocation.getSouthNearest();
                        if (null != southNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(southNearest.getWifiEquipmentInfo().getLatitude(), southNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInEast(videoEquipmentPoint)) {
                                bundle(southNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(southNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                    } else if (direction.equals(CarDirection.西向东)) {
                        LocationInfo westNearest = nearestLocation.getWestNearest();
                        if (null != westNearest) {
                            bundle(westNearest, timePoint, carTraces, DIRECTION.FROM.name());
                        }
                        LocationInfo eastNearest = nearestLocation.getEastNearest();
                        if (null != eastNearest) {
                            bundle(eastNearest, timePoint, carTraces, DIRECTION.TO.name());
                        }
                        LocationInfo northNearest = nearestLocation.getNorthNearest();
                        if (null != northNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(northNearest.getWifiEquipmentInfo().getLatitude(), northNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInWest(videoEquipmentPoint)) {
                                bundle(northNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(northNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                        LocationInfo southNearest = nearestLocation.getSouthNearest();
                        if (null != southNearest) {
                            GeoPoint wifiEquipmentPoint = new GeoPoint(southNearest.getWifiEquipmentInfo().getLatitude(), southNearest.getWifiEquipmentInfo().getLangitude());
                            if (wifiEquipmentPoint.isLocatedInWest(videoEquipmentPoint)) {
                                bundle(southNearest, timePoint, carTraces, DIRECTION.FROM.name());
                            } else {
                                bundle(southNearest, timePoint, carTraces, DIRECTION.TO.name());
                            }
                        }
                    }
                }
            }
        }
    }

    private void bundle (LocationInfo locationInfo, String timePoint, List<CarTrace> carTraces, String direction) {
        int timeGap = (int) (locationInfo.getDistance() / CARSPEED);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.formatDate(timePoint, FORMATER));
        if (direction.equals(DIRECTION.FROM.name())) {
            calendar.add(Calendar.SECOND, -timeGap);
        } else {
            calendar.add(Calendar.SECOND, timeGap);
        }
        calendar.add(Calendar.SECOND, errorTime);
        String maxTimePoint = DateUtils.formatDateByFormat(calendar.getTime(), FORMATER);
        calendar.add(Calendar.SECOND, - (2 * errorTime));
        String minTimePoint = DateUtils.formatDateByFormat(calendar.getTime(), FORMATER);
        Map<String, List<MacTrace>> timePoint2MacTracesMap = equipmentId2MacTimeLineMap.get(locationInfo.getWifiEquipmentInfo().getEquipmentId());
        Set<String> macTimePoints = timePoint2MacTracesMap.keySet();
        for (String macTimePoint : macTimePoints) {
            boolean isInTimeZone = isInTimeZone(macTimePoint, minTimePoint, maxTimePoint);
            if (isInTimeZone) {
                List<MacTrace> macTraces = timePoint2MacTracesMap.get(macTimePoint);
                bundleCarMac(carTraces, macTraces);
            }
        }
    }

    private boolean isInTimeZone(String timePoint, String minTimePoint, String maxTimePoint) {
        if (timePoint.compareTo(minTimePoint) >= 0 && timePoint.compareTo(maxTimePoint) <= 0) {
            return true;
        } else {
            return false;
        }
    }

    private void bundleCarMac(List<CarTrace> carTraces, List<MacTrace> macTraces) {
        for (CarTrace carTrace : carTraces) {
            String carPlateId = carTrace.getCarPlateId();
            for (MacTrace macTrace : macTraces) {
                String macTraceId = macTrace.getMacId();
                String bundle = carPlateId + "_" + macTraceId;
                if (carMacBundleApperenceTimes.containsKey(bundle)) {
                    carMacBundleApperenceTimes.put(bundle, carMacBundleApperenceTimes.get(bundle) + 1);
                } else {
                    carMacBundleApperenceTimes.put(bundle, 1);
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        CarMacRelation carMacRelation = new CarMacRelation();
        System.out.println(1);
    }

}
