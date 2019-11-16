package com.cqust.main;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelRead {

    public List<Map<String, String>> getExcelValue()  {
        String path = "F:/FileUploadAndDownload-master/temple/123.xlsx";
        File xlsx = new File(path);
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(xlsx);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("表名", "first");
        //map.put("表名","first");
        //list.add("表名");

        List listfirst = new ArrayList();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet hssfSheet = workbook.getSheetAt(numSheet);
            int physicalNumberOfCells = hssfSheet.getRow(0).getPhysicalNumberOfCells();
            if (hssfSheet == null) {
                continue;
            }

            for (int i = 0; i < physicalNumberOfCells; i++) {
                Row hssfRow = hssfSheet.getRow(0);
                String cell = String.valueOf(hssfRow.getCell(i));
                listfirst.add(cell);
            }

            // 循环hang
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {

                Row hssfRow = hssfSheet.getRow(rowNum);
                //循环
                for (int i = 0; i < physicalNumberOfCells; i++) {

                    String cell = String.valueOf(hssfRow.getCell(i));
                    map.put(String.valueOf(listfirst.get(i)), cell);
                    list.add(map);
                }

            }
        }


        System.out.println(list);
        return list;

    }

}