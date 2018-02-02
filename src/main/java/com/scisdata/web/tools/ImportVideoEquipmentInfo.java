package com.scisdata.web.tools;

import com.google.common.collect.Lists;
import com.scisdata.web.Dao.VideoEquipmentInfoDao;
import com.scisdata.web.bean.VideoEquipmentInfo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Component("importVideoEquipmentInfo")
public class ImportVideoEquipmentInfo {

    @Autowired
    private VideoEquipmentInfoDao videoEquipmentInfoDao;

    public void parseFile(String filePath,int[] metaIndex,boolean isSplit) throws Exception {
        InputStream in = new FileInputStream(new File(filePath));
        List<XWPFTable> XWPFTableList = null;

        try(XWPFDocument xwpfDocument = new XWPFDocument(in)){
            XWPFTableList = xwpfDocument.getTables();
            for (XWPFTable xwpfTable : XWPFTableList) {
                tableHandler(xwpfTable,metaIndex,isSplit);
            }
        }
        in.close();
    }

    /**
     * 处理word文档中的表格
     * @param xwpfTable
     */
    private void tableHandler(XWPFTable xwpfTable,int[] metaIndex,boolean isSplit) {
        int rows = xwpfTable.getNumberOfRows();
        System.out.println(rows);
        XWPFTableRow xwpfTableRow = null;

        for(int i=1;i<rows;i++) {
            xwpfTableRow= xwpfTable.getRow(i);
            rowParseObject(xwpfTableRow,metaIndex,isSplit);
        }
    }

    /**
     * 处理word表格中的一行
     * @param xwpfTableRow
     * @param metaIndex
     * @param isSplit
     */
    private void rowParseObject(XWPFTableRow xwpfTableRow, int[] metaIndex,boolean isSplit) {
        int colIndex = 0;
        List<String> equipmentIds=null;
        String equipmentLocation=null,direction=null;
        double latitude=0d,langitude=0d;
        int channel=0;
        List<XWPFTableCell> cells = xwpfTableRow.getTableCells();
        for(int j=0;j<metaIndex.length;j++) {
            colIndex = metaIndex[j];
            String colText = cells.get(colIndex).getText();
            if(isSplit){
                switch (colIndex) {
                    case 2:
                        equipmentIds = stringSplitForIndex(colText);
                        break;
                    case 3:
                        equipmentLocation = colText;break;
                    case 4:
                        direction = colText;break;
                    case 5:
                        latitude = Double.parseDouble(colText);break;
                    case 6:
                        langitude = Double.parseDouble(colText);break;
                    default:break;
                }
            }else{
                switch (colIndex) {
                    case 1:
                        equipmentLocation = colText;break;
                    case 2:
                        direction = colText;break;
                    case 3:
                        channel = Integer.parseInt(colText);break;
                    case 4:
                        latitude = Double.parseDouble(colText);break;
                    case 5:
                        langitude = Double.parseDouble(colText);break;
                    default:break;
                }
            }
        }
        if(isSplit){
            for (String equipmentId : equipmentIds) {
                VideoEquipmentInfo videoEquipmentInfo = new VideoEquipmentInfo();
                videoEquipmentInfo.setEquipmentId(equipmentId);
                videoEquipmentInfo.setEquipmentLocation(equipmentLocation);
                videoEquipmentInfo.setDirection(direction);
                videoEquipmentInfo.setLatitude(latitude);
                videoEquipmentInfo.setLangitude(langitude);
                videoEquipmentInfoDao.insert(videoEquipmentInfo);
            }
        }else{
            VideoEquipmentInfo videoEquipmentInfo = new VideoEquipmentInfo();
            videoEquipmentInfo.setEquipmentLocation(equipmentLocation);
            videoEquipmentInfo.setDirection(direction);
            videoEquipmentInfo.setLatitude(latitude);
            videoEquipmentInfo.setLangitude(langitude);
            videoEquipmentInfo.setChannel(channel);
            videoEquipmentInfoDao.insert(videoEquipmentInfo);
        }
    }

    /**
     * 解析设备ID
     * @param content
     * @return
     */
    private List<String> stringSplitForIndex(String content) {
        content = content.trim();
        int length = content.length();
        String equipmentID=null;
        List<String> equipmentIds = Lists.newArrayList();
        for(int i=0;i<length;i+=7) {
            equipmentID = content.substring(i, i+7);
            equipmentIds.add(equipmentID);
        }
        return equipmentIds;
    }

}
