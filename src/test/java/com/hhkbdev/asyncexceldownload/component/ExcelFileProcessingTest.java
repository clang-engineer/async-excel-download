package com.hhkbdev.asyncexceldownload.component;

import static org.assertj.core.api.Assertions.assertThat;

import com.hhkbdev.asyncexceldownload.component.ExcelFileProcessing;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExcelFileProcessingTest {

  @Test
  public void testWriteDataToExcel() {
    List<HashMap> dataList = generateLargeData();
    String filePath = "test.xlsx";
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Data");

    ExcelFileProcessing excelFileProcessing = new ExcelFileProcessing();
    try {
      // 데이터 작성
      excelFileProcessing.writeDataToExcel(filePath, workbook, sheet, 0, dataList);

      // 파일이 제대로 생성되었는지 확인
      assertThat(Files.exists(Paths.get(filePath))).isTrue();

      // 파일 삭제 (테스트 후 정리)
      Files.deleteIfExists(Paths.get(filePath));
    } catch (IOException e) {
      e.printStackTrace();
      Assertions.fail("An exception occurred: " + e.getMessage());
    }
  }

  private List<HashMap> generateLargeData() {
    List<HashMap> dataList = new ArrayList<>();
    for (int i = 1; i <= 50000; i++) {
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

