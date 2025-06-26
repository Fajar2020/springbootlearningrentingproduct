package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsernameIgnoreCaseAndIsDeletedFalse(String username);

    Optional<Users> findByEmailIgnoreCaseAndIsDeletedFalse(String email);
}
