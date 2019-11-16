package com.cqust;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/FileDownloadServlet")
public class FileDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        //String path = request.getServletContext().getRealPath("/template/");
        String path = "F:/FileUploadAndDownload-master/temple/";
        String filename="";
        //下载的模板
        String name="123";
        File infile = new File(path);
        File[] files = infile.listFiles();
        for (File file : files) {
            String f=file.getName();
            if (f.startsWith(name)){
                filename=name+".xlsx";
            }
        }

        File file = new File(path + filename);
        if(file.exists()){
            //设置相应类型让浏览器知道用什么打开  用application/octet-stream也可以，看是什么浏览器
            response.setContentType("application/x-msdownload");
            //设置头信息
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            InputStream inputStream = new FileInputStream(file);
            ServletOutputStream ouputStream = response.getOutputStream();
            byte b[] = new byte[1024];
            int n ;
            while((n = inputStream.read(b)) != -1){
                ouputStream.write(b,0,n);
            }
            //关闭流
            ouputStream.close();
            inputStream.close();
            System.out.println("success");
        }else{
            //模板不存在的情况
            System.out.println("error");
        }
    }
}
