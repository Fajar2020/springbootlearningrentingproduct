package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository <Address, Long>{


    Optional<Address> findByIdAndIsDeletedFalse(Long id);
}
