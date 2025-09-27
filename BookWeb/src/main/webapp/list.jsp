<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sách</title>
</head>
<body>
<h2>Danh sách sách</h2>

<!-- Link gọi đến servlet "book" với action=create -->
<a href="book?action=create">Thêm sách mới</a>
<br><br>

<table border="1" cellpadding="5">
    <tr>
        <th>ID</th>
        <th>Tên sách</th>
        <th>Tác giả</th>
        <th>Giá</th>
        <th>Ảnh</th>
        <th>Hành động</th>
    </tr>

    <!-- Duyệt qua danh sách bookList được đặt trong request scope -->
    <c:forEach var="book" items="${bookList}">
        <tr>
            <td>${book.id}</td>
            <td>
                <!-- Khi click sẽ đi đến detailBook.jsp -->
                <a href="book?action=detail&id=${book.id}">${book.name}</a>
            </td>
            <td>${book.author}</td>
            <td>${book.price}</td>
            <td>
                <img src="${book.imagePath}" alt="Ảnh sách" width="80"/>
            </td>
            <td>
                <a href="book?action=edit&id=${book.id}">Sửa</a> |
                <a href="book?action=delete&id=${book.id}" 
                   onclick="return confirm('Bạn có chắc muốn xoá sách này?');">Xoá</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
