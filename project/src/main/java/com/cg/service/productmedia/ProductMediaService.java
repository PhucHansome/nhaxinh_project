package com.cg.service.productmedia;

import com.cg.model.ProductMedia;

public interface ProductMediaService {

    Iterable<ProductMedia> findAll();

    ProductMedia create(ProductMedia productMedia);

    void delete(ProductMedia productMedia);
}
