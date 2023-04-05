package com.studentManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@Table(name = "session_history")
public class SessionHistory {
    @Id
    @Column(name = "session_id")
    @GeneratedValue(generator = "session_seq")
    @GenericGenerator(
            name = "session_seq",
            strategy = "com.studentManagement.util.SessionIdGenerator"
    )
    private String id;
    @Column(name = "start_date", length = 128, nullable = false)
    private String start_date;
    @Column(name = "end_date", length = 128)
    private String end_date;
    @Column(name = "seconds", nullable = false)
    private int seconds;
    @Column(name = "due")
    private int due;
    @Column(name = "user_email", nullable = false)
    private String email;
    public SessionHistory(String start_date, String end_date, int seconds, String email) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.seconds = seconds;
        this.email = email;
    }
    @Override
    public String toString() {
        return "SessionHistory{" +
                "id=" + id +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", hours=" + seconds +
                '}';
    }

}
