package com.bookstore.bookstoreservlet.repository;

import com.bookstore.bookstoreservlet.entity.Book;

import com.bookstore.bookstoreservlet.util.HibernateUtil;
import org.hibernate.Transaction;
import org.hibernate.Session;


public class BookDaoImpl {

    public boolean addBook(Book book) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // save book object
            session.persist(book);

            // commit the transaction
            transaction.commit();
            return true;
        } catch (Exception e) {
            //if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
}
