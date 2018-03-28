package com.zxm.load.test;

import com.scisdata.web.bean.VideoEquipmentInfo;
import com.zxm.load.DistanceRange;
import com.zxm.load.RelationComputer;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RelationComputerTest {

    @Test
    public void computeEquipmentRelation() {
        RelationComputer compute = new RelationComputer();
        List<VideoEquipmentInfo> videoEquipmentInfos = compute.videoEquipmentInfos;
        for(VideoEquipmentInfo info : videoEquipmentInfos) {
            Map<String, DistanceRange> ranges = compute.getRanges(info);
            Iterator<String> it = ranges.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next();
                System.out.println(key + "  " + ranges.get(key));
            }
        }
    }

}
