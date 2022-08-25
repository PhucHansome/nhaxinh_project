package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.dto.ProductMediaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, String> {

    Optional<ProductMedia> findByProduct(Product product);

    @Query("SELECT NEW com.cg.model.dto.ProductMediaDTO (" +
            "pm.id," +
            "pm.fileUrl) " +
            "FROM ProductMedia pm " +
            "WHERE pm.product.id = ?1"
    )
    List<ProductMediaDTO> findAllByProductId(String id);

}
