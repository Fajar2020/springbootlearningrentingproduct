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
@Table(name = "rent_detail_item")
public class RentDetailItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rent_id", nullable = false)
    private RentDetail rent;

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
