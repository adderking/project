package com.scisdata.web.Service;

import com.scisdata.web.basedao.page.DataStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangshilei on 18/1/31.
 */
public interface ExecuteService {
    public DataStore<Map<String,Object>> getQQBKPage(int pageNum, int limit);
    public boolean insert(String taskName, String controlTarget, String startTime, String endTime, String taskType);
}
