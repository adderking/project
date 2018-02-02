package com.scisdata.web.ServiceImpl;

import com.scisdata.web.Dao.VideoEquipmentInfoDao;
import com.scisdata.web.Dao.WifiEquipmentInfoDao;
import com.scisdata.web.Service.VideoEquipmentInfoService;
import com.scisdata.web.Service.WifiEquipmentInfoService;
import com.scisdata.web.bean.VideoEquipmentInfo;
import com.scisdata.web.bean.WifiEquipmentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("videoEquipmentInfoService")
public class VideoEquipmentInfoServiceImpl implements VideoEquipmentInfoService {

    @Autowired
    private VideoEquipmentInfoDao videoEquipmentInfoDao;

    public List<VideoEquipmentInfo> queryAll() {
        String sql = "select * from videoEquipmentInfo";
        List<VideoEquipmentInfo> videoEquipmentInfoList = videoEquipmentInfoDao.queryList(sql);
        return videoEquipmentInfoList;
    }

    @Override
    public List<Map<String,String>> queryCoordinateAndLocationOfAll() {
        String sql = "select latitude,langitude,equipmentLocation,equipmentId from videoEquipmentInfo";
        List<Map<String,String>> videoEquipmentInfoList = videoEquipmentInfoDao.queryList(sql);
        return videoEquipmentInfoList;
    }
}
