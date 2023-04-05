package com.studentManagement.service;

import com.studentManagement.repository.UserDaoImpl;

public class AdminService {
    UserDaoImpl userDao = new UserDaoImpl();
    public void block(String email) {
        var user = userDao.getUserByEmail(email);
        userDao.block(user);
    }
    public void unblock(String email) {
        var user = userDao.getUserByEmail(email);
        userDao.unBlock(user);
    }
}
