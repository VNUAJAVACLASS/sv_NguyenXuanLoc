<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sách</title>
</head>
<body>
    <!-- Lấy đối tượng Book từ request scope (được đặt trong BookServlet) -->
    <h2>${book.name}</h2>
    <p><b>Tác giả:</b> ${book.author}</p>
    <p><b>Giá:</b> ${book.price} VND</p>
    <p><b>Mã sách:</b> ${book.id}</p>
    <p>
        <b>Ảnh:</b><br>
        <img src="${book.imagePath}" alt="Ảnh sách" width="200"/>
    </p>
    <br>
    <a href="${pageContext.request.contextPath}/book">Quay lại danh sách</a>
</body>
</html>
