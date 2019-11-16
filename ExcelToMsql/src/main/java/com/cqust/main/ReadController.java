package com.cqust.main;

import com.cqust.dao.InsertMapper;
import com.cqust.service.InsertService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReadController {
    @Autowired
    private InsertService insertService;

    @Test
    public void insert() {
        String table="Func";
        ExcelRead read = new ExcelRead();
        List<Map<String, String>> excelValue = read.getExcelValue();
        ApplicationContext app = new ClassPathXmlApplicationContext("spring-mybatis.xml");
        InsertMapper insertMapper = app.getBean(InsertMapper.class);
        insertService.insert(excelValue,table);
    }


}
