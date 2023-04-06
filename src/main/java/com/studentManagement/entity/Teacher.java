package com.studentManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Teacher")
public class Teacher {
    @Id
    @Column(name = "teacher_id")
    @GeneratedValue(generator = "teacher_seq")
    @GenericGenerator(
            name = "teacher_seq",
            strategy = "com.studentManagement.util.TeacherIdGenerator"
    )
    private String teacher_id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user_id;
    @Column(name = "subject", length = 128, nullable = false)
    private String subject;

}
