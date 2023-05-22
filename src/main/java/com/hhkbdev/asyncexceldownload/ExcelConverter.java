package com.hhkbdev.asyncexceldownload;

import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelConverter {
  public Workbook convertToWorkbook(List<Map<String, String>> dataList) {
    Workbook workbook = new SXSSFWorkbook();

    Sheet sheet = workbook.createSheet("Data");
    int rowIndex = 0;

    // 헤더 행 생성
    Row headerRow = sheet.createRow(rowIndex++);
    int cellIndex = 0;
    for (String key : dataList.get(0).keySet()) {
      Cell headerCell = headerRow.createCell(cellIndex++);
      headerCell.setCellValue(key);
    }

    // 데이터 행 생성
    for (Map<String, String> dataMap : dataList) {
      Row dataRow = sheet.createRow(rowIndex++);
      cellIndex = 0;
      for (String value : dataMap.values()) {
        Cell dataCell = dataRow.createCell(cellIndex++);
        dataCell.setCellValue(value);
      }
    }

    return workbook;
  }
}
