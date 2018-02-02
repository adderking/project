package com.scisdata.web.Service;

import com.scisdata.web.bean.VideoEquipmentInfo;
import java.util.List;

/**
 *
 */
public interface VideoEquipmentInfoService {
    public List<VideoEquipmentInfo> queryAll();

    public List queryCoordinateAndLocationOfAll();
}
