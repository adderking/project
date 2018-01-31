package com.kingcobra.tools;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Component("importWifiInfo")
public class ImportWifiEquipmentInfo {


    public void parseFile(String filePath,int[] metaIndex) throws Exception {
        InputStream in = new FileInputStream(new File(filePath));
        List<XWPFTable> XWPFTableList = null;

        try(XWPFDocument xwpfDocument = new XWPFDocument(in)){
            XWPFTableList = xwpfDocument.getTables();
            for (XWPFTable xwpfTable : XWPFTableList) {
                tableHandler(xwpfTable,metaIndex);
            }
        }
    }

    /**
     * 处理word文档中的表格
     * @param xwpfTable
     */
    private void tableHandler(XWPFTable xwpfTable,int[] metaIndex) {
        int rows = xwpfTable.getNumberOfRows();
        System.out.println(rows);
        XWPFTableRow xwpfTableRow = null;
        for(int i=1;i<rows;i++) {
            xwpfTableRow= xwpfTable.getRow(i);
            List<XWPFTableCell> cells = xwpfTableRow.getTableCells();
            for(int j=0;j<metaIndex.length;j++) {
                cells.get(metaIndex[j]).getText();
            }
        }
    }

    public static void main(String[] args) {
        String file = "/Users/kingcobra/Downloads/unit.docx";
        int[] metaIndex={2, 3, 4, 5, 6};

        ImportWifiEquipmentInfo importWifiEquipmentInfo = new ImportWifiEquipmentInfo();
        try {
            importWifiEquipmentInfo.parseFile(file,metaIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
