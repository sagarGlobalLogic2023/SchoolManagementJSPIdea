package com.bookstore.bookstoreservlet.controller;

import com.bookstore.bookstoreservlet.entity.SessionHistory;
import com.bookstore.bookstoreservlet.entity.User;
import com.bookstore.bookstoreservlet.service.SessionHistoryService;
import com.bookstore.bookstoreservlet.service.UserService;
import org.hibernate.Session;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
            case "block" -> block(request, response);
            case "unblock" -> unblock(request, response);
            case "makeAdmin" -> makeAdmin(request, response);
            case "makeUser" -> makeUser(request, response);
            case "adminViewUsers" -> adminViewUsers(request, response);
            case "adminHome" -> adminHome(request, response);
            case "addBook" -> addBook(request, response);
            case "viewBill" -> viewBill(request, response);
            case "profile" -> profile(request, response);
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
            case "register" -> register(request, response);
            case "login" -> login(request, response);
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
            if (user.isAdmin()) {
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
            if (user.isAdmin()) {
                var email = request.getParameter("email");
                List<SessionHistory> sessionHistories = sessionHistoryService.getAllSessions(email);
                int totalDue = 0;
                for (SessionHistory sessionHistory : sessionHistories) {
                    totalDue += sessionHistory.getDue();
                }
                session.setAttribute("sessionList", sessionHistories);
                session.setAttribute("totalDue", totalDue);
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin-view-bill.jsp");
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
            if (user.isAdmin()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin-add-book.jsp");
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
            if (user.isAdmin()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin-home.jsp");
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
            if (user.isAdmin() && !user.isBlocked()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/admin-view-users.jsp");
                dispatcher.include(request, response);
                return;
            }
        }
        loginPage(request, response);
    }
    private void loginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession(false);
        session.invalidate();
        RequestDispatcher dispatcher = request.getRequestDispatcher("pages/sign-in.jsp");
        dispatcher.include(request, response);
    }
    private void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        var session = request.getSession(false);
        SessionHistory sessionHistory = new SessionHistory();

        User user = (User) session.getAttribute("userData");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        var session = request.getSession();
        User user = userService.login(email, password);
        var userList = userService.getUsers();
        userList.remove(user);
        if (user != null) {
            session.setAttribute("userData", user);
            session.setAttribute("userList", userList);
            SessionHistory sessionHistory = new SessionHistory();
            sessionHistory.setEmail(user.getEmail());
            sessionHistory.setStart_date(String.valueOf(new Date(session.getCreationTime())));
            sessionHistoryService.addSession(sessionHistory);
            session.setAttribute("sessionData", sessionHistory);
            if (user.isAdmin()) adminHome(request, response);
            else response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/pages/user-home.jsp");
        }
        else {
            session.setAttribute("failedMessage", "Invalid credentials!");
            response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/");
        }

    }
    private void register(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = new User();
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setAdmin(request.getParameter("isAdmin") != null);

            HttpSession session = request.getSession();

            session.removeAttribute("successMessage");
            session.removeAttribute("failedMessage");

            if (userService.register(user))
                session.setAttribute("successMessage", "User registration successful!");
            else
                session.setAttribute("failedMessage", "Some problem occurred at the server!");

            response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/pages/sign-up.jsp");
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = request.getSession(false);
        User user = new User();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(((User) session.getAttribute("userData")).getEmail());
        user.setAdmin(((User) session.getAttribute("userData")).isAdmin());
        user.setBlocked(((User) session.getAttribute("userData")).isBlocked());
        user.setFavourites(((User) session.getAttribute("userData")).getFavourites());
        user.setCompleted(((User) session.getAttribute("userData")).getCompleted());
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
    private void unblock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        userService.unblock(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        /*var dispatcher = request.getRequestDispatcher("pages/admin-view-users.jsp");
        dispatcher.forward(request, response);*/
        //response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/pages/admin-view-users.jsp");
        //adminViewUsers(request, response);
        adminViewUsersPage(request, response);
    }
    private void block(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        userService.block(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        adminViewUsersPage(request, response);
    }
    private void makeUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        userService.makeUser(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        /*var dispatcher = request.getRequestDispatcher("pages/admin-view-users.jsp");
        dispatcher.include(request, response);*/
        //response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/pages/admin-view-users.jsp");
        //adminViewUsers(request, response);
        adminViewUsersPage(request, response);
    }
    private void makeAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var session = request.getSession(false);
        var email = request.getParameter("email");
        userService.makeAdmin(email);
        var userList = userService.getUsers();
        userList.remove((User) session.getAttribute("userData"));
        session.setAttribute("userList", userList);
        /*var dispatcher = request.getRequestDispatcher("pages/admin-view-users.jsp");
        dispatcher.include(request, response);*/
        //response.sendRedirect("http://localhost:8080/bookstoreServlet_war_exploded/pages/admin-view-users.jsp");
        //adminViewUsers(request, response);
        adminViewUsersPage(request, response);
    }

    // TODO 1: Update session data in every request.
    // TODO 2: Add a Web filter to check if user is blocked or not.
}
