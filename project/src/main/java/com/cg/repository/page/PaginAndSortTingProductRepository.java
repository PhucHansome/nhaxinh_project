package com.cg.repository.page;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaginAndSortTingProductRepository extends PagingAndSortingRepository<Product,Long> {

    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
            "c.id, " +
            "c.code , " +
            "c.title, " +
            "c.price, " +
            "c.quantity, " +
            "c.status, " +
            "c.description, " +
            "c.size, " +
            "c.material, " +
            "c.slug, " +
            "c.image, " +
            "c.category, " +
            "c.productColor, " +
            "c.createdAt" +
            ")  " +
            "FROM Product c WHERE c.deleted = false order by c.createdAt ASC")
    Page<ProductDTO> findAllProductDTONoImage( Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
            "c.id, " +
            "c.code , " +
            "c.title, " +
            "c.price, " +
            "c.quantity, " +
            "c.status, " +
            "c.description, " +
            "c.size, " +
            "c.material, " +
            "c.slug, " +
            "c.image, " +
            "c.category, " +
            "c.productColor, " +
            "c.createdAt" +
            ")  " +
            "FROM Product c WHERE " +
            "c.deleted = false " +
            "AND (c.title LIKE ?1 " +
            "OR c.material LIKE ?1 " +
            "OR c.category.name LIKE ?1 " +
            "OR c.productColor.color LIKE ?1) " +
            " ")
    Page<ProductDTO> searchProductDTOByTitleAndOtherQuery (String title,  Pageable pageable);


}
