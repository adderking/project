package com.scisdata.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.scisdata.web.Service.VideoEquipmentInfoService;
import com.scisdata.web.Service.WifiEquipmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Author: kingcobra
 * createdate: 02/02/2018 - 15:33
 **/
@Controller
public class EquipmentInfoController {
    @Autowired
    private VideoEquipmentInfoService videoEquipmentInfoService;

    @Autowired
    private WifiEquipmentInfoService wifiEquipmentInfoService;

    @RequestMapping(path="/equipmentInfo")
    public String getUrl() {
        return "view/login/equipmentInfo";
    }

    @RequestMapping(path="/videoEquipLocaiton",method = RequestMethod.GET,produces = "applicaiton/json;charset=UTF-8")
    @ResponseBody
    public String getVideoEquipmentLocation() {
        List<Map<String, String>> videoEquipmentInfoList = videoEquipmentInfoService.queryCoordinateAndLocationOfAll();
        String results = JSONArray.toJSONString(videoEquipmentInfoList);
        return results;
    }

    @RequestMapping(path="/wifiEquipLocaiton",method = RequestMethod.GET,produces = "applicaiton/json;charset=UTF-8")
    @ResponseBody
    public String getWifiEquipmentLocation() {
        List<Map<String, String>> wifiEquipmentInfoList = wifiEquipmentInfoService.queryCoordinateAndLocationOfAll();
        String results = JSONArray.toJSONString(wifiEquipmentInfoList);
        return results;
    }
}
