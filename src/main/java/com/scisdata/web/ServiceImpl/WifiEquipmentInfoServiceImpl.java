package com.scisdata.web.ServiceImpl;

import com.scisdata.web.Dao.WifiEquipmentInfoDao;
import com.scisdata.web.Service.WifiEquipmentInfoService;
import com.scisdata.web.bean.WifiEquipmentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("wifiEquipmentInfoService")
public class WifiEquipmentInfoServiceImpl implements WifiEquipmentInfoService {

    @Autowired
    private WifiEquipmentInfoDao wifiEquipmentInfoDao;

    public List<WifiEquipmentInfo> queryAll() {
        String sql = "select * from wifiEquipmentInfo";

        List<WifiEquipmentInfo> wifiEquipmentInfoList = wifiEquipmentInfoDao.queryList(sql);
        return wifiEquipmentInfoList;
    }

    @Override
    public List<Map<String,String>> queryCoordinateAndLocationOfAll() {
        String sql = "select latitude,langitude,equipmentLocation,equipmentId from wifiEquipmentInfo";
        List<Map<String,String>> wifiEquipmentInfoList = wifiEquipmentInfoDao.queryList(sql);
        return wifiEquipmentInfoList;
    }
}
