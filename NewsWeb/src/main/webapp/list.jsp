<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách tin tức</title>
</head>
<body>
<h2>Danh sách tin tức</h2>

<a href="news?action=create">Tạo tin mới</a>
<br><br>

<c:choose>
    <c:when test="${empty newsList}">
        <p>Chưa có tin tức nào.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="5">
            <tr>
                <th>ID</th>
                <th>Tiêu đề</th>
                <th>Hành động</th>
            </tr>
            <c:forEach var="news" items="${newsList}">
                <tr>
                    <td>${news.id}</td>
                    <td><a href="news?action=detail&id=${news.id}"><c:out value="${news.title}" /></a></td>
                    <td>
                        <a href="news?action=edit&id=${news.id}">Sửa</a> |
                        <a href="news?action=delete&id=${news.id}" onclick="return confirm('Xoá tin này?');">Xoá</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>