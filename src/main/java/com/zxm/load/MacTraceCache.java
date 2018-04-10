package com.zxm.load;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scisdata.web.bean.MacTrace;
import com.zxm.load.utils.Constants;
import com.zxm.load.utils.JedisTools;
import com.zxm.load.utils.SystemConfig;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class MacTraceCache {

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private String fileBasePath;
    private long cacheSize;
    private long currentCacheSize;
    private Deque<MacTrace> cache;
    private MacTraceCacheOffset offset;
    private long step;
    private String equipmentId;
//    private JedisTools jedis;

    public MacTraceCache(String equipmentId) throws IOException {
        this.cache = new LinkedList<>();
        this.cacheSize = Long.parseLong(SystemConfig.getString(Constants.CACHE_CAPACITY));
        this.step = Long.parseLong(SystemConfig.getString(Constants.CACHE_STEP_LENGTH));
        this.fileBasePath = SystemConfig.getString(Constants.MAC_TRACE_BASE_PATH);
        this.currentCacheSize = 0;
        this.equipmentId = equipmentId;
//        this.jedis = new JedisTools();
        offsetInit(equipmentId);
    }

    /**
     * 初始化缓存Offset，记录cache中存储了哪些文件中的数据，
     * 并记录存储的起始文件及对应的起始位置，结束文件以及对应的结束位置
     * @param equipmentId
     * @throws IOException
     */
    private void offsetInit(String equipmentId) throws IOException {
        String oo = JedisTools.getOffset(equipmentId);
        if(oo != null) {
            String[] strs = oo.split(":");
            String currentReadFile = strs[0];
            int offset = Integer.parseInt(strs[1]);
            this.offset = new MacTraceCacheOffset(currentReadFile, offset, currentReadFile, 0, 0);
        }
    }

    /**
     * 根据时间范围查询所有在该时间范围内的mac地址信息
     * @param startTime
     * @param endTime
     * @return
     * @throws IOException
     */
    public List<MacTrace> getMacTraces(Date startTime, Date endTime) throws IOException {
        List<MacTrace> macTraces = new ArrayList<>();
        int startIndex = 0;
        while(true) {
            int cacheSize = cache.size();
            if (!cache.isEmpty()) {
                for(MacTrace macTrace : cache) {
                    if(startIndex > 0) {
                        startIndex--;
                        continue;
                    }
                    Date macTraceTime = macTrace.getStartTime();
                    // mac日期 > 最大时间范围，说明该mac地址失效
                    if(macTraceTime.compareTo(endTime) > 0) {
                        return macTraces;
                    }
                    // mac日期 > 最小时间范围，说明该mac地址有可能与车辆有关联
                    if(macTraceTime.compareTo(startTime) >= 0) {
                        macTraces.add(macTrace);
                    }
                }
                if(hasMoreMacTraces()) {
                    int movedSize = moveForward();
                    startIndex = cacheSize - movedSize;
                } else {
                    return macTraces;
                }

            } else {
                if(!cacheMacTrace(false)) {
                    break;
                }
            }
        }
        return macTraces;
    }

    /**
     * cache缓存窗口向前移动step位
     * 删除缓存中的前step个mac地址信息，
     * 并缓存下step个mac地址信息。
     * @return
     * @throws IOException
     */
    private int moveForward() throws IOException {
        int indexChangeSize = 0;
        rwLock.writeLock().lock();
        long stepTemp = step;
        try {
            while(stepTemp-- > 0 && !cache.isEmpty()) {
                cache.remove();
                indexChangeSize++;
            }
            offset.resetStartOffset(indexChangeSize);
            cacheMacTrace(true);
        } finally {
            rwLock.writeLock().unlock();
        }
        return indexChangeSize;
    }

    /**
     * 向cache中添加mac地址信息
     * @param append
     * @return
     */
    private boolean cacheMacTrace(boolean append) {
        if(currentCacheSize >= cacheSize) {
            return true;
        }
        rwLock.writeLock().lock();
        try {
            if(offset == null) {switchToNextFile();}
            String fileName;
            int beginReadIndex;
            while(currentCacheSize < cacheSize && offset != null)  {
                if(!append) {
                    fileName = offset.getStartFileName();
                    beginReadIndex = offset.getStartLineIndex();
                } else {
                    fileName = offset.getEndFileName();
                    beginReadIndex = offset.getEndLineIndex();
                }

                FileReader reader = null;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File(fileBasePath+"/"+fileName)));
                    int lineIndex = 0;
                    String line;
                    while((line = br.readLine()) != null) {
                        if(lineIndex++ < beginReadIndex) continue;
                        MacTrace macTrace = parseJson(line);
                        cache.add(macTrace);
                        if(cache.size() >= cacheSize) {
                            offset.setEndFileName(fileName);
                            offset.setEndLineIndex(lineIndex);
                            offset.setSize(cache.size());
                            return true;
                        }
                    }
                    offset.setEndLineIndex(lineIndex);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(reader != null) try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(!switchToNextFile()) {
                    offset.setSize(cache.size());
                    break;
                }
                append = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }

        if(cache.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 解析mac地址信息
     * @param json
     * @return
     * @throws ParseException
     */
    private MacTrace parseJson(String json) throws ParseException {
        if(json == null) return null;
        JSONObject obj = JSON.parseObject(json);
        MacTrace macTrace = new MacTrace();
        Long timestamp = Long.parseLong(obj.getString("START_TIME"));
        macTrace.setStartTime(new Date(timestamp*1000));
        macTrace.setEquipmentId(obj.getString("DEVICENUM"));
        macTrace.setMacId(obj.getString("MAC"));
        return macTrace;
    }

    /**
     * 判断是否还有未读的数据
     * @return
     */
    private boolean hasMoreMacTraces() {

        File fileDir = new File(fileBasePath);
        File[] files = fileDir.listFiles();
        String endReadFile = offset.getEndFileName();
        for(int i=0; i<files.length; i++) {
            File file = files[i];
            String fileName = file.getName();
            if(!fileName.startsWith(equipmentId)) continue;
            if(fileName.compareTo(endReadFile)>0) {
                return true;
            }
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(fileBasePath+"/"+offset.getEndFileName())));
            int lineCount = 0;
            String line;
            while((line = br.readLine()) != null) {
                lineCount++;
            }
            if(offset.getEndLineIndex() < lineCount) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br!=null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 查询下一个需要读的文件路径
     * @return
     */
    private boolean switchToNextFile() {

        if(offset == null) {
            File fileDir = new File(fileBasePath);
            File[] files = fileDir.listFiles();

            String nextFileName = getNextFileName(null,files);
            if(nextFileName != null) {
                offset = new MacTraceCacheOffset(nextFileName, 0, nextFileName, 0, 0);
                return true;
            } else {
                return false;
            }
        } else {
            File fileDir = new File(fileBasePath);
            File[] files = fileDir.listFiles();
            String endReadFile = offset.getEndFileName();
            String nextFileName = getNextFileName(endReadFile, files);
            if(nextFileName != null) {
                if(!nextFileName.equals(endReadFile) && !endReadFile.equals(offset.getStartFileName())) {
                    offset.addBetweenFile(endReadFile, offset.getEndLineIndex());
                }
                offset.setEndFileName(nextFileName);
                offset.setEndLineIndex(0);
                return true;
            }
        }
        return false;
    }

    /**
     * 查询下一个文件名
     * @param currentFileName
     * @param files
     * @return
     */
    private String getNextFileName(String currentFileName, File[] files) {
        List<String> matchedFiles = new ArrayList<>();
        for(int i=0; i<files.length; i++) {
            File file = files[i];
            String fileName = file.getName();
            if(!fileName.startsWith(equipmentId)) continue;
            matchedFiles.add(fileName);
        }
        List<String> sortedFiles = matchedFiles.stream().sorted().collect(Collectors.toList());
        for(String fileName : sortedFiles) {
            if(currentFileName == null) return fileName;
            if(currentFileName.compareTo(fileName) < 0) return fileName;
        }
        return null;
    }

    /**
     * 关闭缓存，并将offset地址写入redis中
     */
    public void close() {
        if(offset != null && offset.getStartFileName() != null) {
            JedisTools.setOffset(equipmentId, offset.getStartFileName(), offset.getStartLineIndex());
        }
    }
}
