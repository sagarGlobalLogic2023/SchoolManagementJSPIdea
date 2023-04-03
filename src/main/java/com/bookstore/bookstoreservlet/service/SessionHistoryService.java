package com.bookstore.bookstoreservlet.service;

import com.bookstore.bookstoreservlet.entity.SessionHistory;
import com.bookstore.bookstoreservlet.repository.SessionHistoryDaoImpl;

import java.util.List;

public class SessionHistoryService {
    SessionHistoryDaoImpl sessionHistoryDao = new SessionHistoryDaoImpl();
    public void addSession(SessionHistory sessionHistory) {
        sessionHistoryDao.addSession(sessionHistory);
    }

    public void updateSession(SessionHistory sessionHistory) {
        sessionHistoryDao.updateSession(sessionHistory);
    }

    public List<SessionHistory> getAllSessions(String email) {
        return sessionHistoryDao.getAllSessions(email);
    }

    public int getTotalDue(String email) {
        return sessionHistoryDao.getTotalDue(email);
    }
}
