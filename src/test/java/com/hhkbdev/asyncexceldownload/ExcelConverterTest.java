package com.hhkbdev.asyncexceldownload;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhkbdev.asyncexceldownload.domain.Field;
import com.hhkbdev.asyncexceldownload.domain.Field.Schema;
import com.hhkbdev.asyncexceldownload.domain.Field.Style;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExcelConverterTest {

  private Field field1 = new Field(
      new Schema(1L, "Name", "String", "이름"),
      new Style(HorizontalAlignment.CENTER));
  private Field field2 = new Field(
      new Schema(2L, "Age", "Integer", "나이"),
      new Style(HorizontalAlignment.RIGHT));
  private Field field3 = new Field(
      new Schema(3L, "Country", "String", "국가"),
      new Style(HorizontalAlignment.LEFT));

  private Map data1 = Map.of("Name", "John", "Age", "20", "Country", "USA");
  private Map data2 = Map.of("Name", "Jane", "Age", "25", "Country", "Canada");
  private Map data3 = Map.of("Name", "Peter", "Age", "35", "Country", "Australia");

  private ExcelConverter converter = new ExcelConverter(Arrays.asList(field1, field2, field3));

  @BeforeEach
  void setUp() {
  }

  @Test
  void testConvertToWorkbook() throws Exception {
    List dataList = Arrays.asList(data1, data2, data3);

    Workbook workbook = converter.convertToWorkbook(dataList);

    assertThat(workbook).isNotNull();

    Sheet sheet = workbook.getSheetAt(0);
    assertThat(sheet).isNotNull();

    Row headerRow = sheet.getRow(0);
    assertThat(headerRow).isNotNull();
    assertThat(Integer.valueOf(headerRow.getLastCellNum())).isEqualTo(3);

    Cell headerCell1 = headerRow.getCell(0);
    assertThat(headerCell1).isNotNull();
    assertThat(headerCell1.getStringCellValue()).isEqualTo(field1.getSchema().getComment());

    Cell headerCell2 = headerRow.getCell(1);
    assertThat(headerCell2).isNotNull();
    assertThat(headerCell2.getStringCellValue()).isEqualTo(field2.getSchema().getComment());

    Cell headerCell3 = headerRow.getCell(2);
    assertThat(headerCell3).isNotNull();
    assertThat(headerCell3.getStringCellValue()).isEqualTo(field3.getSchema().getComment());

    Row dataRow1 = sheet.getRow(1);
    assertThat(dataRow1).isNotNull();
    assertThat(Integer.valueOf(dataRow1.getLastCellNum())).isEqualTo(3);

    Cell dataCell1 = dataRow1.getCell(0);
    assertThat(dataCell1).isNotNull();
    assertThat(dataCell1.getStringCellValue()).isEqualTo("John");
    assertThat(dataCell1.getCellStyle().getAlignment()).isEqualTo(HorizontalAlignment.CENTER);

    Cell dataCell2 = dataRow1.getCell(1);
    assertThat(dataCell2).isNotNull();
    assertThat(dataCell2.getStringCellValue()).isEqualTo("20");
    assertThat(dataCell2.getCellStyle().getAlignment()).isEqualTo(HorizontalAlignment.RIGHT);

    Cell dataCell3 = dataRow1.getCell(2);
    assertThat(dataCell3).isNotNull();
    assertThat(dataCell3.getStringCellValue()).isEqualTo("USA");
    assertThat(dataCell3.getCellStyle().getAlignment()).isEqualTo(HorizontalAlignment.LEFT);

    workbook.close();
  }
}
