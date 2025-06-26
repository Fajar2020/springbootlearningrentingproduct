package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmailIgnoreCaseAndIsDeletedFalse(String email);

    Optional<Customer> findByDisplayNameIgnoreCaseAndIsDeletedFalse(String displayName);

    Optional<Customer> findByIdAndIsDeletedFalse(Long id);

    @Query("select c from Customer c where upper(c.displayName) like CONCAT('%', UPPER(?1), '%') and c.isDeleted = false")
    List<Customer> findByDisplayNameLikeIgnoreCaseAndIsDeletedFalse(String displayName);

    List<Customer> findByIsDeletedFalse();
}
