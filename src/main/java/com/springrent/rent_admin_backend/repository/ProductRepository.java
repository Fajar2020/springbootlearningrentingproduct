package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameIgnoreCaseAndIsDeletedFalse(String name);

    Optional<Product> findByIdAndIsDeletedFalse(Long id);

    List<Product> findByNameAndIsDeletedFalse(String name);

    List<Product> findByIsDeletedFalse();

}
