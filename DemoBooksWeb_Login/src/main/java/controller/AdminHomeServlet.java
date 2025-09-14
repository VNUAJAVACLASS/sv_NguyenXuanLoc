package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Books;

/**
 * Servlet thực hiện chức năng trang chủ admin
 */
@WebServlet("/AdminHomeServlet")
public class AdminHomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor mặc định
     */
    public AdminHomeServlet() {
        super();
    }

    /**
     * Xử lý yêu cầu GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // Kiểm tra trạng thái đăng nhập từ session
        HttpSession session = req.getSession(false);
        String user = (session != null) ? (String) session.getAttribute("username") : null;

        if (user == null) {
            resp.sendRedirect("login"); // Chuyển hướng về trang login nếu chưa đăng nhập
            return;
        }

        // Giả định lấy danh sách sách (thay bằng logic thực tế từ database)
        List<Books> booksList = new ArrayList<>();
        booksList.add(new Books(1, "Sách 1", "Tác giả 1", 10000, "images/book1.jpg"));
        booksList.add(new Books(2, "Sách 2", "Tác giả 2", 15000, "images/book2.jpg"));

        // Đưa danh sách sách vào request scope
        req.setAttribute("booksList", booksList);

        req.setCharacterEncoding("UTF-8"); // Đặt mã hóa ký tự là UTF-8
        resp.setContentType("text/html;charset=UTF-8"); // Đặt loại nội dung là HTML với mã hóa UTF-8
        req.getRequestDispatcher("/adminHome.jsp").forward(req, resp); // Chuyển tiếp đến JSP
    }

    /**
     * Xử lý yêu cầu POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        doGet(req, resp); // Gọi lại phương thức doGet để xử lý
    }
}