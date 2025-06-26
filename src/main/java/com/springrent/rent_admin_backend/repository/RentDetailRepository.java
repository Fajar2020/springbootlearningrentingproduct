package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.RentDetail;
import com.springrent.rent_admin_backend.models.RentDetailBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface RentDetailRepository extends JpaRepository<RentDetail, Long> {


    @Query(value = """
            select r.id, r.is_deleted, r.state, r.total_amount, r.created_at, r.updated_at from rent_detail r
            where r.state = :state and r.customer_id = :customerId and r.created_at >= :startTime and r.created_at <= :endTime and r.is_deleted = false""", nativeQuery = true)
    List<RentDetailBasic> findByStateAndCustomer_IdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndIsDeletedFalse(
            @Param("state") String state,
            @Param("customerId") Long id,
            @Param("startTime") Timestamp createdAt,
            @Param("endTime")Timestamp createdAt1
    );

    @Query(value = """
            select r.id, r.is_deleted, r.state, r.total_amount, r.created_at, r.updated_at from rent_detail r
            where r.state = :state and r.created_at >= :startTime and r.created_at <= :endTime and r.is_deleted = false""", nativeQuery = true)
    List<RentDetailBasic> findByStateAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndIsDeletedFalse(
            @Param("state") String state,
            @Param("startTime") Timestamp createdAt,
            @Param("endTime") Timestamp createdAt1
    );

    Optional<RentDetail> findByIdAndIsDeletedFalse(Long id);
}
