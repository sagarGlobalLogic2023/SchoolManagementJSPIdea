package com.studentManagement.repository;

import com.studentManagement.entity.Student;
import com.studentManagement.entity.User;
import com.studentManagement.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentDaoImpl implements StudentDao{
    @Override
    public List<Student> findAll() {
        Transaction transaction = null;
        List<Student> students = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // get all books
            students = session.createQuery("from Student ", Student.class).list();

            // commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return students;
    }
    @Override
    public Student find(String rollNumber) {
        Transaction transaction = null;
        Student student = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            student = (Student) session.createQuery("from Student where id=:id")
                    .setParameter("id", rollNumber)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) transaction.rollback();
            exception.printStackTrace();
        }
        return student;
    }
    @Override
    public void update(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(student);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Student findByUser(User user) {
        Transaction transaction = null;
        Student student = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            student = (Student) session.createQuery("from Student where user_id=:id")
                    .setParameter("id", user)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) transaction.rollback();
            exception.printStackTrace();
        }
        return student;
    }
}
