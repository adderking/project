package com.scisdata.web.ServiceImpl;

import com.scisdata.web.Dao.ExecuteDao;
import com.scisdata.web.Service.ExecuteService;
import com.scisdata.web.basedao.page.DataStore;
import com.scisdata.web.basedao.page.PagingParameter;
import com.scisdata.web.exception.DaoAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by fangshilei on 18/1/31.
 */
@Service("executeService")
public class ExecuteServiceImpl implements ExecuteService{
    @Autowired
    private ExecuteDao executeDao;
    @Override
    public DataStore<Map<String,Object>> getQQBKPage(int pageNum,int limit) {
        PagingParameter paging = new PagingParameter(pageNum,limit);
        String sql = "select c.*,count(r.taskId) as totalCount from ControlTask c left join ControlResult r on " +
                "c.ID=r.taskId where c.controlType=0 group by r.taskId,c.ID ";
        try {
            DataStore<Map<String,Object>> dataStore =  this.executeDao.pageQuery(sql,paging);
            return dataStore;
        } catch (DaoAccessException e) {
            e.printStackTrace();
        }
        return new DataStore<>();
    }

    @Override
    public DataStore<Map<String, Object>> getDDBKPage(int pageNum, int limit) {
        PagingParameter paging = new PagingParameter(pageNum,limit);
        String sql = "select c.*,count(r.taskId) as totalCount from ControlTask c left join ControlResult r on " +
                "c.ID=r.taskId where c.controlType=1 group by r.taskId,c.ID ";
        try {
            DataStore<Map<String,Object>> dataStore =  this.executeDao.pageQuery(sql,paging);
            return dataStore;
        } catch (DaoAccessException e) {
            e.printStackTrace();
        }
        return new DataStore<>();
    }

    @Override
    public boolean insert(String taskName, String controlTarget, String startTime, String endTime,String taskType,String enqumentId,String controlType) {
        if(!StringUtils.isEmpty(taskName)&&!StringUtils.isEmpty(controlTarget)
                &&!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)&&!StringUtils.isEmpty(taskType)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("taskName",taskName);
            map.put("controlTarget",controlTarget);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("createTime",new Date());
            map.put("taskStatus",0);
            map.put("taskType",taskType);
            map.put("controlType",controlType);
            map.put("enqumentId",enqumentId);
            String sql = "insert into ControlTask(taskName,taskTarget,startTime,endTime,createTime,taskStatus,taskType,controlType,enqumentID) " +
                    "values (:taskName,:controlTarget,:startTime,:endTime,:createTime,:taskStatus,:taskType,:controlType,:enqumentId)";
            Long key = this.executeDao.insertBackId(sql,map);
            if(key>=0){
                return true;
            }else{
                return false;
            }
        }else{
            return  false;
        }
    }

    @Override
    public List<Map<String, String>> queryEqumentInfo(String val,String taskType) {
        List<Map<String,String>> list = new ArrayList<>();
        if("0".equals(taskType)){
            //查询车辆设备
            String sql = "select * from videoEquipmentInfo t where t.equipmentLocation in ("+val+")";
            list = this.executeDao.queryList(sql);
        }else{
            //查询车辆设备
            String sql = "select * from wifiEquipmentInfo w where w.equipmentLocation in ("+val+")";
            list = this.executeDao.queryList(sql);
        }
        return list;
    }
}
