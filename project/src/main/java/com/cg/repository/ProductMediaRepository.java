package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, String> {

    Optional<ProductMedia> findByProduct(Product product);
}
