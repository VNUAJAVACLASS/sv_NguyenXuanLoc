<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dang nhap</title>
</head>
<body>
	<h2>Dang nhap</h2>
	
	<c:if = test="${not empty error }">
		<p style="color: red;" >${error}</p>
	</c:if>
	
	<form action="login" method="POST">
	Ten dang nhap: <br>
	<input type="text" name="userName" value="rememberedUser" required><br>
	<br>Mat khau: <br>
	<input type="password" name="password" required><br><br>
	<label><input type="checkbox" name="remember">
		Ghi nho dang nhap</label><br><br>
		<input type="submit" value"Dang nhap">
	</form>
</body>
</html>