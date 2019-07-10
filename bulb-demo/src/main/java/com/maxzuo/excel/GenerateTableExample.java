package com.maxzuo.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * 生成表格（包含合并单元格，单元格对齐居中）
 * <p>
 * Created by zfh on 2019/07/10
 */
public class GenerateTableExample {

    private static final Logger logger = LoggerFactory.getLogger(GenerateTableExample.class);

    public static void main(String[] args) {
        simpleTable();
    }

    /**
     * 生成简单表
     */
    private static void simpleTable () {
        try {
            FileOutputStream fos = new FileOutputStream(new File("signupRecord.xls"));

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("sheet1");

            HSSFCell title = sheet.createRow(0).createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            title.setCellValue("标题");
            CellStyle titleCellStyle = wb.createCellStyle();
            titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // 字体大小
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 14);
            titleCellStyle.setFont(font);
            title.setCellStyle(titleCellStyle);


            HSSFRow row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("序号");
            row1.createCell(1).setCellValue("海报ID");
            row1.createCell(2).setCellValue("订单号");
            row1.createCell(3).setCellValue("用户ID");
            row1.createCell(4).setCellValue("时间");

            HSSFRow row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue(1);
            row2.createCell(1).setCellValue(10);
            row2.createCell(2).setCellValue("12345");
            row2.createCell(3).setCellValue(1);
            row2.createCell(4).setCellValue(new Date());

            HSSFRow row3 = sheet.createRow(3);
            row3.createCell(0).setCellValue(2);
            row3.createCell(1).setCellValue(10);
            row3.createCell(2).setCellValue("12323");
            row3.createCell(3).setCellValue(3);
            row3.createCell(4).setCellValue(new Date());

            HSSFRow row4 = sheet.createRow(4);
            row4.createCell(0).setCellValue(2);
            row4.createCell(1).setCellValue(10);
            row4.createCell(2).setCellValue("13433");
            row4.createCell(3).setCellValue(3);
            row4.createCell(4).setCellValue(new Date());

            // 设定合并单元格区域范围
            CellRangeAddress cra = new CellRangeAddress(2, 3, 2, 2);
            sheet.addMergedRegion(cra);

            // 单元格设置对其居中
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            row2.getCell(2).setCellStyle(cellStyle);

            wb.write(fos);
            fos.close();
        } catch (Exception e) {
            logger.error("导出表格异常！", e);
        }
    }
}
