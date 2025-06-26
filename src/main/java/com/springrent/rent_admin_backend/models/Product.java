package com.springrent.rent_admin_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, length = 255)
    private String name;

    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Column(name = "rent_price")
    private Double rentPrice;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_id", nullable = false)
    private Users createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by_id", nullable = false)
    private Users updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
