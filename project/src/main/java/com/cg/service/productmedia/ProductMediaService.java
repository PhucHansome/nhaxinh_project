package com.cg.service.productmedia;

import com.cg.model.ProductMedia;
import com.cg.model.dto.ProductMediaDTO;

import java.util.List;

public interface ProductMediaService {

    Iterable<ProductMedia> findAll();

    ProductMedia create(ProductMedia productMedia);

    List<ProductMediaDTO> findAllByProductId(String productId);

    void delete(ProductMedia productMedia);
}
