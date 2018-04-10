package com.zxm.load;

import java.util.ArrayList;
import java.util.List;

public class MacTraceCacheOffset {
    private String startFileName;
    private int startLineIndex;
    private List<String> betweenFiles;
    private List<Integer> betweenFileSizes;
    private String endFileName;
    private int endLineIndex;
    private int size;

    public MacTraceCacheOffset(String startFileName, int startLineIndex, int size) {
        this.startFileName = startFileName;
        this.startLineIndex = startLineIndex;
        this.betweenFileSizes = new ArrayList<>();
        this.betweenFiles = new ArrayList<>();
        this.size = size;
    }

    public MacTraceCacheOffset(String startFileName, int startLineIndex, String endFileName, int endLineIndex, int size) {
        this.startFileName = startFileName;
        this.startLineIndex = startLineIndex;
        this.endFileName = endFileName;
        this.endLineIndex = endLineIndex;
        this.betweenFileSizes = new ArrayList<>();
        this.betweenFiles = new ArrayList<>();
        this.size = size;
    }

    private void decreaseSize(int num) {
        int size = this.size - num;
        this.size = size>=0?size:0;
    }

    /**
     * 重新设置offset信息
     * @param num
     */
    public void resetStartOffset(int num) {
        decreaseSize(num);
        /**
         * 可能出现情况:
         * 1. startFile与endFile属于同一个
         * 2. startFile与endFile不属于同一个
         *    2.1 数据删除num个元素后startFile与endFile属于同一个文件
         *    2.2 数据删除num个后，开始位置在between中的某个文件中
         *    2.3 数据删除num个后，开始位置未超出startFile
         **/
        // 1
        if(startFileName.equals(endFileName)) {
            startLineIndex += num;
        } else {
            // 2.1
            if(endLineIndex >= size) {
                startLineIndex = endLineIndex-size;
                startFileName = endFileName;
                return;
            }

            long totalSize = endLineIndex;
            for(int size : betweenFileSizes) {
                totalSize += size;
            }

            // 2.2
            if(totalSize >= size) {
                int additionSize = endLineIndex;
                int i=betweenFileSizes.size();
                for(; i>=0; i--) {
                    int s = betweenFileSizes.get(i);
                    additionSize += s;
                    if(additionSize < size) {
                        continue;
                    } else {
                        startLineIndex = additionSize-size;
                        startFileName = betweenFiles.get(i);
                    }
                }
                while(i-- >= 0) {
                    betweenFileSizes.remove(i);
                    betweenFiles.remove(i);
                }
            } else {    // 2.3
                startLineIndex += num;
            }
        }
    }

    public void addBetweenFile(String fileName, int size) {
        betweenFiles.add(fileName);
        betweenFileSizes.add(size);
    }

    public String getStartFileName() {
        return startFileName;
    }

    public void setStartFileName(String startFileName) {
        this.startFileName = startFileName;
    }

    public int getStartLineIndex() {
        return startLineIndex;
    }

    public void setStartLineIndex(int startLineIndex) {
        this.startLineIndex = startLineIndex;
    }

    public String getEndFileName() {
        return endFileName;
    }

    public void setEndFileName(String endFileName) {
        this.endFileName = endFileName;
    }

    public int getEndLineIndex() {
        return endLineIndex;
    }

    public void setEndLineIndex(int endLineIndex) {
        this.endLineIndex = endLineIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
