package com.studentManagement.repository;

import com.studentManagement.entity.SessionHistory;

import java.util.List;

public interface SessionHistoryDao {
    void addSession(SessionHistory sessionHistory);

    void updateSession(SessionHistory sessionHistory);

    List<SessionHistory> getAllSessions(String email);

    int getTotalDue(String email);
}
