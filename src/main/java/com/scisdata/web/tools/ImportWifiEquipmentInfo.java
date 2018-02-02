package com.scisdata.web.tools;

import com.scisdata.web.Dao.WifiEquipmentInfoDao;
import com.scisdata.web.bean.WifiEquipmentInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;

@Component("importWifiEquipmentInfo")
public class ImportWifiEquipmentInfo {
    private static final String factoryCode = "723005104";
    @Autowired
    private WifiEquipmentInfoDao wifiEquipmentInfoDao;

    /**
     * 解析excel文件
     * @param file
     * @throws Exception
     */
    public void parseFile(String file,int[] metaIndex) throws Exception {
        InputStream inputStream = new FileInputStream(file);
        Workbook workBook = WorkbookFactory.create(inputStream);
        Sheet firstSheet =  workBook.getSheetAt(0);
        int rows = firstSheet.getPhysicalNumberOfRows();
        for(int i=1;i<rows;i++) {
            Row row = firstSheet.getRow(i);
            rowHandler(row,metaIndex);
        }
        inputStream.close();
    }

    /**
     * 处理单行
     * @param row
     */
    private void rowHandler(Row row,int[] metaIndex) {
        String locationCode=null,equipmentId=null,equipmentLocation=null,equipmentMac=null,locationName=null,locationType=null,
                locationSubType=null;
        double latitude=0d,langitude=0d;
        int cols = row.getPhysicalNumberOfCells();
        for(int i : metaIndex){
            switch (i) {
                case 2: locationName = row.getCell(i).getStringCellValue();break;
                case 3: equipmentLocation = row.getCell(i).getStringCellValue();break;
                case 4:locationType = row.getCell(i).getStringCellValue();break;
                case 5:locationSubType= row.getCell(i).getStringCellValue();break;
                case 6: equipmentMac =  row.getCell(i).getStringCellValue();break;
                case 7: langitude =  row.getCell(i).getNumericCellValue();break;
                case 8: latitude =  row.getCell(i).getNumericCellValue();break;
                case 9: locationCode = row.getCell(i).getStringCellValue();break;
                default:break;
            }
        }
        equipmentId = factoryCode + equipmentMac.replace("-", "");
        WifiEquipmentInfo wifiEquipmentInfo = new WifiEquipmentInfo();
        wifiEquipmentInfo.setEquipmentId(equipmentId);
        wifiEquipmentInfo.setEquipmentLocation(equipmentLocation);
        wifiEquipmentInfo.setEquipmentMac(equipmentMac);
        wifiEquipmentInfo.setLangitude(langitude);
        wifiEquipmentInfo.setLatitude(latitude);
        wifiEquipmentInfo.setLocationCode(locationCode);
        wifiEquipmentInfo.setLocationName(locationName);
        wifiEquipmentInfo.setLocationType(locationType);
        wifiEquipmentInfo.setLocationSubType(locationSubType);
        wifiEquipmentInfoDao.insert(wifiEquipmentInfo);
    }


}
