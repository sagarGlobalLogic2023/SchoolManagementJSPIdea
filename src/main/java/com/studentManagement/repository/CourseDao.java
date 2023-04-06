package com.studentManagement.repository;

import com.studentManagement.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> findAll();

    void add(Course course);
}
