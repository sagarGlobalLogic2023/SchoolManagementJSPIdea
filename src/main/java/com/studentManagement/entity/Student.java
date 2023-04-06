package com.studentManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Student")
public class Student {
    @Id
    @Column(name = "roll_number")
    @GeneratedValue(generator = "student_seq") // custom Roll Number generator is used
    @GenericGenerator(
            name = "student_seq",
            strategy = "com.studentManagement.util.RollNumberGenerator"
    )
    private String roll_number;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user_id;
    @Column(name = "standard", length = 128, nullable = true)
    private String standard;
    @Column(name = "score")
    private double score;
    @Column(name = "isPresent", nullable = false)
    private boolean isPresent;
    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return roll_number.equals(student.roll_number);
    }
    @Override
    public int hashCode() {
        return Objects.hash(roll_number);
    }
}
