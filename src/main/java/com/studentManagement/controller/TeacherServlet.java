package com.studentManagement.controller;

import com.studentManagement.entity.Student;
import com.studentManagement.entity.User;
import com.studentManagement.service.StudentService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "TeacherServlet", value = "/TeacherServlet")
public class TeacherServlet extends HttpServlet {
    StudentService studentService = new StudentService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        String action = request.getParameter("action");
        switch (action) {
            case "homePage" -> homePage(request, response);
            case "attendance" -> attendance(request, response);
            case "makeAbsent" -> makeAbsent(request, response);
            case "makePresent" -> makePresent(request, response);
            default -> {}
        }
    }

    private void makePresent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roll_number = request.getParameter("roll_number");
        studentService.makePresent(roll_number);
        attendance(request, response);
    }

    private void makeAbsent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roll_number = request.getParameter("roll_number");
        studentService.makeAbsent(roll_number);
        attendance(request, response);
    }

    // Redirecting to attendance page
    private void attendance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        List<Student> students = studentService.getAllStudents();
        session.setAttribute("studentList", students);
        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (Objects.equals(user.getRole(), "teacher") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/teacher/attendance.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }

    // Redirecting to teacher home page
    private void homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        session.removeAttribute("failedMessage");
        session.removeAttribute("successMessage");
        List<Student> students = studentService.getAllStudents();
        session.setAttribute("studentList", students);
        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (Objects.equals(user.getRole(), "teacher") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/teacher/home.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        String action = request.getParameter("action");
        switch (action) {
            case "updateScore" -> updateScore(request, response);
            default -> {}
        }
    }

    private void updateScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double score = Double.parseDouble(request.getParameter("score"));
        String roll_number = request.getParameter("roll_number");
        studentService.updateScore(roll_number, score);
        homePage(request, response);
    }
}
