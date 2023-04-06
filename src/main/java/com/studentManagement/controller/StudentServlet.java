package com.studentManagement.controller;

import com.studentManagement.entity.Student;
import com.studentManagement.entity.User;
import com.studentManagement.service.StudentService;
import com.studentManagement.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "StudentServlet", value = "/StudentServlet")
public class StudentServlet extends HttpServlet {
    StudentService studentService = new StudentService();
    UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        String action = request.getParameter("action");
        switch (action) {
            case "homePage" -> homePage(request, response);
            default -> {}
        }
    }
    private void homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        User sessionUser = (User) session.getAttribute("userData");

        if (sessionUser != null) {
            List<User> users = userService.getUsers();

            User user = userService.findUser(sessionUser.getEmail());
            users.remove(user);
            session.setAttribute("userData", user);
            session.setAttribute("userList", users);
            Student student = studentService.find(user);
            session.setAttribute("studentData", student);
            if (Objects.equals(user.getRole(), "student") && user.isActive()) {
                System.out.println("active=" + user.isActive() + user.getEmail());
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/student/home.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        session.setAttribute("failedMessage", "You have been blocked by the user :/");
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
