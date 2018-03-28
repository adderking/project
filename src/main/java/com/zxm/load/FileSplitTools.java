package com.zxm.load;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileSplitTools {

    private static String base_path = "/Users/zxm/IdeaProjects/Works/project/src/main/resources/test-data/";

    public void split() throws IOException {
        FileReader fr = new FileReader(base_path+"20180321130509419_139_110115_723005104_001.log");
        BufferedReader br = new BufferedReader(fr);
        String line;
        Map<String, FileWriter> writers = new HashMap<>();
        while((line=br.readLine()) != null) {
            JSONArray arr = JSON.parseArray(line);
            int size = arr.size();
            for(int i=0; i<size; i++) {
                JSONObject obj = arr.getJSONObject(i);
                String deviceNum = obj.getString("DEVICENUM");
                if(writers.containsKey(deviceNum)) {
                    FileWriter writer = writers.get(deviceNum);

                    writer.write(obj.toJSONString()+"\n");
                } else {
                    FileWriter writer = new FileWriter(base_path+deviceNum);
                    writers.put(deviceNum, writer);
                    writer.write(obj.toJSONString()+"\n");
                }
            }
        }

        Iterator<FileWriter> it = writers.values().iterator();
        while(it.hasNext()) {
            FileWriter writer = it.next();
            writer.close();
        }
    }

    public static void sort(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lists = new ArrayList<>();
        String line;
        while((line=br.readLine())!=null) {
            lists.add(line);
        }
        br.close();
        file.deleteOnExit();
        FileWriter fw = new FileWriter(fileName+"_sorted");
        List<String> lists_2 =  lists.stream().sorted((o1, o2) -> {
            JSONObject obj1 = JSON.parseObject(o1);
            JSONObject obj2 = JSON.parseObject(o2);
            String startTime1 = obj1.getString("START_TIME");
            String startTime2 = obj2.getString("START_TIME");
            return startTime1.compareTo(startTime2);
        }).collect(Collectors.toList());

        for(String str : lists_2) {
            fw.write(str + "\n");
        }
        fw.close();

    }

    public static void main(String[] args) throws IOException {
        FileSplitTools.sort(base_path+"72300510494885E3136D2");
    }

}
