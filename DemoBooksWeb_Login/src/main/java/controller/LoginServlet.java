package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    // Giả định tài khoản đăng nhập
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";

    // doGet được gọi khi LoginServlet được gọi đến từ một servlet khác (vd adminHome) hoặc từ URL
    // vd: http://localhost:8080/DemoNewsWeb_login/login
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra cookie "remember me" có tồn tại từ lần đăng nhập trước không
        // Cookie được trình duyệt gửi tới WebServer kèm theo mỗi request
        Cookie[] cookies = req.getCookies();
        String rememberedUser = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                // Chỉ lấy cookie với tên đã ghi vào trước đó
                if (c.getName().equals("rememberedUser")) {
                    rememberedUser = c.getValue(); // Nếu có lấy giá trị
                    break;
                }
            }
        }

        // Ghi giá trị cookie ra request scope để sử dụng trên trang login.jsp điền vào ô username
        req.setAttribute("rememberedUser", rememberedUser);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    // doPost được gọi đến khi submit form đăng nhập trên trang login với phương thức POST
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember"); // "on" nếu có check

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            // Đăng nhập thành công
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            if ("on".equals(remember)) { // có check ghi nhớ đăng nhập
                Cookie cookie = new Cookie("rememberedUser", username);
                cookie.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
                // Cookie được thêm vào response của servlet gửi về client
                // Sẽ được trình duyệt nhận và ghi vào bộ nhớ cookie của trình duyệt
                resp.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("rememberedUser", "");
                cookie.setMaxAge(0); // xoá cookie
                resp.addCookie(cookie);
            }

            resp.sendRedirect("adminHome");
        } else {
            // thuộc tính error được ghi vào request để báo lỗi trên trang login.jsp
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}