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
            case "viewBill" -> viewBill(request, response);
            case "profile" -> profile(request, response);
            case "loginPage" -> loginPage(request, response);
            case "registerPage" -> registerPage(request, response);
            case "delete" -> deleteUser(request, response);
            default -> {}
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        userService.removeUser(id);
        String contextPath = "http://localhost:8080" + request.getContextPath();
        response.sendRedirect(contextPath + "/AdminServlet?action=viewUsers");
    }

    private void registerPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession(false);
        session.removeAttribute("userData");
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

    // Redirecting to user profile page
    private void profile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
        response.setHeader("Cache-control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires", -1);
        var session = request.getSession(false);
        String id = request.getParameter("id");
        User selectedUser = userService.findUserById(id);
        session.setAttribute("selectedUser", selectedUser);
        User sessionUser = (User) session.getAttribute("userData");
        if (sessionUser != null) {
            User user = userService.findUser(sessionUser.getEmail());
            session.setAttribute("userData", user);
            RequestDispatcher dispatcher = null;
            if (user.isActive()) {
                switch (selectedUser.getRole()) {
                    // redirecting to a profile page corresponding to the role of the user
                    case "admin" -> dispatcher = request.getRequestDispatcher("pages/admin/profile.jsp");
                    case "student" -> dispatcher = request.getRequestDispatcher("pages/student/profile.jsp");
                    case "teacher" -> dispatcher = request.getRequestDispatcher("pages/teacher/profile.jsp");
                }
                assert dispatcher != null;
                dispatcher.include(request, response);
                return;
            }
        }
        loginPage(request, response);
    }

    // Redirecting to view bill page
    private void viewBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
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

    // Redirecting to admin home page
    private void adminHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Blocking cache save of browser so that on pressing back button in browser, it will not load the
        // page from cache and instead check again if the user is logged in and unBlocked.
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
            // converting date stored in string to date object
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
        User sessionUser = (User) session.getAttribute("userData");
        User selectedUser = (User) session.getAttribute("selectedUser");
        System.out.println("sessionUser" + sessionUser.getFirstName());
        System.out.println("selectedUser" + selectedUser.getFirstName());
        if (selectedUser.getRole().equals("admin")) {
            sessionUser.setFirstName(request.getParameter("firstName"));
            sessionUser.setLastName(request.getParameter("lastName"));
            sessionUser.setPassword(request.getParameter("password"));
            if (userService.update(sessionUser)) {
                session.setAttribute("successMessage", "Updated data successfully!");
                session.setAttribute("userData", sessionUser);
            }
            else
                session.setAttribute("failedMessage", "Some problem occurred at the server!");
            String contextPath = "http://localhost:8080" + request.getContextPath();
            response.sendRedirect(contextPath + "/UserServlet?action=profile&id=" + sessionUser.getUser_id());
        } else {
            selectedUser.setFirstName(request.getParameter("firstName"));
            selectedUser.setLastName(request.getParameter("lastName"));
            selectedUser.setPassword(request.getParameter("password"));
            if (userService.update(selectedUser)) {
                session.setAttribute("successMessage", "Updated data successfully!");
                session.setAttribute("selectedUser", selectedUser);
            }
            else
                session.setAttribute("failedMessage", "Some problem occurred at the server!");
            String contextPath = "http://localhost:8080" + request.getContextPath();
            response.sendRedirect(contextPath + "/UserServlet?action=profile&id=" + selectedUser.getUser_id());
        }

    }

    // change role from admin to user
    @Deprecated
    private void makeUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        userService.makeUser(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        adminViewUsersPage(request, response);
    }

    // change role to admin
    @Deprecated
    private void makeAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        userService.makeAdmin(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        adminViewUsersPage(request, response);
    }

}
