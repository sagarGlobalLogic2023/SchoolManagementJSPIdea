package com.studentManagement.service;

import com.studentManagement.entity.Student;
import com.studentManagement.repository.StudentDaoImpl;

import java.util.List;

public class StudentService {
    StudentDaoImpl studentDao = new StudentDaoImpl();

    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }
}
