package com.scisdata.web.ServiceImpl;

import com.scisdata.web.Dao.OrbitDao;
import com.scisdata.web.Service.OrbitService;
import com.scisdata.web.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fangshilei on 18/1/26.
 * 查询mac历史轨迹信息
 */
@Service("orbitService")
public class OrbitServiceImpl implements OrbitService{
    @Autowired
    private OrbitDao orbitDao;
    @Override
    public List<Map<String, String>> getHistoryOrbit(String mac, String startDate, String endDate) {
        if(!StringUtils.isEmpty(mac)){
            if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
                String  sql = "select m.*,w.latitude as equipmentLatitude\n" +
                        ",w.langitude as equipmentLangitude from mactrace m left join macInfo i \n" +
                        "on m.macId = i.primaryId left join wifiEquipmentInfo w\n" +
                        "on m.equipmentId = w.equipmentId where \n" +
                        "m.startTime>='"+startDate+"'\n" +
                        "and m.endTime<='"+endDate+"'\n" +
                        " and i.macAddress='"+mac+"' ";
                List<Map<String,String>> list = this.orbitDao.queryList(sql);
                return list;
            }else{
                String  sql = "select m.*,w.latitude as equipmentLatitude\n" +
                        ",w.langitude as equipmentLangitude from mactrace m left join macInfo i \n" +
                        "on m.macId = i.primaryId left join wifiEquipmentInfo w\n" +
                        "on m.equipmentId = w.equipmentId where \n" +
                        "m.startTime>='"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 00:00:00'\n" +
                        "and m.endTime<='"+DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 23:59:59'\n" +
                        " and i.macAddress='"+mac+"' ";
                List<Map<String,String>> list = this.orbitDao.queryList(sql);
                return list;
            }

        }
        return new ArrayList<Map<String,String>>();
    }

    @Override
    public List<Map<String, String>> getEquipementInfo(String mac, String startDate, String endDate) {
        if(!StringUtils.isEmpty(mac)){
            if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
                String  sql = "select DISTINCT w.langitude,w.latitude,w.equipmentLocation from mactrace m left join macInfo i on \n" +
                        "i.primaryId=m.macId left join wifiEquipmentInfo w on \n" +
                        "w.equipmentId = m.equipmentId\n" +
                        "where \n" +
                        "m.startTime>='"+startDate+"' and\n" +
                        "m.endTime<='"+endDate+"' and \n" +
                        "i.macAddress='"+mac+"' ";
                List<Map<String,String>> list = this.orbitDao.queryList(sql);
                return list;
            }else{
                String  sql = "select DISTINCT w.langitude,w.latitude,w.equipmentLocation from mactrace m left join macInfo i on \n" +
                        "i.primaryId=m.macId left join wifiEquipmentInfo w on \n" +
                        "w.equipmentId = m.equipmentId\n" +
                        "where \n" +
                        "m.startTime>='"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 00:00:00' and\n" +
                        "m.endTime<='"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 23:59:59' and \n" +
                        "i.macAddress='"+mac+"' ";
                List<Map<String,String>> list = this.orbitDao.queryList(sql);
                return list;
            }

        }
        return new ArrayList<Map<String,String>>();
    }

    @Override
    public List<Map<String, String>> getCarHistoryOrbit(String plate, String startDate, String endDate) {
        if(!StringUtils.isEmpty(plate)){
            if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
                String  sql = "select c.* from cartrace c left join carinfo i\n" +
                        "on i.primaryId=c.carPlateId left join \n" +
                        "videoEquipmentInfo v on \n" +
                        "v.equipmentId=c.equipmentId\n" +
                        "where c.startTime>='"+startDate+"' and c.endTime<='"+endDate+"' \n" +
                        "and i.carPlate='"+plate+"'";
                List<Map<String,String>> list = this.orbitDao.queryList(sql);
                return list;
            }else{
                String  sql = "select c.* from cartrace c left join carinfo i\n" +
                        "on i.primaryId=c.carPlateId left join \n" +
                        "videoEquipmentInfo v on \n" +
                        "v.equipmentId=c.equipmentId\n" +
                        "where c.startTime>='"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 00:00:00' " +
                        "and c.endTime<='"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 23:59:59' \n" +
                        "and i.carPlate='"+plate+"'";
                List<Map<String,String>> list = this.orbitDao.queryList(sql);
                return list;
            }

        }
        return new ArrayList<Map<String,String>>();
    }

    @Override
    public List<Map<String, String>> getCarEquipementInfo(String mac, String startDate, String endDate) {
        if(!StringUtils.isEmpty(mac)){
            if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
                String  sql = "select DISTINCT v.langitude,v.latitude,v.equipmentLocation from cartrace c left join carinfo i\n" +
                        "on i.primaryId=c.carPlateId left join \n" +
                        "videoEquipmentInfo v on \n" +
                        "v.equipmentId=c.equipmentId\n" +
                        "where c.startTime>='"+startDate+"' and c.endTime<='"+endDate+"' \n" +
                        "and i.carPlate='"+mac+"'";
                List<Map<String,String>> list = this.orbitDao.queryList(sql);
                return list;
            }else{
                String  sql = "select DISTINCT v.langitude,v.latitude,v.equipmentLocation from cartrace c left join carinfo i\n" +
                        "on i.primaryId=c.carPlateId left join \n" +
                        "videoEquipmentInfo v on \n" +
                        "v.equipmentId=c.equipmentId\n" +
                        "where c.startTime>='"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 00:00:00' " +
                        "and c.endTime<='"+ DateUtils.formatDate(new Date(),"yyyy-MM-dd")+" 23:59:59' \n" +
                        "and i.carPlate='"+mac+"'";
                List<Map<String,String>> list = this.orbitDao.queryList(sql);
                return list;
            }

        }
        return new ArrayList<Map<String,String>>();
    }
}
