package com.cg.service.productColor;

import com.cg.model.dto.ProductColorDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ProductColorService extends IGeneralService<ProductColorService> {
    List<ProductColorDTO> findAllProductColorDTO();
    Optional<ProductColorDTO> findProductColorDTOById(Long id);
}
