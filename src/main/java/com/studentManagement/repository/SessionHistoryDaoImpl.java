package com.studentManagement.repository;

import com.studentManagement.entity.SessionHistory;
import com.studentManagement.util.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SessionHistoryDaoImpl {
    public void addSession(SessionHistory sessionHistory) {
        Transaction transaction = null;
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // save sessionHistory object
            session.persist(sessionHistory);

            // commit the transaction
            transaction.commit();
        } catch (Exception e) {
            // if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public void updateSession(SessionHistory sessionHistory) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE SessionHistory set due = :due, start_date = :startDate, email = :email, end_date = :endDate, seconds = :seconds where email = :email and start_date = :startDate");
            query.setParameter("startDate", sessionHistory.getStart_date());
            query.setParameter("endDate", sessionHistory.getEnd_date());
            query.setParameter("seconds", sessionHistory.getSeconds());
            query.setParameter("email", sessionHistory.getEmail());
            query.setParameter("due", sessionHistory.getDue());
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            //if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    public List<SessionHistory> getAllSessions(String email) {
        Transaction transaction = null;
        List<SessionHistory> sessionHistories = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // get book object
            sessionHistories = session.createQuery("from SessionHistory where email = :email")
                    .setParameter("email", email)
                    .list();

            // commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return sessionHistories;
    }

    public int getTotalDue(String email) {
        Transaction transaction = null;
        int totalDue = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // get book object
            totalDue = (int) session.createQuery("select sum(due) from SessionHistory s where s.email = :email")
                    .setParameter("email", email)
                    .getSingleResultOrNull();

            System.out.println("before commit");
            // commit the transaction
            transaction.commit();
            System.out.println(totalDue);
            return totalDue;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return totalDue;
    }
}
