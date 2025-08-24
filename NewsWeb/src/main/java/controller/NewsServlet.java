package controller;

import dao.NewsDAO;
import model.News;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class NewsServlet extends HttpServlet {

    private NewsDAO newsDAO;

    @Override
    public void init() {
        newsDAO = new NewsDAO();
        try {
            // Khởi tạo dữ liệu mẫu nếu cần
            if (newsDAO.getAllNews().isEmpty()) {
                newsDAO.addNews(new News(0, "Tiêu đề 1", "Nội dung bài viết 1")); // ID sẽ được tự động tăng
                newsDAO.addNews(new News(0, "Tiêu đề 2", "Nội dung bài viết 2"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Không thể khởi tạo dữ liệu mẫu", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        String idStr = req.getParameter("id");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "create":
                    req.getRequestDispatcher("form.jsp").forward(req, resp);
                    break;

                case "edit":
                    if (idStr == null || idStr.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
                        return;
                    }
                    int idEdit = Integer.parseInt(idStr);
                    News editNews = newsDAO.findById(idEdit);
                    if (editNews == null) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "News not found");
                        return;
                    }
                    req.setAttribute("news", editNews);
                    req.getRequestDispatcher("form.jsp").forward(req, resp);
                    break;

                case "delete":
                    if (idStr == null || idStr.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
                        return;
                    }
                    int idDelete = Integer.parseInt(idStr);
                    newsDAO.deleteNews(idDelete);
                    resp.sendRedirect("news");
                    break;

                case "detail":
                    if (idStr == null || idStr.isEmpty()) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
                        return;
                    }
                    int idDetail = Integer.parseInt(idStr);
                    News detailNews = newsDAO.findById(idDetail);
                    if (detailNews == null) {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "News not found");
                        return;
                    }
                    req.setAttribute("news", detailNews);
                    req.getRequestDispatcher("detail.jsp").forward(req, resp);
                    break;

                default:
                    List<News> newsList = newsDAO.getAllNews();
                    req.setAttribute("newsList", newsList);
                    req.getRequestDispatcher("list.jsp").forward(req, resp);
                    break;
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (SQLException e) {
            throw new ServletException("Lỗi cơ sở dữ liệu", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String idStr = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Title and content are required");
            return;
        }

        try {
            if (idStr == null || idStr.isEmpty()) {
                News newNews = new News(0, title, content); // ID sẽ được tự động tăng
                newsDAO.addNews(newNews);
            } else {
                int id = Integer.parseInt(idStr);
                News existing = newsDAO.findById(id);
                if (existing != null) {
                    existing.setTitle(title);
                    existing.setContent(content);
                    newsDAO.updateNews(existing);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "News not found");
                    return;
                }
            }
            resp.sendRedirect("news");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (SQLException e) {
            throw new ServletException("Lỗi cơ sở dữ liệu", e);
        }
    }
}