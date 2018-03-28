package com.zxm.load.test;

import com.scisdata.web.bean.MacTrace;
import com.zxm.load.MacTraceCache;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MacTraceCacheTest {


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void execute(String equipmentId, Date startTime, Date endTime) throws IOException {
        MacTraceCache cache = new MacTraceCache(equipmentId);
        List<MacTrace> macTraces = null;
        try {
            macTraces = cache.getMacTraces(startTime, endTime);
            for(MacTrace macTrace : macTraces) {
                System.out.println(macTrace.getMacId() + " " + macTrace.getStartTime() + " " + macTrace.getEquipmentId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 测试所有数据位于一个文件中
    @Test
    public void test1() throws IOException, ParseException {
        String equipmentId = "72300510494885E3133FD";
        String start ="2018-03-21 04:45:09";  // 1521578709
        String end = "2018-03-21 12:49:05";   // 1521607745
        execute(equipmentId, sdf.parse(start), sdf.parse(end));
    }

    // 测试所有取件内的数据位于两个文件中
    @Test
    public void test2() throws ParseException, IOException {
        String equipmentId = "72300510494885E3133FD";
        String start ="2018-03-21 04:45:19";    // 1521578719
        String end = "2018-03-21 12:49:15";     // 1521607755
        execute(equipmentId, sdf.parse(start), sdf.parse(end));
    }

    // 测试所有数据位于多个文件中
    @Test
    public void test3() throws ParseException, IOException {
        String equipmentId = "72300510494885E3133FD";
        String start ="2018-03-21 12:49:05";    // 1521607745
        String end = "2018-03-21 13:03:47";     // 1521608627
        execute(equipmentId, sdf.parse(start), sdf.parse(end));
    }

    // 测试数据位于多个文件中，并且startFile与endFile跨多个文件
    @Test
    public void test4() throws ParseException, IOException {
        // 设置capacity值为 443
        String equipmentId = "72300510494885E3133FD";
        String start ="2018-03-21 12:49:05";    // 1521607745
        String end = "2018-03-21 13:03:47";     // 1521608627
        execute(equipmentId, sdf.parse(start), sdf.parse(end));
    }

    // 测试数据设备编号为空
    @Test
    public void test5() throws ParseException, IOException {
        // 设置capacity值为 443
        String equipmentId = "7510494885E3133FD";
        String start ="2018-03-21 12:49:05";    // 1521607745
        String end = "2018-03-21 13:03:47";     // 1521608627
        execute(equipmentId, sdf.parse(start), sdf.parse(end));
    }



}
