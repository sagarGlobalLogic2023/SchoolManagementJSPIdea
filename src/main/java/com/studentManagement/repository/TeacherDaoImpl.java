package com.studentManagement.repository;

import com.studentManagement.entity.Course;
import com.studentManagement.entity.Teacher;
import com.studentManagement.entity.User;
import com.studentManagement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TeacherDaoImpl {
    public List<Teacher> findAll() {
        Transaction transaction = null;
        List<Teacher> teachers = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // get all books
            teachers = session.createQuery("from Teacher ", Teacher.class).list();

            // commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return teachers;
    }

    public Teacher find(User user) {
        Transaction transaction = null;
        Teacher teacher = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            teacher = (Teacher) session.createQuery("from Teacher where user_id=:id")
                    .setParameter("id", user)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return teacher;
    }
}
