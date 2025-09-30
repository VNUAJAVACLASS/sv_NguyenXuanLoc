package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.UserDAO;
import model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Chuyển tới trang register.jsp
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // Lấy dữ liệu từ form
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        // Tạo user mới
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // ⚠️ nên mã hoá password (BCrypt/MD5) trong thực tế
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setRole("user");   // mặc định role là user
        newUser.setActive(true);   // mặc định active

        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.register(newUser);

        if (success) {
            req.setAttribute("message", "Đăng ký thành công, hãy đăng nhập!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}
