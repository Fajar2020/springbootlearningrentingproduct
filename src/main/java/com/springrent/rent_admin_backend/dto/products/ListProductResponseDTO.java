package com.springrent.rent_admin_backend.dto.products;

import com.springrent.rent_admin_backend.dto.BasicResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListProductResponseDTO extends BasicResponseDTO<List<ProductResponseDTO>>{
}
