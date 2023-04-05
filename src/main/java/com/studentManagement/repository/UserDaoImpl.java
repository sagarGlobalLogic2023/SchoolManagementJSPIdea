package com.studentManagement.repository;

import com.studentManagement.entity.User;
import com.studentManagement.util.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements IUserDao {
    @Override
    public boolean addUser(User user) {
        Transaction transaction = null;
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // save user object
            session.persist(user);

            // commit the transaction
            transaction.commit();
            return true;
        } catch (Exception e) {
            // if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public User getUserByEmail(String email) {
        Transaction transaction = null;
        User user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = (User) session.createQuery("from User where email=:email")
                    .setParameter("email", email)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) transaction.rollback();
            exception.printStackTrace();
        }
        return user;
    }
    @Override
    public void makeUser(User user) {
        user.setRole(user.getRole());
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE User set firstName = :firstName, lastName = :lastName, password = :password, role = :role where email = :username");

            query.setParameter("firstName", user.getFirstName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("password", user.getPassword());
            query.setParameter("username", user.getEmail());
            query.setParameter("role", user.getRole());
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            //if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    @Override
    public void makeAdmin(User user) {
        user.setRole(user.getRole());
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE User set firstName = :firstName, lastName = :lastName, password = :password, role = :role where email = :username");

            query.setParameter("firstName", user.getFirstName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("password", user.getPassword());
            query.setParameter("username", user.getEmail());
            query.setParameter("isAdmin", user.getRole());
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            //if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    @Override
    public boolean updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE User set firstName = :firstName, lastName = :lastName, password = :password, role = :role where email = :username");
            query.setParameter("firstName", user.getFirstName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("password", user.getPassword());
            query.setParameter("username", user.getEmail());
            query.setParameter("isAdmin", user.getRole());
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
            return true;
        } catch (Exception e) {
            //if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public List<User> findAll() {
        Transaction transaction = null;
        List<User> users = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();

            // get all books
            users = session.createQuery("from User ", User.class).list();

            // commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public void block(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE User set firstName = :firstName, lastName = :lastName, password = :password, role = :role, isActive = :isActive where email = :username");
            query.setParameter("firstName", user.getFirstName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("password", user.getPassword());
            query.setParameter("username", user.getEmail());
            query.setParameter("role", user.getRole());
            query.setParameter("isActive", false);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            //if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    @Override
    public void unBlock(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE User set firstName = :firstName, lastName = :lastName, password = :password, role = :role, isActive = :isActive where email = :username");

            query.setParameter("firstName", user.getFirstName());
            query.setParameter("lastName", user.getLastName());
            query.setParameter("password", user.getPassword());
            query.setParameter("username", user.getEmail());
            query.setParameter("role", user.getRole());
            query.setParameter("isActive", true);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result);
            transaction.commit();
        } catch (Exception e) {
            //if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
