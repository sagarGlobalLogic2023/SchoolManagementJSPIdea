package com.studentManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "user_seq")
    @GenericGenerator(
            name = "user_seq",
            strategy = "com.studentManagement.util.UserIdGenerator"
    )
    private String user_id;
    @Column(name = "first_name", length = 128, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 128, nullable = false)
    private String lastName;
    @Column(name = "email", length = 128, nullable = false, unique = true)
    private String email;
    @Column(name = "password", length = 128, nullable = false)
    private String password;
    @Column(name = "role", length = 128, nullable = false)
    private String role;
    @Column(name = "isActive", nullable = false)
    private boolean isActive;
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
