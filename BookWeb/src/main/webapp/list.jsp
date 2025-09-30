<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách sách</title>
    <link rel="stylesheet" href="list.css">
</head>
<body>
<div class="container">
    <!-- Sidebar -->
    <jsp:include page="slidebar.jsp"/>

    <!-- Nội dung chính -->
    <div class="content">
        <h2>📚 Danh sách sách</h2>
        <a class="btn-add" href="adminHome?action=create">➕ Thêm sách mới</a>

        <table class="book-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tên sách</th>
                    <th>Tác giả</th>
                    <th>Giá</th>
                    <th>Ảnh</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${bookList}">
                    <tr>
                        <td>${book.id}</td>
                        <td>
                            <a href="adminHome?action=detail&id=${book.id}">
                                ${book.name}
                            </a>
                        </td>
                        <td>${book.author}</td>
                        <td>${book.price} đ</td>
                        <td>
                            <img src="${book.imagePath}" alt="Ảnh sách" width="60"/>
                        </td>
                        <td>
                            <a class="btn-edit" href="adminHome?action=edit&id=${book.id}">Sửa</a> |
                            <a class="btn-delete" href="adminHome?action=delete&id=${book.id}" 
                               onclick="return confirm('Bạn có chắc muốn xoá sách này?');">Xoá</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
