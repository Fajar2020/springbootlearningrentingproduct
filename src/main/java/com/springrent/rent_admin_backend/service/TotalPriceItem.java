package com.springrent.rent_admin_backend.service;

import com.springrent.rent_admin_backend.models.RentDetailItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TotalPriceItem {
    Double totalPriceRent;
    List<RentDetailItem> rentDetailItems;

}
