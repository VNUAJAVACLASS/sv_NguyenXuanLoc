<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    boolean isEdit = request.getAttribute("books") != null;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= isEdit ? "Sửa sách" : "Thêm sách" %></title>
</head>
<body>
    <h2><%= isEdit ? "Sửa sách" : "Thêm sách" %></h2>

    <form action="books" method="post">
        <c:if test="${books != null}">
            <input type="hidden" name="id" value="${books.id}">
        </c:if>

        Tên sách:<br>
        <input type="text" name="title" value="${books.title}" required><br><br>
        Tác giả:<br>
        <input type="text" name="author" value="${books.author}" required><br><br>
        Giá:<br>
        <input type="text" name="price" value="${books.price}" required><br><br>
        Link ảnh:<br>
        <input type="text" name="imagePath" value="${books.imagePath}" required><br><br>

        <input type="submit" value="<%= isEdit ? "Cập nhật" : "Tạo mới" %>">
    </form>

    <a href="books">Quay lại danh sách</a>
</body>
</html>
