<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sách</title>
</head>
<body>
    <h2>${book.name}</h2>
    <p><b>Tác giả:</b> ${book.author}</p>
    <p><b>Giá:</b> ${book.price} VND</p>
    <p><b>Mã sách:</b> ${book.id}</p>
    <p>
        <b>Ảnh:</b><br>
        <img src="${book.imagePath}" alt="Ảnh sách" width="200"/>
    </p>
    <br>

    <!-- Nếu user là admin thì quay lại adminHome, ngược lại clientHome -->
    <c:choose>
        <c:when test="${sessionScope.user.role == 'admin'}">
            <a href="${pageContext.request.contextPath}/adminHome">Quay lại danh sách</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/clientHome">Quay lại danh sách</a>
        </c:otherwise>
    </c:choose>
</body>
</html>
