package com.studentManagement.service;

import com.studentManagement.entity.SessionHistory;
import com.studentManagement.entity.User;
import com.studentManagement.repository.UserDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class UserService {
    SessionHistoryService sessionHistoryService = new SessionHistoryService();
    UserDaoImpl userDao = new UserDaoImpl();
    public User findUser(String email) {
        return userDao.getUserByEmail(email);
    }
    public List<User> getUsers() {
        return userDao.findAll();
    }
    public boolean update(User user) {
        return userDao.updateUser(user);
    }
    public void makeAdmin(String email) {
        var user = userDao.getUserByEmail(email);
        userDao.makeAdmin(user);
    }
    public void makeUser(String email) {
        var user = userDao.getUserByEmail(email);
        userDao.makeUser(user);
    }

    public void register(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = new User();
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setRole(request.getParameter("role"));
            if (user.getRole().equals("admin")) {
                user.setActive(true);
            }

            HttpSession session = request.getSession();

            session.removeAttribute("successMessage");
            session.removeAttribute("failedMessage");

            if (userDao.addUser(user))
                session.setAttribute("successMessage", "User registration successful!");
            else
                session.setAttribute("failedMessage", "Some problem occurred at the server!");

            String contextPath = "http://localhost:8080" + request.getContextPath();
            response.sendRedirect(contextPath + "/UserServlet?action=registerPage");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        var session = request.getSession();
        User user = userDao.getUserByEmail(email);
        var userList = userDao.findAll();
        String contextPath = "http://localhost:8080" + request.getContextPath();
        session.setAttribute("failedMessage", "User not found!");
        if (user != null) {
            if (user.isActive()) {
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    session.setAttribute("userData", user);
                    session.setAttribute("userList", userList);
                    SessionHistory sessionHistory = new SessionHistory();
                    sessionHistory.setEmail(user.getEmail());
                    sessionHistory.setStart_date(String.valueOf(new Date(session.getCreationTime())));
                    sessionHistoryService.addSession(sessionHistory);
                    session.setAttribute("sessionData", sessionHistory);
                    switch (user.getRole()) {
                        case "admin" -> response.sendRedirect(contextPath + "/AdminServlet?action=homePage");
                        case "teacher" -> response.sendRedirect(contextPath + "/TeacherServlet?action=homePage");
                        case "student" -> response.sendRedirect(contextPath + "/StudentServlet?action=homePage");
                        default -> {}
                    }
                    return;
                }
                session.setAttribute("failedMessage", "Invalid credentials!");
            } else session.setAttribute("failedMessage", "Account not activated!");
        }

        response.sendRedirect(contextPath + "/UserServlet?action=loginPage");
    }
}
