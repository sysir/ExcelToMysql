package com.cqust.main;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//最终成果！！！！
public class ReadExcel {
    private static JdbcTemplate jdbcTemplate;


    public static void main(String[] args) {
        String path = "F:/FileUploadAndDownload-master/temple/123.xlsx";
        GetExcel(path);


    }

    public static void GetExcel(String path) {
        File file = new File(path.trim());
        System.out.println(file);
        //filename-and-table
        String FileName = file.getName();
        String tablename = FileName.substring(0,FileName.lastIndexOf("."));

        //写入mysql
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-mybatis.xml");

        File xlsx = new File(path);
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(xlsx);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        //cell集合
        List<String> pd = new ArrayList<>();
        //sql片段
        StringBuilder sqlbuffer = new StringBuilder();

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            StringBuilder addSql = new StringBuilder(pd.size());
            Sheet hssfSheet = workbook.getSheetAt(numSheet);
            int physicalNumberOfCells = hssfSheet.getRow(0).getPhysicalNumberOfCells();
            if (hssfSheet == null) {
                continue;
            }


            // 循环行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                Row hssfRow = hssfSheet.getRow(rowNum);
                //循环列
                for (int i = 0; i < physicalNumberOfCells; i++) {
                    String cell = String.valueOf(hssfRow.getCell(i));
                    pd.add(cell);
                }
            }

            List<List> resultList = getSubList(pd, physicalNumberOfCells);
            String zx = "\"";
            int sqlbuLen = 0;
            boolean flag = true;
            for (List list1 : resultList) {
                for (Object o : list1) {
                    addSql = sqlbuffer.append(zx).append(o).append(zx).append(",");
                }
                if (flag) {
                    sqlbuLen = addSql.length();
                    flag = false;
                }
                System.out.println(sqlbuLen);
                addSql.deleteCharAt(sqlbuffer.length() - 1);


                applicationContext = new ClassPathXmlApplicationContext("spring-mybatis.xml");
                jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
                String stringend = ")";
                StringBuffer stringBuffer = new StringBuffer("insert into " + tablename + " VALUES (").append(addSql).append(stringend);
                try {
                    jdbcTemplate.execute(String.valueOf(stringBuffer));
                } catch (Exception e) {
                    System.out.println(stringBuffer + "插入失败" + e.toString());
                }
                //删除已插入的sqlbuffer
                sqlbuffer.delete(0, sqlbuLen);


            }
        }
    }

    private static List<List> getSubList(List list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % len == 0) {
                int count = i / len;
                List subList = (List) list.stream().limit((count + 1) * len).skip(count * len).collect(Collectors.toList());

                resultList.add(subList);
            }
        }
        return resultList;
    }
}