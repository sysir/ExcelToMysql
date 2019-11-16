package com.cqust;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends BaseServlet {

  private static final long serialVersionUID = 1L;

  @Override public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    super.doGet(request, response);
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    //Check that we have a file upload request 检查是否有文件上传请求
    boolean isMultipart= ServletFileUpload.isMultipartContent(request);
    if(isMultipart){
      // Create a factory for disk-based file items 创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
      DiskFileItemFactory factory=new DiskFileItemFactory();
      //Create a new file upload handler创建ServletFileUpload对象，并设置上传文件的大小限制
      ServletFileUpload upload=new ServletFileUpload(factory);
      upload.setSizeMax(40 * 1024 * 1024);// 以byte为单位 不能超过40M

      // Set overall request size constraint
//			upload.setSizeMax(yourMaxRequestSize);

      // Parse the request 调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象
      try {
        List<FileItem> items = upload.parseRequest(request);
        // Process the uploaded items
        Iterator<FileItem> iter=items.iterator();
        while(iter.hasNext()){
          FileItem item=iter.next();
          //遍历list，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
          if(item.isFormField()==true){// 普通表单元素
            String name=item.getFieldName();// name属性值
            String value=item.getString();// name对应的value值
            System.out.println("form field："+name+"--"+value);
          }else{
            String fieldName = item.getFieldName();
            String fileName = item.getName();// 文件名称
            String contentType = item.getContentType();
            boolean isInMemory = item.isInMemory();
            long sizeInBytes = item.getSize();
            System.out.println("file upload："+fieldName+"--"+fileName+"--"+contentType+"--"+isInMemory+"--"+sizeInBytes);
          }

          // Process a file upload
          if(!item.isFormField()){
            //保存到指定盘符
            String fileName=item.getName();
            //判断文件后缀是否为excel文件
            List<String> filTypes = Arrays.asList("xlsx","xls");

            String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
            boolean flag = filTypes.contains(fileType);
            if (flag){
              File savePath=new File("F:\\FileUploadAndDownload-master\\temple");
              if(!savePath.exists()) {
                savePath.mkdirs();
              }
              File uploadedFile=new File(savePath+File.separator+fileName);
              //输出路径为out
              //request.getServletContext().getRealPath("/"+fileName)
              item.write(uploadedFile);
              request.getRequestDispatcher("/message.jsp").forward(request,response);
            }}else{
            System.out.println("不是指定后缀文件，上传失败");
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }
}
