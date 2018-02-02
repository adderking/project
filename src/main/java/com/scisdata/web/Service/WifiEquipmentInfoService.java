package com.scisdata.web.Service;

import com.scisdata.web.bean.WifiEquipmentInfo;

import java.util.List;

public interface WifiEquipmentInfoService {
    public List<WifiEquipmentInfo> queryAll();

    public List queryCoordinateAndLocationOfAll();
}
