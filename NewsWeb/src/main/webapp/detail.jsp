<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>${news != null ? 'Sửa tin tức' : 'Tạo tin tức'}</title>
</head>
<body>
<h2>${news != null ? 'Sửa tin tức' : 'Tạo tin tức'}</h2>

<form action="news" method="post">
    <c:if test="${news != null}">
        <input type="hidden" name="id" value="${news.id}">
    </c:if>

    Tiêu đề:<br>
    <input type="text" name="title" value="${news != null ? news.title : ''}" required><br><br>

    Nội dung:<br>
    <textarea name="content" rows="5" cols="40" required><c:out value="${news != null ? news.content : ''}" /></textarea><br><br>

    <input type="submit" value="${news != null ? 'Cập nhật' : 'Tạo mới'}">
</form>

<br>
<a href="news">Quay lại danh sách</a>
</body>
</html>