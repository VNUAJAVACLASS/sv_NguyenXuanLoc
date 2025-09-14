<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


	session.invalidate(); // Hủy Session của user bên phía webserver
	response.sendRedirect("login"); // Chuyển lại trang login

</body>
</html>