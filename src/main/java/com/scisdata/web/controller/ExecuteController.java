package com.scisdata.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.scisdata.web.Service.ExecuteService;
import com.scisdata.web.basedao.page.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangshilei on 18/1/31.
 * 查询布控信息
 */
@Controller
@RequestMapping("/execute")
public class ExecuteController extends BaseController{
    @Autowired
    private ExecuteService executeService;
    /**
     * 查询布控任务列表
     * @param pageNum
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getQQBKPage")
    @ResponseBody
    public DataStore<Map<String,Object>> getQQBKPage(int pageNum,int limit){
        return this.executeService.getQQBKPage(pageNum,limit);
    }
    @RequestMapping(value = "/insertQQBK")
    @ResponseBody
    public JSONObject insertQQBK(String taskName,String controlTarget,String startTime,String endTime,String taskType){
        boolean flag = this.executeService.insert(taskName,controlTarget,startTime,endTime,taskType);
        JSONObject jsonObject = new JSONObject();
        if(flag){
            jsonObject.put("msg","true");
        }else{
            jsonObject.put("msg","false");
        }
        return jsonObject;
    }
}
