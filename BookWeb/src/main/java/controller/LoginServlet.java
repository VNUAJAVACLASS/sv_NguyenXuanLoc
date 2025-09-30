package controller;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        User user = userDAO.login(username, password);

        if (user != null) {
            HttpSession session = req.getSession();
           
            session.setAttribute("user", user.getFullName());

            // Ghi nhớ tài khoản bằng cookie
            if ("on".equals(remember)) {
                Cookie ckUser = new Cookie("username", username);
                Cookie ckPass = new Cookie("password", password);
                ckUser.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
                ckPass.setMaxAge(60 * 60 * 24 * 7);
                resp.addCookie(ckUser);
                resp.addCookie(ckPass);
            }

            resp.sendRedirect("adminHome");
        } else {
            req.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
