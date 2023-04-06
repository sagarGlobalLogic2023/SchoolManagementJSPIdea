package com.studentManagement.service;

import com.studentManagement.entity.Course;
import com.studentManagement.entity.Teacher;
import com.studentManagement.entity.User;
import com.studentManagement.repository.CourseDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    CourseDaoImpl courseDao = new CourseDaoImpl();
    TeacherService teacherService = new TeacherService();
    UserService userService = new UserService();

    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }

    public void addCourse(HttpServletRequest request, HttpServletResponse response) {
        Course course = new Course();
        course.setSubject(request.getParameter("subject"));
        List<Teacher> teachers = new ArrayList<>();
        User user = userService.findUserById(request.getParameter("teacher"));
        teachers.add(teacherService.getTeacherById(user));
        course.setTeachers(teachers);
        System.out.println(course);
        courseDao.add(course);
    }
}
