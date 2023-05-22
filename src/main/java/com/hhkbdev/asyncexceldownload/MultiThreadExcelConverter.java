package com.hhkbdev.asyncexceldownload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class MultiThreadExcelConverter {
  public Workbook getCombinedWorkbook(List<Map<String, String>> dataList) {
    ExcelConverter converter = new ExcelConverter();

    ExecutorService executorService = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors());

    List<Callable<Workbook>> tasks = new ArrayList<>();
    for (int i = 0; i < dataList.size(); i++) {
      final int index = i;
      Callable<Workbook> task = () -> {
        List<Map<String, String>> subList = dataList.subList(index, index + 1);
        return converter.convertToWorkbook(subList);
      };
      tasks.add(task);
    }

    List<Future<Workbook>> results;
    try {
      results = executorService.invokeAll(tasks);
    } catch (InterruptedException e) {
      executorService.shutdownNow();
      return null;
    }

    executorService.shutdown();

    Workbook combinedWorkbook = new SXSSFWorkbook();
    Sheet combinedSheet = combinedWorkbook.createSheet("Combined Data");
    int rowIndex = 0;

    for (Future<Workbook> result : results) {
      try {
        Workbook workbook = result.get();
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
          Row newRow = combinedSheet.createRow(rowIndex++);
          for (Cell cell : row) {
            Cell newCell = newRow.createCell(cell.getColumnIndex());
            newCell.setCellValue(cell.getStringCellValue());
          }
        }

        workbook.close();
      } catch (InterruptedException | ExecutionException | IOException e) {
        e.printStackTrace();
      }
    }

    return combinedWorkbook;
  }
}
