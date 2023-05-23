package com.hhkbdev.asyncexceldownload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileProcessing {
  public void writeDataToExcel(String filePath, XSSFWorkbook workbook,
      XSSFSheet sheet, int rowIndex, List<HashMap> dataList)
      throws IOException {
    writeData(sheet, dataList, rowIndex);

    FileOutputStream outputStream = new FileOutputStream(filePath);
    workbook.write(outputStream);
    workbook.close();
    outputStream.close();
  }

  private void writeData(XSSFSheet sheet, List<HashMap> dataList, int rowIndex) {
    for (HashMap dataMap : dataList) {
      Row row = sheet.createRow(rowIndex++);
      int cellIndex = 0;
      for (Object value : dataMap.values()) {
        Cell cell = row.createCell(cellIndex++);
        cell.setCellValue(String.valueOf(value));
      }
    }
  }

}
