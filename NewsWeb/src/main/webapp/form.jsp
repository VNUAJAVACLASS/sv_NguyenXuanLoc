<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("isEdit", request.getAttribute("news") != null);
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? 'Sửa tin tức' : 'Tạo tin tức'}</title>
</head>
<body>
<h2>${isEdit ? 'Sửa tin tức' : 'Tạo tin tức'}</h2>

<form action="news" method="post">
    <c:if test="${isEdit}">
        <input type="hidden" name="id" value="${news.id}">
    </c:if>

    Tiêu đề:<br>
    <input type="text" name="title" value="${news.title}" required><br><br>

    Nội dung:<br>
    <textarea name="content" rows="5" cols="40" required>${news.content}</textarea><br><br>

    <input type="submit" value="${isEdit ? 'Cập nhật' : 'Tạo mới'}">
</form>

<br>
<a href="news">Quay lại danh sách</a>
</body>
</html>
