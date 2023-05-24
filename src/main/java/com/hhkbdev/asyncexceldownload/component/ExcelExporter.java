package com.hhkbdev.asyncexceldownload.component;

import com.hhkbdev.asyncexceldownload.domain.Field;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

  private static final Integer MAX_ROW_COUNT = 50000;

  String hashedFilename;
  List<Field> fields;

  public ExcelExporter(
      String hashedFilename,
      List<Field> fields) {
    this.hashedFilename = hashedFilename;
    this.fields = fields;
  }

  public void uploadToServer(List<HashMap> dataList) throws IOException {
    Workbook workbook = getInitWorkbook();
    Sheet sheet = getStyledSheet(getInitSheet(workbook));
    int rowIndex = getInitRowIndex(sheet);

    if (rowIndex == 0) {
      createHeaderOnSheet(sheet);
      rowIndex++;
    }

    Map<String, CellStyle> styleMap = getCellStyleMap(workbook);

    for (HashMap dataMap : dataList) {
      if (rowIndex > MAX_ROW_COUNT) {
        sheet = getStyledSheet(getNewSheet(workbook));
        createHeaderOnSheet(sheet);
        rowIndex = 1;
      }

      Row dataRow = sheet.createRow(rowIndex++);
      createCellOnRow(dataRow, dataMap, styleMap);
    }

    FileOutputStream outputStream = new FileOutputStream(hashedFilename);
    workbook.write(outputStream);
    workbook.close();
    outputStream.close();
  }

  private Workbook getInitWorkbook() {
    Workbook workbook;
    try (FileInputStream fis = new FileInputStream(hashedFilename)) {
      workbook = new XSSFWorkbook(fis);
    } catch (FileNotFoundException e) {
      workbook = new XSSFWorkbook();
    } catch (IOException e) {
      throw new RuntimeException("File read failed", e);
    }

    return workbook;
  }

  private Sheet getInitSheet(Workbook workbook) {
    int sheetNum = workbook.getNumberOfSheets();

    Sheet sheet;
    if (sheetNum == 0) {
      sheet = getNewSheet(workbook);
    } else {
      sheet = workbook.getSheetAt(sheetNum - 1);
    }

    for (int i = 0; i < fields.size(); i++) {
      if (fields.get(i).getStyle().getWidth() != null) {
        sheet.setColumnWidth(i, fields.get(i).getStyle().getWidth());
      }
    }

    return sheet;
  }

  private Sheet getNewSheet(Workbook workbook) {
    return workbook.createSheet("Data" + (workbook.getNumberOfSheets() + 1));
  }

  private Sheet getStyledSheet(Sheet sheet) {
    for (int i = 0; i < fields.size(); i++) {
      if (fields.get(i).getStyle().getWidth() != null) {
        sheet.setColumnWidth(i, fields.get(i).getStyle().getWidth());
      }
    }

    return sheet;
  }

  private int getInitRowIndex(Sheet sheet) {
    int rowIndex = 0;
    if (sheet.getLastRowNum() > 0) {
      rowIndex = sheet.getLastRowNum() + 1;
    }
    return rowIndex;
  }

  private void createHeaderOnSheet(Sheet sheet) {
    int cellIndex = 0;
    Row headerRow = sheet.createRow(0);
    for (Field field : fields) {
      Cell headerCell = headerRow.createCell(cellIndex++);
      headerCell.setCellValue(field.getSchema().getName());
    }
  }

  private Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
    Map<String, CellStyle> styleMap = new HashMap<>();
    for (Field field : fields) {
      CellStyle cellStyle = workbook.createCellStyle();
      cellStyle.setAlignment(field.getStyle().getHorizontalAlignment());
      styleMap.put(field.getSchema().getId(), cellStyle);
    }

    return styleMap;
  }


  private void createCellOnRow(Row row, HashMap dataMap, Map<String, CellStyle> cellStyleMap) {
    int cellIndex = 0;
    for (Field field : fields) {
      Cell dataCell = row.createCell(cellIndex++);
      if (dataMap.get(field.getSchema().getId()) == null) {
        dataCell.setCellValue("");
      } else {
        dataCell.setCellValue(String.valueOf(dataMap.get(field.getSchema().getId())));
        CellStyle cellStyle = cellStyleMap.get(field.getSchema().getId());
        dataCell.setCellStyle(cellStyle);
      }
    }
  }
}

