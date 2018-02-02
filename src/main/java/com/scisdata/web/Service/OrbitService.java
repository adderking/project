package com.scisdata.web.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by fangshilei on 18/1/26.
 */
public interface OrbitService {
    /**
     * 查询mac轨迹信息
     * @param mac
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String,String>> getHistoryOrbit(String mac,String startDate,String endDate);

    /**
     * 查询对应的设备信息
     * @param mac
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String,String>> getEquipementInfo(String mac,String startDate,String endDate);
    /**
     * 查询车辆轨迹历史信息
     * @param plate
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String,String>> getCarHistoryOrbit(String plate,String startDate,String endDate);

    /**
     * 查询车辆对应的设备信息
     * @param plate
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Map<String,String>> getCarEquipementInfo(String plate,String startDate,String endDate);

    /**
     * 查询全区布控结果表
     * @param id
     * @return
     */
    public List<Map<String,String>> getCarBKOrbit(String id);
    public List<Map<String, String>> getBKOrbit(String id);
}
