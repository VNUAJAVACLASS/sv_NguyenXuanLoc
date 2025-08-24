<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lỗi</title>
</head>
<body>
    <h2>Có lỗi xảy ra</h2>
    <p>
        <c:choose>
            <c:when test="${pageContext.errorData.statusCode == 404}">
                Không tìm thấy trang hoặc tài nguyên bạn yêu cầu.
            </c:when>
            <c:when test="${pageContext.errorData.statusCode == 500}">
                Lỗi máy chủ. Vui lòng thử lại sau.
            </c:when>
            <c:otherwise>
                Đã xảy ra lỗi không xác định.
            </c:otherwise>
        </c:choose>
    </p>
    <a href="news">Quay lại danh sách</a>
</body>
</html>