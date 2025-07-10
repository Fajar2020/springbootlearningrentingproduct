package com.springrent.rent_admin_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "address_line1", length = 512, nullable = false)
    private String addressLine1;

    @Column(name = "address_line2", length = 512)
    private String addressLine2;

    @Column(name = "city", length = 256)
    private String city;

    @Column(name = "cuntry", length = 256)
    private String country;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_id", nullable = false)
    private Users createdBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "updated_by_id", nullable = false)
    private Users updatedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
