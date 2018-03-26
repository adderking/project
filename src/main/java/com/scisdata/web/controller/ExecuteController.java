package com.scisdata.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.scisdata.web.Service.ExecuteService;
import com.scisdata.web.basedao.page.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

    @RequestMapping(value = "/getDDBKPage")
    @ResponseBody
    public DataStore<Map<String,Object>> getDDBKPage(int pageNum,int limit){
        return this.executeService.getDDBKPage(pageNum, limit);
    }
    @RequestMapping(value = "/insertQQBK")
    @ResponseBody
    public JSONObject insertQQBK(String taskName,String controlTarget,String startTime,String endTime,String taskType,String enqumentId){
        //先查询设备信息
        List<Map<String,String>> list = new ArrayList<>();
        boolean flag =false;
        if(!StringUtils.isEmpty(enqumentId)){
            list =  this.executeService.queryEqumentInfo(enqumentId,taskType);
            String val = "";
            for(Map<String,String> map:list){
                val = val + map.get("equipmentId")+",";
            }
            if(!"".equals(val)){
                val = val.substring(0,val.length()-1);
            }
            String controlType = "1";
            flag =this.executeService.insert(taskName,controlTarget,startTime,endTime,taskType,val,controlType);
        }else{
            flag =this.executeService.insert(taskName,controlTarget,startTime,endTime,taskType,"","0");
        }
        JSONObject jsonObject = new JSONObject();
        if(flag){
            jsonObject.put("msg","true");
        }else{
            jsonObject.put("msg","false");
        }
        return jsonObject;
    }
    @RequestMapping(value = "/getRenyuanInfo")
    @ResponseBody
    public DataStore<Map<String,Object>> getRenyuanInfo(int pageNum,int limit){
        List<Map<String,Object>> data = new ArrayList<>();
        for(int i=0;i<5;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("images", "/demo/images/renyuandangan/u522.png");
            map.put("name", "王超");
            map.put("ename", "wangchao");
            map.put("sex", "男");
            map.put("age", "35岁");
            map.put("mz", "汉族");
            map.put("sfhm", "110101199002190101");
            map.put("mobile", "13501234567");
            map.put("MAC", "08:9E:0A:45:34");
            map.put("whcd", "初中");
            map.put("zy", "教师");
            map.put("cphm", "京N 43AB8");
            map.put("hjdz", "北京市大兴区黄村二号院6楼");
            map.put("clpp", "上海大众");
            data.add(map);
        }
        DataStore<Map<String,Object>> dataStore = new DataStore<Map<String,Object>>(5,data,1,5);
        return dataStore;
    }
}
