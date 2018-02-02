package com.kingcobra.test;

import com.scisdata.web.controller.EquipmentInfoController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: kingcobra
 * <p>
 * createdate: 02/02/2018 - 15:45
 **/
public class ControllerTest extends BaseTest{

    @Autowired
    private EquipmentInfoController equipmentInfoController;

    @Test
    public void getVideoEquipMethodTest() {

        String json = equipmentInfoController.getVideoEquipmentLocation();
        System.out.println(json);
    }
    @Test
    public void getWifiEquipMethodTest() {

        String json = equipmentInfoController.getWifiEquipmentLocation();
        System.out.println(json);
    }
}
