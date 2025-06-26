package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.RentDetailItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentDetailItemRepository extends JpaRepository<RentDetailItem, Long> {
}
