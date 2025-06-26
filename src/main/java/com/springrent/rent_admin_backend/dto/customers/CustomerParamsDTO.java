package com.springrent.rent_admin_backend.dto.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerParamsDTO {

    private String displayName;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date endDate;
}
