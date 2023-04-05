package com.studentManagement.controller;

import com.studentManagement.entity.SessionHistory;
import com.studentManagement.entity.User;
import com.studentManagement.service.SessionHistoryService;
import com.studentManagement.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    UserService userService = new UserService();
    SessionHistoryService sessionHistoryService = new SessionHistoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        String action = request.getParameter("action");
        switch (action) {
            case "signOut" -> signOut(request, response);
            case "makeAdmin" -> makeAdmin(request, response);
            case "makeUser" -> makeUser(request, response);
            case "adminViewUsers" -> adminViewUsers(request, response);
            case "adminHome" -> adminHome(request, response);
            case "addBook" -> addBook(request, response);
            case "viewBill" -> viewBill(request, response);
            case "profile" -> profile(request, response);
            case "loginPage" -> loginPage(request, response);
            case "registerPage" -> registerPage(request, response);
            default -> {}
        }
    }

    private void registerPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession(false);
        session.invalidate();
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/sign-up.jsp");
        dispatcher.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        String action = request.getParameter("action");
        switch (action) {
            case "register" -> userService.register(request, response);
            case "login" -> userService.login(request, response);
            case "update" -> update(request, response);
            default -> {}
        }
    }
    private void profile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (Objects.equals(user.getRole(), "admin")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/profile.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        loginPage(request, response);
    }
    private void viewBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);

        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (Objects.equals(user.getRole(), "admin")) {
                var email = request.getParameter("email");
                List<SessionHistory> sessionHistories = sessionHistoryService.getAllSessions(email);
                sessionHistories.removeIf(s -> s.getEnd_date() == null);
                int totalDue = 0;
                for (SessionHistory sessionHistory : sessionHistories) {
                    totalDue += sessionHistory.getDue();
                }
                session.setAttribute("sessionList", sessionHistories);
                session.setAttribute("totalDue", totalDue);
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/view-bill.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        loginPage(request, response);
    }
    private void addBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);

        if (session != null && session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (user.getRole() == "admin") {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/add-book.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        loginPage(request, response);
    }
    private void adminHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);

        if (session.getAttribute("userData") != null) {
            User user = (User) session.getAttribute("userData");
            if (Objects.equals(user.getRole(), "admin")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/home.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        loginPage(request, response);
    }
    private void adminViewUsersPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/UserServlet?action=adminViewUsers");
    }
    private void adminViewUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);

        if (session.getAttribute("userData") != null) {
            List<User> users = userService.getUsers();
            User sessionUser = (User) session.getAttribute("userData");
            User user = userService.findUser(sessionUser.getEmail());
            users.remove(user);
            session.setAttribute("userData", user);
            session.setAttribute("userList", users);
            if (Objects.equals(user.getRole(), "admin") && user.isActive()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin/view-users.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        loginPage(request, response);
    }
    private void loginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession(false);
        //session.invalidate();
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/sign-in.jsp");
        dispatcher.include(request, response);
    }
    private void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        var session = request.getSession(false);
        SessionHistory sessionHistory = new SessionHistory();

        User user = (User) session.getAttribute("userData");
        if (user != null) {
            SessionHistory sessionData = (SessionHistory) session.getAttribute("sessionData");
            sessionHistory.setEmail(user.getEmail());
            sessionHistory.setEnd_date(String.valueOf(new Date(session.getLastAccessedTime())));
            sessionHistory.setStart_date(sessionData.getStart_date());
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date startTime = null;
            Date endTime = null;
            try {
                startTime = format.parse(sessionHistory.getStart_date());
                endTime = format.parse(sessionHistory.getEnd_date());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            sessionHistory.setSeconds((int) TimeUnit.MILLISECONDS.toSeconds(endTime.getTime() - startTime.getTime()));
            sessionHistory.setDue(sessionHistory.getSeconds() / 5);
            sessionHistoryService.updateSession(sessionHistory);

            session.invalidate();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = request.getSession(false);
        User user = new User();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(((User) session.getAttribute("userData")).getEmail());
        user.setRole(((User) session.getAttribute("userData")).getRole());
        user.setActive(((User) session.getAttribute("userData")).isActive());
        if (userService.update(user)) {
            var users = userService.getUsers();
            users.remove(user);
            session.setAttribute("userList", users);
            session.setAttribute("successMessage", "Updated data successfully!");
            session.setAttribute("userData", user);
        }
        else
            session.setAttribute("failedMessage", "Some problem occurred at the server!");
        response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/pages/profile.jsp");
        return;

    }
    private void makeUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        userService.makeUser(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        adminViewUsersPage(request, response);
    }
    private void makeAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        userService.makeAdmin(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        adminViewUsersPage(request, response);
    }

    // TODO 1: Update session data in every request.
    // TODO 2: Add a Web filter to check if user is blocked or not.
}
