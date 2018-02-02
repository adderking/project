package com.kingcobra.test;

import com.scisdata.web.Service.VideoEquipmentInfoService;
import com.scisdata.web.Service.WifiEquipmentInfoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class ServiceTest extends BaseTest {

    @Autowired
    private WifiEquipmentInfoService wifiEquipmentInfoService;

    @Autowired
    private VideoEquipmentInfoService videoEquipmentInfoService;

    @Test
    public void queryWifiTest() {
        List<Map<String,String>> wifiEquipmentList = wifiEquipmentInfoService.queryCoordinateAndLocationOfAll();
        for(int i=0;i<wifiEquipmentList.size();i++) {
            Map<String,String> o  =  wifiEquipmentList.get(i);
            System.out.println(o);
        }
    }
    @Test
    public void queryVideoTest() {
        List<Map<String,String>> videoEquipmentList = videoEquipmentInfoService.queryCoordinateAndLocationOfAll();
        for(int i=0;i<videoEquipmentList.size();i++) {
            Map<String,String> o  =  videoEquipmentList.get(i);
            System.out.println(o);
        }
    }
}
