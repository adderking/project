package com.zxm.load;

import com.scisdata.web.bean.*;
import com.zxm.load.utils.ConnectionUtil;
import com.zxm.load.utils.CreateInstanceUtil;
import com.zxm.load.utils.MapCaculateUtil;
import com.zxm.load.utils.SystemConfig;
import com.zxm.load.utils.Constants;
import org.apache.commons.collections.map.HashedMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RelationComputer {

    // 缓存wifi采集设备列表信息
    public List<WifiEquipmentInfo> wifiEquipmentInfos = new ArrayList<>();

    // 缓存车辆采集设备列表信息
    public List<VideoEquipmentInfo> videoEquipmentInfos = new ArrayList<>();

    // 视频采集设备周围所有wifi采集设备信息
    public Map<String, Map<Direction, LocationInfo>> equipmentNearestMap =
            new HashMap<>();

    public RelationComputer() {
        try {
            Connection conn = ConnectionUtil.getInstance().getConnection();
            this.wifiEquipmentInfos = cacheWifiEquipmentInfo(conn);
            this.videoEquipmentInfos = cacheVideoEquipmentInfo(conn);
            this.equipmentNearestMap = getAllEquipmentMap();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //缓存wifi采集设备信息
    private List<WifiEquipmentInfo> cacheWifiEquipmentInfo(Connection conn) throws SQLException {
        List<WifiEquipmentInfo> wifiEquipmentInfos = new ArrayList<>();
        String sql = "SELECT primaryId, equipmentId, equipmentLocation, latitude, langitude, status FROM wifiequipmentinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            WifiEquipmentInfo wifiEquipmentInfoInstance = CreateInstanceUtil.createWifiEquipmentInfoInstance(resultSet);
            wifiEquipmentInfos.add(wifiEquipmentInfoInstance);
        }
        resultSet.close();
        pst.close();
        return wifiEquipmentInfos;
    }

    //缓存车辆采集设备信息
    private List<VideoEquipmentInfo> cacheVideoEquipmentInfo(Connection conn) throws SQLException {
        List<VideoEquipmentInfo> videoEquipmentInfos = new ArrayList<>();
        String sql = "SELECT primaryId, equipmentId, equipmentLocation, latitude, langitude, direction, channel, area, blatitude, blangitude FROM videoequipmentinfo";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            VideoEquipmentInfo videoEquipmentInfoInstance = CreateInstanceUtil.createVideoEquipmentInfoInstance(resultSet);
            videoEquipmentInfos.add(videoEquipmentInfoInstance);
        }
        resultSet.close();
        pst.close();
        return videoEquipmentInfos;

    }

    public Map<String, Map<Direction, LocationInfo>> getAllEquipmentMap() {
        Map<String, Map<Direction, LocationInfo>> map = new HashMap<>();
        for (VideoEquipmentInfo videoEquipmentInfo : videoEquipmentInfos) {
            Map<Direction, LocationInfo> distanceInfo = constructDistanceInfo(videoEquipmentInfo);
            map.put(videoEquipmentInfo.getEquipmentId(), distanceInfo);
        }
        return map;
    }

    private Map<Direction, LocationInfo> constructDistanceInfo(VideoEquipmentInfo videoEquipmentInfo) {
        GeoPoint videoEquipmentPosition = new GeoPoint(videoEquipmentInfo.getLatitude(), videoEquipmentInfo.getLangitude());
        Map<Direction, LocationInfo> infos = new HashMap<>();
        double dd = Double.parseDouble(SystemConfig.getString(Constants.RELATION_COMPUTER_DISTANCE));
        for (WifiEquipmentInfo wifiEquipmentInfo : wifiEquipmentInfos) {
            GeoPoint wifiEquipmentPosition = new GeoPoint(wifiEquipmentInfo.getLatitude(), wifiEquipmentInfo.getLangitude());
            Double distance = MapCaculateUtil.GetShortDistance(videoEquipmentPosition, wifiEquipmentPosition);
            if (distance > dd) {
                continue;
            }
            Direction direction = videoEquipmentPosition.getPointDirection(wifiEquipmentPosition);
            if(!infos.containsKey(direction)) {
                infos.put(direction, new LocationInfo(wifiEquipmentInfo, distance));
            } else {
                LocationInfo locationInfo = infos.get(direction);
                if(locationInfo.getDistance() > distance) {
                    infos.put(direction, new LocationInfo(wifiEquipmentInfo, distance));
                }
            }
        }
        return infos;
    }

    public Map<String, DistanceRange> getRanges(VideoEquipmentInfo videoEquipmentInfo) {
        LocationInfo fromLocationInfo;
        LocationInfo toLocationInfo;
        LocationInfo rightLocationInfo;
        LocationInfo leftLocationInfo;
        Map<String, DistanceRange> ranges = new HashedMap();
        if(videoEquipmentInfo == null) {
            return ranges;
        }
        String localDirection = videoEquipmentInfo.getDirection();
        if(localDirection == null) return ranges;
        Direction dd = Direction.val(localDirection);
        if (videoEquipmentInfo != null && dd != null) {
            Map<Direction, LocationInfo> locationInfoMap =
                    equipmentNearestMap.get(videoEquipmentInfo.getEquipmentId());
            switch (dd) {
                case NORTH_TO_SOUTH :
                    fromLocationInfo = locationInfoMap.get(Direction.NORTH);
                    setRanges(videoEquipmentInfo, fromLocationInfo, "FROM", ranges);
                    toLocationInfo = locationInfoMap.get(Direction.SOUTH);
                    setRanges(videoEquipmentInfo, toLocationInfo, "TO", ranges);
                    rightLocationInfo = locationInfoMap.get(Direction.WEST);
                    leftLocationInfo = locationInfoMap.get(Direction.EAST);
                    setBesideRange(videoEquipmentInfo, rightLocationInfo, Direction.NORTH_TO_SOUTH, ranges);
                    setBesideRange(videoEquipmentInfo, leftLocationInfo, Direction.NORTH_TO_SOUTH, ranges);
                    break;
                case SOUTH_TO_NORTH :
                    fromLocationInfo = locationInfoMap.get(Direction.SOUTH);
                    setRanges(videoEquipmentInfo, fromLocationInfo, "FROM", ranges);
                    toLocationInfo = locationInfoMap.get(Direction.NORTH);
                    setRanges(videoEquipmentInfo, toLocationInfo, "TO", ranges);
                    rightLocationInfo = locationInfoMap.get(Direction.EAST);
                    leftLocationInfo = locationInfoMap.get(Direction.WEST);
                    setBesideRange(videoEquipmentInfo, rightLocationInfo, Direction.SOUTH_TO_NORTH, ranges);
                    setBesideRange(videoEquipmentInfo, leftLocationInfo, Direction.SOUTH_TO_NORTH, ranges);
                    break;
                case EAST_TO_WEST :
                    fromLocationInfo = locationInfoMap.get(Direction.EAST);
                    setRanges(videoEquipmentInfo, fromLocationInfo, "FROM", ranges);
                    toLocationInfo = locationInfoMap.get(Direction.WEST);
                    setRanges(videoEquipmentInfo, toLocationInfo, "TO", ranges);
                    rightLocationInfo = locationInfoMap.get(Direction.NORTH);
                    leftLocationInfo = locationInfoMap.get(Direction.SOUTH);
                    setBesideRange(videoEquipmentInfo, rightLocationInfo, Direction.EAST_TO_WEST, ranges);
                    setBesideRange(videoEquipmentInfo, leftLocationInfo, Direction.EAST_TO_WEST, ranges);
                    break;
                case WEST_TO_EAST :
                    fromLocationInfo = locationInfoMap.get(Direction.WEST);
                    setRanges(videoEquipmentInfo, fromLocationInfo, "FROM", ranges);
                    toLocationInfo = locationInfoMap.get(Direction.EAST);
                    setRanges(videoEquipmentInfo, toLocationInfo, "TO", ranges);
                    rightLocationInfo = locationInfoMap.get(Direction.SOUTH);
                    leftLocationInfo = locationInfoMap.get(Direction.NORTH);
                    setBesideRange(videoEquipmentInfo, rightLocationInfo, Direction.WEST_TO_EAST, ranges);
                    setBesideRange(videoEquipmentInfo, leftLocationInfo, Direction.WEST_TO_EAST, ranges);
                    break;
            }

        }
        return ranges;
    }

    private void setBesideRange(VideoEquipmentInfo videoEquipmentInfo, LocationInfo wifiLocationInfo,
                                Direction direction, Map<String, DistanceRange> ranges) {
        if(wifiLocationInfo == null) return;
        GeoPoint wifiEquipmentPoint = new GeoPoint(wifiLocationInfo.getWifiEquipmentInfo().getLatitude(),
                wifiLocationInfo.getWifiEquipmentInfo().getLangitude());
        GeoPoint videoEquipmentPoint = new GeoPoint(videoEquipmentInfo.getLatitude(),
                videoEquipmentInfo.getLangitude());

        switch (direction) {
            case NORTH_TO_SOUTH :
                if (wifiEquipmentPoint.isLocatedInNorth(videoEquipmentPoint)) {
                    setRanges(videoEquipmentInfo, wifiLocationInfo, "FROM", ranges);
                } else {
                    setRanges(videoEquipmentInfo, wifiLocationInfo, "TO", ranges);
                }
                break;
            case SOUTH_TO_NORTH :
                if (wifiEquipmentPoint.isLocatedInSouth(videoEquipmentPoint)) {
                    setRanges(videoEquipmentInfo, wifiLocationInfo, "FROM", ranges);
                } else {
                    setRanges(videoEquipmentInfo, wifiLocationInfo, "TO", ranges);
                }
                break;
            case EAST_TO_WEST :
                if (wifiEquipmentPoint.isLocatedInEast(videoEquipmentPoint)) {
                    setRanges(videoEquipmentInfo, wifiLocationInfo, "FROM", ranges);
                } else {
                    setRanges(videoEquipmentInfo, wifiLocationInfo, "TO", ranges);
                }
                break;
            case WEST_TO_EAST :
                if (wifiEquipmentPoint.isLocatedInWest(videoEquipmentPoint)) {
                    setRanges(videoEquipmentInfo, wifiLocationInfo, "FROM", ranges);
                } else {
                    setRanges(videoEquipmentInfo, wifiLocationInfo, "TO", ranges);
                }
                break;
        }
    }

    private void setRanges(VideoEquipmentInfo videoEquipmentInfo, LocationInfo wifiLocationInfo,
                           String direction, Map<String, DistanceRange> ranges) {
        if(wifiLocationInfo == null) return;
        String rangeKey = videoEquipmentInfo.getEquipmentId()+"_"
                +wifiLocationInfo.getWifiEquipmentInfo().getEquipmentId();
        if("FROM".equals(direction)) {
            ranges.put(rangeKey, new DistanceRange(wifiLocationInfo.getDistance(), DistanceRange.DrivingDirection.FROM));
        } else {
            ranges.put(rangeKey, new DistanceRange(wifiLocationInfo.getDistance(), DistanceRange.DrivingDirection.TO));
        }
    }

}






