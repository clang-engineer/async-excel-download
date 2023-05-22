package com.hhkbdev.asyncexceldownload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    List<String> headers = new ArrayList<>();
    headers.add("Name");
    headers.add("Age");
    headers.add("Country");

    ExcelConverter converter = new ExcelConverter(headers);

    List<Map<String, String>> dataList = new ArrayList<>();
    Map<String, String> map1 = new LinkedHashMap<>();
    map1.put("Name", "John");
    map1.put("Age", "30");
    map1.put("Country", "USA");
    dataList.add(map1);

    Map<String, String> map2 = new LinkedHashMap<>();
    map2.put("Name", "Emma");
    map2.put("Age", "28");
    map2.put("Country", "Canada");
    dataList.add(map2);

    Map<String, String> map3 = new LinkedHashMap<>();
    map3.put("Name", "Peter");
    map3.put("Age", "35");
    map3.put("Country", "Australia");
    dataList.add(map3);

    Workbook workbook = converter.convertToWorkbook(dataList);

    assertNotNull(workbook);

    Sheet sheet = workbook.getSheetAt(0);
    assertNotNull(sheet);

    Row headerRow = sheet.getRow(0);
    assertNotNull(headerRow);
    assertEquals(3, headerRow.getLastCellNum());

    Cell headerCell1 = headerRow.getCell(0);
    assertNotNull(headerCell1);
    assertEquals("Name", headerCell1.getStringCellValue());

    Cell headerCell2 = headerRow.getCell(1);
    assertNotNull(headerCell2);
    assertEquals("Age", headerCell2.getStringCellValue());

    Cell headerCell3 = headerRow.getCell(2);
    assertNotNull(headerCell3);
    assertEquals("Country", headerCell3.getStringCellValue());

    Row dataRow1 = sheet.getRow(1);
    assertNotNull(dataRow1);
    assertEquals(3, dataRow1.getLastCellNum());

    Cell dataCell1 = dataRow1.getCell(0);
    assertNotNull(dataCell1);
    assertEquals("John", dataCell1.getStringCellValue());

    Cell dataCell2 = dataRow1.getCell(1);
    assertNotNull(dataCell2);
    assertEquals("30", dataCell2.getStringCellValue());

    Cell dataCell3 = dataRow1.getCell(2);
    assertNotNull(dataCell3);
    assertEquals("USA", dataCell3.getStringCellValue());

    // 추가적인 데이터 행들을 검증할 수 있습니다.

    // workbook.close() 메소드를 호출하여 리소스를 정리해주어야 합니다.
    workbook.close();
  }
}
