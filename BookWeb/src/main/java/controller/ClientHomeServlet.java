package controller;

import dao.BookDAO;
import model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/clientHome")
public class ClientHomeServlet extends HttpServlet {

    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "detail": {
                    String idStr = req.getParameter("id");
                    Book detailBook = bookDAO.getBookById(Integer.parseInt(idStr));
                    req.setAttribute("book", detailBook);
                    req.getRequestDispatcher("detail.jsp").forward(req, resp);
                    break;
                }

                default: // list
                    List<Book> books = bookDAO.getAllBooks();
                    req.setAttribute("bookList", books);
                    req.getRequestDispatcher("user-list.jsp").forward(req, resp);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
