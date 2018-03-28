//package com.zxm.load;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.scisdata.web.bean.MacTrace;
//import com.zxm.load.utils.SystemConfig;
//import com.zxm.load.utils.Constants;
//
//import java.io.*;
//import java.text.ParseException;
//import java.util.*;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
//public class MacTraceCache {
//
//    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
//    private String fileBasePath;
//    private long cacheSize;
//    private Deque<MacTrace> cache;
//    private String currentReadFile;
//    private long offset;
//    private long step;
//
//    public MacTraceCache(String equipmentId) throws IOException {
//        this.cache = new LinkedList<>();
//        this.cacheSize = Long.parseLong(SystemConfig.getString(Constants.CACHE_CAPACITY));
//        this.step = Long.parseLong(SystemConfig.getString(Constants.CACHE_STEP_LENGTH));
//        this.fileBasePath = SystemConfig.getString(Constants.MAC_TRACE_BASE_PATH);
//        offsetInit(equipmentId);
//    }
//
//    private void offsetInit(String equipmentId) throws IOException {
//        String offsetFile = SystemConfig.getString(Constants.CACHE_OFFSET_FILE_PATH);
//        FileReader fr = new FileReader(new File(offsetFile));
//        BufferedReader br = new BufferedReader(fr);
//        String line;
//        while((line=br.readLine()) != null) {
//            String[] strs = line.split(":");
//            if(equipmentId.equals(strs[0])) {
//                this.currentReadFile = equipmentId+"_"+strs[1];
//                this.offset = Long.parseLong(strs[2]);
//            }
//        }
//    }
//
//    public List<MacTrace> getMacTraces(Date startTime, Date endTime) throws IOException {
//        List<MacTrace> macTraces = new ArrayList<>();
//        int startIndex = 0;
//        while(true) {
//            int cacheSize = cache.size();
//            if (!cache.isEmpty()) {
//                rwLock.readLock().lock();
//                try {
//                    for(MacTrace macTrace : cache) {
//                        if(startIndex > 0) {
//                            startIndex--;
//                            continue;
//                        }
//                        Date macTraceTime = macTrace.getStartTime();
//                        // mac日期 > 最大时间范围，说明该mac地址失效
//                        if(macTraceTime.compareTo(endTime) > 0) {
//                            return macTraces;
//                        }
//                        // mac日期 > 最小时间范围，说明该mac地址有可能与车辆有关联
//                        if(macTraceTime.compareTo(startTime) >= 0) {
//                            macTraces.add(macTrace);
//                        }
//                    }
//                } finally {
//                    rwLock.readLock().unlock();
//                }
//                if(hasMoreMacTraces()) {
//                    int movedSize = moveForward();
//                    startIndex = cacheSize - movedSize;
//                } else {
//                    return macTraces;
//                }
//            } else {
//                if(!cacheMacTrace()) {
//                    break;
//                }
//            }
//        }
//        return macTraces;
//    }
//
//    private int moveForward() throws IOException {
//        int indexChangeSize = 0;
//        rwLock.writeLock().lock();
//        long stepTemp = step;
//        try {
//            while(stepTemp-- > 0 && !cache.isEmpty()) {
//                cache.remove();
//                indexChangeSize++;
//            }
//            // reset offset
//
//
//            cacheMacTrace();
//        } finally {
//            rwLock.writeLock().unlock();
//        }
//        return indexChangeSize;
//    }
//
//    public void resetOffset() {
//
//    }
//
//    private boolean cacheMacTrace() throws IOException {
//        if(cache.size() >= cacheSize) {
//            return true;
//        }
//        rwLock.writeLock().lock();
//        try {
//            while(cache.size() < cacheSize && offset != -1)  {
//                if(currentReadFile == null) {switchToNextFile();}
//                RandomAccessFile reader = new RandomAccessFile(new File(fileBasePath+"/"+currentReadFile),"r");
//                reader.seek(offset);
//                String line;
//                while((line = reader.readLine()) != null) {
//                    MacTrace macTrace = parseJson(line);
//                    cache.add(macTrace);
//                    if(cache.size() >= cacheSize) {
//                        offset = reader.getChannel().position();
//                        return true;
//                    }
//                }
//                switchToNextFile();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } finally {
//            rwLock.writeLock().unlock();
//        }
//
//        if(cache.isEmpty()) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private MacTrace parseJson(String json) throws ParseException {
//        if(json == null) return null;
//        JSONObject obj = JSON.parseObject(json);
//        MacTrace macTrace = new MacTrace();
//        Long timestamp = Long.parseLong(obj.getString("START_TIME"));
//        macTrace.setStartTime(new Date(timestamp*1000));
//        macTrace.setEquipmentId(obj.getString("DEVICENUM"));
//        macTrace.setMacId(obj.getString("MAC"));
//        return macTrace;
//    }
//
//    /**
//     * 判断是否还有未读的数据
//     * @return
//     */
//    private boolean hasMoreMacTraces() {
//        if(currentReadFile != null && offset != -1) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 查询下一个需要读的文件路径
//     * @return
//     */
//    private boolean switchToNextFile() {
//        if(currentReadFile == null) {
//            File fileDir = new File(fileBasePath);
//            File[] files = fileDir.listFiles();
//            for(int i=0; i<files.length; i++) {
//                File file = files[i];
//                String fileName = file.getName();
//                if(currentReadFile == null) {
//                    currentReadFile = fileName;
//                    offset = 0L;
//                } else if(fileName.compareTo(currentReadFile)<0) {
//                    currentReadFile = fileName;
//                    offset = 0L;
//                    return true;
//                }
//            }
//        } else {
//            File fileDir = new File(fileBasePath);
//            File[] files = fileDir.listFiles();
//            for(int i=0; i<files.length; i++) {
//                File file = files[i];
//                String fileName = file.getName();
//                if(fileName.compareTo(currentReadFile)>0) {
//                    currentReadFile = fileName;
//                    offset = 0L;
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//}
