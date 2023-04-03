package com.bookstore.bookstoreservlet.service;

import com.bookstore.bookstoreservlet.entity.User;
import com.bookstore.bookstoreservlet.repository.UserDaoImpl;

import java.util.List;

public class UserService {
    UserDaoImpl userDao = new UserDaoImpl();
    public boolean register(User user) {
        return userDao.addUser(user);
    }
    public User login(String email, String password) {
        var user = new User();


        user = userDao.getUserByEmail(email);

        if (user != null) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("Logged in!");
                return user;
            }
        }
        return null;
        //throw new UserNotFoundException();
    }
    public User findUser(String email) {
        return userDao.getUserByEmail(email);
    }
    public List<User> getUsers() {
        return userDao.findAll();
    }
    public boolean update(User user) {
        return userDao.updateUser(user);
    }
    public void block(String email) {
        var user = userDao.getUserByEmail(email);
        userDao.block(user);
    }
    public void unblock(String email) {
        var user = userDao.getUserByEmail(email);
        userDao.unblock(user);
    }
    public void makeAdmin(String email) {
        var user = userDao.getUserByEmail(email);
        userDao.makeAdmin(user);
    }
    public void makeUser(String email) {
        var user = userDao.getUserByEmail(email);
        userDao.makeUser(user);
    }
}
