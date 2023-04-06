package com.studentManagement.repository;

import com.studentManagement.entity.Student;
import com.studentManagement.entity.User;

import java.util.List;

public interface StudentDao {
    List<Student> findAll();

    Student find(String rollNumber);

    void update(Student student);

    Student findByUser(User user);
}
