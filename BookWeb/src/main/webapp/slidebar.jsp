<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="sidebar">
    <h3>Xin chào, ${sessionScope.user}</h3>
    <ul>
        <li><a href="adminHome">📚 Quản lý sách</a></li>
        <li><a href="manageUsers.jsp">👤 Quản lý user</a></li>
        <li><a href="manageOrders.jsp">📦 Quản lý đơn hàng</a></li>
        <li><a href="logout.jsp">🚪 Đăng xuất</a></li>
    </ul>
</div>
