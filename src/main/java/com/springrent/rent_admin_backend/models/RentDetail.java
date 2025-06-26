package com.springrent.rent_admin_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springrent.rent_admin_backend.models.enums.RentDetailState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rent_detail")
public class RentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RentDetailState state;

    @Column(name="total_amount")
    private Double totalAmount;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "rent", orphanRemoval = true)
    private List<RentDetailItem> rentDetailItems = new ArrayList<>();


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
