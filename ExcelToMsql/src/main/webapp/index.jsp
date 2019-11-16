<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<body>
<h2>FileUploadAndDownload</h2>
</body>
<form action="${pageContext.request.contextPath}/FileUploadServlet" enctype="multipart/form-data" method="post">
    上传文件：<input type="file" name="file1"><br/>
    <input type="submit" value="提交">
</form>

<a href="${pageContext.request.contextPath}/FileDownloadServlet">下载</a>
<br/>


dbfygdyf
</html>
