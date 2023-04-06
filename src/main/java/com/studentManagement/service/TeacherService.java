package com.studentManagement.service;

import com.studentManagement.entity.Teacher;
import com.studentManagement.entity.User;
import com.studentManagement.repository.TeacherDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    TeacherDaoImpl teacherDao = new TeacherDaoImpl();
    public List<User> getAllTeachers() {
        List<Teacher> teachers = teacherDao.findAll();
        List<User> users = new ArrayList<>();
        for (Teacher teacher : teachers) {
            users.add(teacher.getUser_id());
        }
        return users;
    }

    public Teacher getTeacherById(User user) {
        return teacherDao.find(user);
    }
}
