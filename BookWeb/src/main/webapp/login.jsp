<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String savedUsername = "";
    String savedPassword = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("username".equals(c.getName())) {
                savedUsername = c.getValue();
            }
            if ("password".equals(c.getName())) {
                savedPassword = c.getValue();
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
    <div class="login-container">
        <h2>Đăng nhập</h2>

        <form action="login" method="post">
            <div class="form-group">
                <label>Tài khoản:</label>
                <input type="text" name="username" value="<%= savedUsername %>" required>
            </div>

            <div class="form-group">
                <label>Mật khẩu:</label>
                <input type="password" name="password" value="<%= savedPassword %>" required>
            </div>

            <!-- checkbox căn trái -->
            <div class="form-group checkbox">
                <input type="checkbox" name="remember" 
                       <%= (savedUsername.isEmpty() ? "" : "checked") %> >
                <span>Ghi nhớ đăng nhập</span>
            </div>

            <button type="submit" class="btn">Đăng nhập</button>
        </form>

        <p class="error">${error}</p>
    </div>
</body>
</html>
