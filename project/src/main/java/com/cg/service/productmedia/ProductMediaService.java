package com.cg.service.productmedia;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.dto.ProductMediaDTO;

import java.util.List;
import java.util.Optional;

public interface ProductMediaService {

    Iterable<ProductMedia> findAll();

    ProductMedia create(ProductMedia productMedia);

    List<ProductMediaDTO> findAllByProductId(String productId);

    Optional<ProductMedia> findTopByProductOrderByTsAsc(Product product);

    void delete(ProductMedia productMedia);

    List<ProductMediaDTO> findAllByProductIdOrderByTsAsc(String id);
}
