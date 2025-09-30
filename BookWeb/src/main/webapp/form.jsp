<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // Trang formBook.jsp dùng chung cho tạo mới và sửa sách
    request.setAttribute("isEdit", request.getAttribute("book") != null);
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Sửa sách' : 'Thêm sách mới'}</title>
</head>
<body>
    <h2>${isEdit ? 'Sửa sách' : 'Thêm sách mới'}</h2>

   <form action="adminHome" method="post">
    <input type="hidden" name="id" value="${book.id}">
    <label>Tên sách:</label>
    <input type="text" name="name" value="${book.name}" required><br><br>

    <label>Tác giả:</label>
    <input type="text" name="author" value="${book.author}" required><br><br>

    <label>Giá:</label>
    <input type="number" name="price" value="${book.price}" required><br><br>

    <label>Ảnh:</label>
    <input type="text" name="imagePath" value="${book.imagePath}"><br><br>

    <input type="submit" value="Lưu">
</form>

    <br>
    <a href="book">Quay lại danh sách</a>
</body>
</html>
