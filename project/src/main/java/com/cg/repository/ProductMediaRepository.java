package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.dto.ProductMediaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductMediaRepository extends JpaRepository<ProductMedia, String> {

    Optional<ProductMedia> findByProduct(Product product);

    List<ProductMedia> findProductMediaByProduct(Product product);
    @Query("SELECT NEW com.cg.model.dto.ProductMediaDTO (" +
            "pm.id," +
            "pm.fileUrl) " +
            "FROM ProductMedia pm " +
            "WHERE pm.product.id = ?1"
    )
    List<ProductMediaDTO> findAllByProductId(String id);


    Optional<ProductMedia> findTopByProductOrderByTsAsc(Product product);


    @Query("SELECT NEW com.cg.model.dto.ProductMediaDTO (" +
            "pm.id," +
            "pm.ts," +
            "pm.fileUrl) " +
            "FROM ProductMedia pm " +
            "WHERE pm.product.id = ?1" +
            " order by pm.ts desc "
    )
    List<ProductMediaDTO> findAllByProductIdOrderByTsAsc(String id);

    @Query("SELECT NEW com.cg.model.dto.ProductMediaDTO (" +
            "pm.id," +
            "pm.fileName, " +
            "pm.fileFolder, " +
            "pm.fileUrl, " +
            "pm.fileType, " +
            "pm.cloundId, " +
            "pm.ts," +
            "pm.product) " +
            "FROM ProductMedia pm " +
            "WHERE pm.product.id = ?1"
    )
    List<ProductMedia> findByProductDTO (String id);
}
