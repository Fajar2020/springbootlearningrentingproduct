package com.springrent.rent_admin_backend.models;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @OneToMany(mappedBy = "customer", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RentDetail> rentDetails = new ArrayList<>();

    @OneToMany(mappedBy = "customer", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_id", nullable = false)
    private Users createdBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "updated_by_id", nullable = false)
    private Users updatedBy;
}
