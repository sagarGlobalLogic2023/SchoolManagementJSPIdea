package com.studentManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Course")
public class Course {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(generator = "course_seq")
    @GenericGenerator(
            name = "course_seq",
            strategy = "com.studentManagement.util.CourseIdGenerator"
    )
    private String course_id;
    @Column(name = "subject", length = 128, nullable = false)
    private String subject;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_course")
    private List<Student> students = new ArrayList<>();

    @Override
    public String toString() {
        return "Course{" +
                "course_id='" + course_id + '\'' +
                ", subject='" + subject + '\'' +
                ", students=" + students +
                ", teachers=" + teachers +
                '}';
    }

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id")
    private List<Teacher> teachers;
}
