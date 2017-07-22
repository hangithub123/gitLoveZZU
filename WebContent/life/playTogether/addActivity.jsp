<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form enctype="multipart/form-data" method="post"
		action="addActivity">
		phone:<input type="text" name="a_phone" /><br>
		请求标题：<input type="text" name="a_title" /><br>
		文件上传：<input type="file" name="a_img" /><br>  
		发布内容：<input type="text" name="a_content" /><br>
		praise：<input type="text" name="a_praiseNumber" /><br>  
		comment：<input type="text" name="a_commentNumber" /><br>
		分享URL：<input type="text" name="a_shareUrl" /><br> 
        <input type="submit" value="提交" />
	</form>
</body>
</html>