package com.scisdata.web.controller;

import com.scisdata.web.Service.OrbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangshilei on 18/1/26.
 * 查询车辆、mac轨迹信息
 */
@Controller
@RequestMapping("/orbit")
public class OrbitController extends BaseController{
    @Autowired
    private OrbitService orbitService;
    /**
     * 查询MAC历史轨迹
     * @param mac
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/getHistoryOrbit")
    @ResponseBody
    public  Map<String,List<Map<String,String>>> getHistoryOrbit(String mac, String startDate, String endDate){
        //查询MAC轨迹信息
        List<Map<String,String>> list = this.orbitService.getHistoryOrbit(mac,startDate,endDate);
        //查询MAC设备信息
        List<Map<String,String>> enList = this.orbitService.getEquipementInfo(mac,startDate,endDate);
        Map<String,List<Map<String,String>>> map = new HashMap<>();
        map.put("orbit",list);
        map.put("enument",enList);
        return map;
    }

    /**
     * 查询车辆历史信息
     * @param mac
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/getCarHistoryOrbit")
    @ResponseBody
    public Map<String,List<Map<String,String>>> getCarHistoryOrbit(String mac, String startDate, String endDate){
        //查询车辆历史轨迹信息
        List<Map<String,String>> list = this.orbitService.getCarHistoryOrbit(mac,startDate,endDate);
        //查询车辆设备信息
        List<Map<String,String>> enList = this.orbitService.getCarEquipementInfo(mac,startDate,endDate);
        Map<String,List<Map<String,String>>> map = new HashMap<>();
        map.put("orbit",list);
        map.put("enument",enList);
        return map;
    }
}
