<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sách</title>
</head>
<body>
<h2>Danh sách sách</h2>

<table border="1" cellpadding="5">
    <tr>
        <th>ID</th>
        <th>Tên sách</th>
        <th>Tác giả</th>
        <th>Giá</th>
        <th>Ảnh</th>
    </tr>

    <!-- Duyệt qua danh sách bookList được đặt trong request scope -->
    <c:forEach var="book" items="${bookList}">
        <tr>
            <td>${book.id}</td>
            <td>
                <!-- Khi click sẽ đi đến detail.jsp của user -->
                <a href="clientHome?action=detail&id=${book.id}">${book.name}</a>
            </td>
            <td>${book.author}</td>
            <td>${book.price}</td>
            <td>
                <img src="${book.imagePath}" alt="Ảnh sách" width="80"/>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
