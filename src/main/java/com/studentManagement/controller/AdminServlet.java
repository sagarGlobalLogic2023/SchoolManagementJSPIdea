package com.studentManagement.controller;

import com.studentManagement.entity.Course;
import com.studentManagement.entity.Teacher;
import com.studentManagement.entity.User;
import com.studentManagement.service.AdminService;
import com.studentManagement.service.CourseService;
import com.studentManagement.service.TeacherService;
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
    CourseService courseService = new CourseService();
    TeacherService teacherService = new TeacherService();

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
            case "newCourse" -> newCourse(request, response);
            default -> {}
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        String action = request.getParameter("action");
        switch (action) {
            case "addCourse" -> courseService.addCourse(request, response);
            default -> {}
        }
    }

    // Unblocking user
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

    // Blocking user
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

    // Redirecting to new-course page
    private void newCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        User user = (User) session.getAttribute("userData");
        List<User> teachers = teacherService.getAllTeachers();
        session.setAttribute("teacherList", teachers);
        if (user != null) {
            if (Objects.equals(user.getRole(), "admin") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/new-course.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }

   // Redirecting to courses page
    private void coursesPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        User user = (User) session.getAttribute("userData");
        List<Course> courses = courseService.getAllCourses();
        session.setAttribute("courseList", courses);
        if (user != null) {
            if (Objects.equals(user.getRole(), "admin") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/view-courses.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }

    // Redirecting to sessions page
    private void sessionsPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
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

    // Redirecting to users page
    private void usersPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
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

    // Redirecting to admin home page
    private void homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
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
}
