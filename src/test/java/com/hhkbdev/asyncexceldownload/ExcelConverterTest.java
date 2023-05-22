package com.hhkbdev.asyncexceldownload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.hhkbdev.asyncexceldownload.domain.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

class ExcelConverterTest {

  @Test
  void testConvertToWorkbook() throws Exception {
    List<Field> fields = new ArrayList<>();
    Field filed1 = new Field(1L, "Name", "String", "이름");
    Field filed2 = new Field(2L, "Age", "Integer", "나이");
    Field filed3 = new Field(3L, "Country", "String", "국가");
    fields.add(filed1);
    fields.add(filed2);
    fields.add(filed3);

    ExcelConverter converter = new ExcelConverter(fields);

    List<Map<String, String>> dataList = new ArrayList<>();
    dataList.add(Map.of("Name", "John", "Age", "20", "Country", "USA"));
    dataList.add(Map.of("Name", "Jane", "Age", "25", "Country", "Canada"));
    dataList.add(Map.of("Name", "Peter", "Age", "35", "Country", "Australia"));

    Workbook workbook = converter.convertToWorkbook(dataList);

    assertNotNull(workbook);

    Sheet sheet = workbook.getSheetAt(0);
    assertNotNull(sheet);

    Row headerRow = sheet.getRow(0);
    assertNotNull(headerRow);
    assertEquals(3, headerRow.getLastCellNum());

    Cell headerCell1 = headerRow.getCell(0);
    assertNotNull(headerCell1);
    assertEquals(filed1.getFieldComment(), headerCell1.getStringCellValue());

    Cell headerCell2 = headerRow.getCell(1);
    assertNotNull(headerCell2);
    assertEquals(filed2.getFieldComment(), headerCell2.getStringCellValue());

    Cell headerCell3 = headerRow.getCell(2);
    assertNotNull(headerCell3);
    assertEquals(filed3.getFieldComment(), headerCell3.getStringCellValue());

    Row dataRow1 = sheet.getRow(1);
    assertNotNull(dataRow1);
    assertEquals(3, dataRow1.getLastCellNum());

    Cell dataCell1 = dataRow1.getCell(0);
    assertNotNull(dataCell1);
    assertEquals("John", dataCell1.getStringCellValue());

    Cell dataCell2 = dataRow1.getCell(1);
    assertNotNull(dataCell2);
    assertEquals("20", dataCell2.getStringCellValue());

    Cell dataCell3 = dataRow1.getCell(2);
    assertNotNull(dataCell3);
    assertEquals("USA", dataCell3.getStringCellValue());

    workbook.close();
  }
}
