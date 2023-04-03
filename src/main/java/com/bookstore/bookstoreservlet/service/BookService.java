package com.bookstore.bookstoreservlet.service;

import com.bookstore.bookstoreservlet.entity.Book;
import com.bookstore.bookstoreservlet.repository.BookDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;

public class BookService {
    BookDaoImpl bookDao = new BookDaoImpl();
    public void addBook(HttpServletRequest request, HttpServletResponse response) {
        try {
            Book book = new Book();
            book.setAuthor(request.getParameter("author"));
            book.setName(request.getParameter("name"));
            book.setPrice(Long.parseLong(request.getParameter("price")));
            book.setDescription(request.getParameter("description"));
            book.setStock(Integer.parseInt(request.getParameter("stock")));
            Part part = request.getPart("cover");
            String fileName = part.getSubmittedFileName();
            book.setCover(fileName);

            var session = request.getSession(false);

            session.removeAttribute("successMessage");
            session.removeAttribute("failedMessage");



            if (bookDao.addBook(book)) {
                String path = request.getServletContext().getRealPath("") + "assets\\img";
                System.out.println("PATH: " + path);
                File file = new File(path);
                part.write(path + File.separator + fileName);
                session.setAttribute("successMessage", "Book added successful!");
            }
            else
                session.setAttribute("failedMessage", "Some problem occurred at the server!");

            response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/UserServlet?action=addBook");
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
