package com.springrent.rent_admin_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name", nullable = false, length = 255)
    private String username;

    @Column(name="email", nullable = false, length = 255)
    private String email;

    @Column(name="employee_number", nullable = false, length = 64, unique = true)
    private String employeeNumber;

    @Column(name="password", length = 1047)
    private String password;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
