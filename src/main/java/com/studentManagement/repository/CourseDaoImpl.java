package com.studentManagement.repository;

import com.studentManagement.entity.Course;
import com.studentManagement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CourseDaoImpl implements CourseDao{
    @Override
    public List<Course> findAll() {
        Transaction transaction = null;
        List<Course> courses = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // get all books
            courses = session.createQuery("from Course", Course.class).list();

            // commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public void add(Course course) {
        Transaction transaction = null;
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // save user object
            session.persist(course);

            // commit the transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
