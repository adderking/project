package com.kingcobra.test;

import com.scisdata.web.tools.ImportVideoEquipmentInfo;
import com.scisdata.web.tools.ImportWifiEquipmentInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ImportTest extends BaseTest {

    @Autowired
    private ImportVideoEquipmentInfo importVideoEquipmentInfo;

    @Autowired
    private ImportWifiEquipmentInfo importWifiEquipmentInfo;
    @Test
    public void importVideo() {
        String oneAndTwo_Period = "/Users/kingcobra/Downloads/unit.docx";
        int[] oneAndTwo_Period_metaIndex={2, 3, 4, 5, 6};
        boolean oneAndTwo_Period_IsSplit= true;
        String three_period = "/Users/kingcobra/Downloads/3period.docx";
        int[] three_period_metaIndex = {1, 2, 3, 4, 5};
        boolean three_peroid_metaIndex=false;
        try {
            //导入一期二期113系统点位
//            importVideoEquipmentInfo.parseFile(oneAndTwo_Period,oneAndTwo_Period_metaIndex,oneAndTwo_Period_IsSplit);
            //导入三期113系统点位
            importVideoEquipmentInfo.parseFile(three_period,three_period_metaIndex,three_peroid_metaIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void importWifi() {
        String wifiPoint = "/Users/kingcobra/Downloads/wifiPoints.xlsx";
        int[] metaIndex = {2, 3, 4, 5, 6, 7, 8, 9};
        try {
            importWifiEquipmentInfo.parseFile(wifiPoint, metaIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
