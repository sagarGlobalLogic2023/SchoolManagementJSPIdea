package com.bookstore.bookstoreservlet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", length = 128, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 128, nullable = false)
    private String lastName;
    @Column(name = "email", length = 128, nullable = false, unique = true)
    private String email;
    @Column(name = "password", length = 128, nullable = false)
    private String password;
    @Column(name = "favourites", length = 128)
    private String favourites;
    @Column(name = "completed", length = 128)
    private String completed;
    @Column(name = "isAdmin", length = 128, nullable = false)
    private boolean isAdmin;
    @Column(name = "isBlocked", length = 128, nullable = false)
    private boolean isBlocked;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
