package com.bookstore.bookstoreservlet.repository;

import com.bookstore.bookstoreservlet.entity.User;

import java.util.List;

public interface IUserDao {
    boolean addUser(User user);

    User getUserByEmail(String email);

    void makeUser(User user);

    void makeAdmin(User user);

    boolean updateUser(User user);

    List<User> findAll();

    void block(User user);

    void unblock(User user);
}
