<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách Sách</title>
</head>
<body>
    <a href="books?action=create">Tạo sách mới</a>
    <br><br>
    <table border="1" cellpadding="10">
        <tr>
            <th>Id</th>
            <th>Tên sách</th>
            <th>Tác giả</th>
            <th>Hành động</th>
        </tr>
        <c:forEach var="book" items="${booksList}">
            <tr>
                <td>${book.id}</td>
                <td><a href="books?action=detail&id=${book.id}">${book.title}</a></td>
                <td>${book.author}</td>
                <td>
                    <a href="books?action=edit&id=${book.id}">Sửa</a> | 
                    <a href="books?action=delete&id=${book.id}" onclick="return confirm('Xóa sách này?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
