<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <link rel="stylesheet" href="register.css">
</head>
<body>
    <div class="register-container">
        <h2>Đăng ký tài khoản</h2>

        <form action="register" method="post">
            <div class="form-group">
                <label>Tài khoản:</label>
                <input type="text" name="username" required>
            </div>

            <div class="form-group">
                <label>Mật khẩu:</label>
                <input type="password" name="password" required>
            </div>

            <div class="form-group">
                <label>Họ và tên:</label>
                <input type="text" name="fullName" required>
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email">
            </div>

            <div class="form-group">
                <label>Số điện thoại:</label>
                <input type="text" name="phone">
            </div>

            <button type="submit" class="btn">Đăng ký</button>
        </form>

        <!-- Hiển thị thông báo -->
        <p class="success">${message}</p>
        <p class="error">${error}</p>

        <p class="login-link">Đã có tài khoản? <a href="login.jsp">Đăng nhập</a></p>
    </div>
</body>
</html>
