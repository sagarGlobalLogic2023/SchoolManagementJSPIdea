package com.studentManagement.repository;

import com.studentManagement.entity.Teacher;
import com.studentManagement.entity.User;

import java.util.List;

public interface TeacherDao {
    List<Teacher> findAll();

    Teacher find(User user);
}
