package com.bookstore.bookstoreservlet.controller;

import com.bookstore.bookstoreservlet.entity.Book;
import com.bookstore.bookstoreservlet.entity.User;
import com.bookstore.bookstoreservlet.service.BookService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BookServlet", value = "/BookServlet")
@MultipartConfig
public class BookServlet extends HttpServlet {
    BookService bookService = new BookService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "add" -> bookService.addBook(request, response);
            default -> {}
        }
    }
}
