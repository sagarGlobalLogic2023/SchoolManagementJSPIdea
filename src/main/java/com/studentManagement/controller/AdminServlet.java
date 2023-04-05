package com.studentManagement.controller;

import com.studentManagement.entity.User;
import com.studentManagement.service.AdminService;
import com.studentManagement.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "AdminServlet", value = "/AdminServlet")
public class AdminServlet extends HttpServlet {

    AdminService adminService = new AdminService();
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        String action = request.getParameter("action");
        switch (action) {
            case "homePage" -> homePage(request, response);
            case "viewUsers" -> usersPage(request, response);
            case "viewSessions" -> sessionsPage(request, response);
            case "viewCourses" -> coursesPage(request, response);
            case "blockUser" -> blockUser(request, response);
            case "unBlockUser" -> unBlockUser(request, response);
            default -> {}
        }
    }

    private void unBlockUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        adminService.unblock(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/AdminServlet?action=viewUsers");
    }
    private void blockUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        adminService.block(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/AdminServlet?action=viewUsers");
    }
    private void coursesPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);

        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (Objects.equals(user.getRole(), "admin") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/view-courses.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }
    private void sessionsPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);

        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (Objects.equals(user.getRole(), "admin") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/view-sessions.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");

    }
    private void usersPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            List<User> users = userService.getUsers();
            users.remove(user);
            session.setAttribute("userList", users);
            if (Objects.equals(user.getRole(), "admin") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/view-users.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }
    private void homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        session.removeAttribute("failedMessage");
        session.removeAttribute("successMessage");
        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (Objects.equals(user.getRole(), "admin") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/home.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
