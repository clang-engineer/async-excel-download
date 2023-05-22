package com.hhkbdev.asyncexceldownload;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelWriter {
    private static final int BATCH_SIZE = 1000;
    private static final int THREAD_POOL_SIZE = 4;

    public void saveDataToExcel(String filePath, Map<String, List<String>> dataMap) throws Exception {
        Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        CompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);

        for (Map.Entry<String, List<String>> entry : dataMap.entrySet()) {
            String sheetName = entry.getKey();
            List<String> dataList = entry.getValue();

            completionService.submit(() -> {
                Sheet sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    sheet = workbook.createSheet(sheetName);
                }

                int lastRowNum = sheet.getLastRowNum();
                int rowIndex = lastRowNum + 1;

                for (int i = 0; i < dataList.size(); i++) {
                    Row row = sheet.createRow(rowIndex + i);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(dataList.get(i));
                }
                return null;
            });
        }

        for (int i = 0; i < dataMap.size(); i++) {
            completionService.take().get();
        }

        executorService.shutdown();
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
