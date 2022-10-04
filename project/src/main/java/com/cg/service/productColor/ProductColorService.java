package com.cg.service.productColor;

import com.cg.model.ProductColor;
import com.cg.model.dto.ProductColorDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ProductColorService extends IGeneralService<ProductColor> {
    List<ProductColorDTO> findAllProductColorDTO();
    Optional<ProductColorDTO> findProductColorDTOById(Long id);

    void deleteColor( Long id);

    Boolean existsByColor(String color);

}
