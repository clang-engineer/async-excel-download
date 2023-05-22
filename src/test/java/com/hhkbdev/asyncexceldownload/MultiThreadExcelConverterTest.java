package com.hhkbdev.asyncexceldownload;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

class MultiThreadExcelConverterTest {

  @Test
  void testGetCombinedWorkbook() throws Exception {
    MultiThreadExcelConverter asyncMaker = new MultiThreadExcelConverter();

    List<Map<String, String>> dataList = new ArrayList<>();

    Map<String, String> data1 = new LinkedHashMap<>();
    data1.put("Name", "John");
    data1.put("Age", "30");
    data1.put("Country", "USA");
    dataList.add(data1);

    Map<String, String> data2 = new LinkedHashMap<>();
    data2.put("Name", "Emma");
    data2.put("Age", "28");
    data2.put("Country", "Canada");
    dataList.add(data2);

    Map<String, String> data3 = new LinkedHashMap<>();
    data3.put("Name", "Peter");
    data3.put("Age", "35");
    data3.put("Country", "Australia");

    Workbook combinedWorkbook = asyncMaker.getCombinedWorkbook(dataList);

    assertThat(combinedWorkbook).isNotNull();

    Sheet sheet = combinedWorkbook.getSheetAt(0);
    assertThat(sheet).isNotNull();

    Row headerRow = sheet.getRow(0);
    assertThat(headerRow).isNotNull();
    assertThat(headerRow.getLastCellNum()).isEqualTo((short) 3);

    Cell headerCell1 = headerRow.getCell(0);
    assertThat(headerCell1).isNotNull();
    assertThat(headerCell1.getStringCellValue()).isEqualTo("Name");

    Cell headerCell2 = headerRow.getCell(1);
    assertThat(headerCell2).isNotNull();
    assertThat(headerCell2.getStringCellValue()).isEqualTo("Age");

    Cell headerCell3 = headerRow.getCell(2);
    assertThat(headerCell3).isNotNull();
    assertThat(headerCell3.getStringCellValue()).isEqualTo("Country");

    assertThat(sheet.getLastRowNum()).isEqualTo(3);

    // 데이터 행들을 추가적으로 검증할 수 있습니다.
    Row dataRow1 = sheet.getRow(1);
    assertThat(dataRow1).isNotNull();
    assertThat(dataRow1.getLastCellNum()).isEqualTo((short) 3);

    Cell dataCell1 = dataRow1.getCell(0);
    assertThat(dataCell1).isNotNull();
    assertThat(dataCell1.getStringCellValue()).isEqualTo("John");

    Cell dataCell2 = dataRow1.getCell(1);
    assertThat(dataCell2).isNotNull();
    assertThat(dataCell2.getStringCellValue()).isEqualTo("30");

    Cell dataCell3 = dataRow1.getCell(2);
    assertThat(dataCell3).isNotNull();
    assertThat(dataCell3.getStringCellValue()).isEqualTo("USA");

    combinedWorkbook.close();
  }
}
