package com.excel.manage.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class ExcelUtil {
    /**
     * Excel文件读取
     */
    public static List<List<Object>> readExcel(String path) throws Exception {
        File file = new File(path) ;
        List<List<Object>> list = new LinkedList<>();
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
        // 读取 Sheet1 表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        // 读取行数：不读取Excel表头
        for (int i = (sheet.getFirstRowNum()+1); i <= (sheet.getPhysicalNumberOfRows()-1); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) { continue; }
            List<Object> linked = new LinkedList<>();
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                XSSFCell cell = row.getCell(j);
                if (cell == null) { continue; }
                Object value ;
                // 这里需根据实际业务情况处理
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        //处理数值带{.0}问题
                        value = Double.valueOf(String.valueOf(cell)).longValue() ;
                        break;
                    default:
                        value = cell.toString();
                }
                linked.add(value);
            }
            if (linked.size()!= 0) {
                list.add(linked);
            }
        }
        return list;
    }

    /**
     * 创建 Excel
     */
    public static void createExcel(String excelName, String[] headList,List<List<Object>> dataList)
            throws Exception {
        // 创建 Excel 工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        // 创建表头
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headList.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(headList[i]);
        }
        //添加数据
        for (int line = 0; line < dataList.size(); line++) {
            XSSFRow rowData = sheet.createRow(line+1);
            List<Object> data = dataList.get(line);
            for (int j = 0; j < headList.length; j++) {
                XSSFCell cell = rowData.createCell(j);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue((data.get(j)).toString());
            }
        }
        FileOutputStream fos = new FileOutputStream(excelName);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    /**
     * 导出 Excel
     */
    public static void exportExcel(String[] headList, List<List<Object>> dataList,
                                   OutputStream outputStream) throws Exception {
        // 创建 Excel 工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        // 创建表头
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headList.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(headList[i]);
        }
        //添加数据
        for (int line = 0; line < dataList.size(); line++) {
            XSSFRow rowData = sheet.createRow(line+1);
            List<Object> data = dataList.get(line);
            for (int j = 0; j < headList.length; j++) {
                XSSFCell cell = rowData.createCell(j);
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue((data.get(j)).toString());
            }
        }
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public static void main(String[] args) throws Exception {
        // 文件读取测试
        List<List<Object>> dataList = readExcel("F:\\file-type\\user-excel.xlsx") ;
        System.out.println(dataList);
        // 文件创建测试
        String[] headList = new String[]{"用户ID", "用户名", "手机号"} ;
        String exportName = "F:\\file-type\\user-excel-new.xlsx" ;
        createExcel(exportName,headList,dataList);
    }
}
