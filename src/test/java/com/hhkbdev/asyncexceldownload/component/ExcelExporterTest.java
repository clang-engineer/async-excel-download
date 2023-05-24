package com.hhkbdev.asyncexceldownload.component;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhkbdev.asyncexceldownload.domain.Field;
import com.hhkbdev.asyncexceldownload.domain.Field.Schema;
import com.hhkbdev.asyncexceldownload.domain.Field.Style;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExcelExporterTest {

  private ExcelExporter excelExporter;

  private String hashedFilename;

  private List<Field> fields;

  @BeforeEach
  void setUp() {
    hashedFilename = "test.xlsx";
    fields = Arrays.asList(
        new Field(new Schema("test-id1", "ID", "type", "comment"),
            new Style(10, HorizontalAlignment.CENTER)),
        new Field(new Schema("test-id2", "Name", "type", "comment"),
            new Style(11, HorizontalAlignment.RIGHT)),
        new Field(new Schema("test-id3", "Age", "type", "comment"),
            new Style(12, HorizontalAlignment.LEFT))
    );
    excelExporter = new ExcelExporter(hashedFilename, fields);
  }

  @AfterEach
  void tearDown() {
    try {
      Files.deleteIfExists(Paths.get(hashedFilename));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testWriteDataToExcel() throws Exception {
    List<HashMap> dataList = generateLargeData();

    excelExporter.uploadToServer(dataList);

    File file = new File(hashedFilename);
    assertThat(file).exists();

    FileInputStream fis = new FileInputStream(file);
    Workbook workbook = new XSSFWorkbook(fis);
    Sheet firstSheet = workbook.getSheetAt(0);
    Sheet secondSheet = workbook.getSheetAt(1);
    Row headerRow = firstSheet.getRow(0);

    assertThat(headerRow.getCell(0).getStringCellValue()).isEqualTo("ID");
    assertThat(headerRow.getCell(1).getStringCellValue()).isEqualTo("Name");
    assertThat(headerRow.getCell(2).getStringCellValue()).isEqualTo("Age");

    assertThat(workbook.getNumberOfSheets()).isEqualTo(2);
    assertThat(firstSheet.getLastRowNum()).isEqualTo(50000);
    assertThat(secondSheet.getLastRowNum()).isEqualTo(4321);

    workbook.close();
    fis.close();
  }

  private List<HashMap> generateLargeData() {
    List<HashMap> dataList = new ArrayList<>();
    for (int i = 1; i <= 54321; i++) {
      HashMap data = new HashMap();
      data.put("id", i);
      data.put("name", "name" + i);
      data.put("age", i);
      data.put("address", "address" + i);

      dataList.add(data);
    }
    return dataList;
  }
}

