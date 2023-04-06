package com.studentManagement.service;

import com.studentManagement.entity.Student;
import com.studentManagement.entity.User;
import com.studentManagement.repository.StudentDaoImpl;

import java.util.List;

public class StudentService {
    StudentDaoImpl studentDao = new StudentDaoImpl();

    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }

    public void updateScore(String rollNumber, double score) {
        Student student = studentDao.find(rollNumber);
        student.setScore(score);
        studentDao.update(student);
    }

    public void makePresent(String rollNumber) {
        Student student = studentDao.find(rollNumber);
        student.setPresent(true);
        studentDao.update(student);
    }

    public void makeAbsent(String rollNumber) {
        Student student = studentDao.find(rollNumber);
        student.setPresent(false);
        studentDao.update(student);
    }

    public Student find(User user) {
        return studentDao.findByUser(user);
    }
}
