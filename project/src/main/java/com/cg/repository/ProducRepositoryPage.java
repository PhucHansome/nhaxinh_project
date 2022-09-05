package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducRepositoryPage extends PagingAndSortingRepository<Product, Integer> {

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
            "FROM Product c WHERE c.deleted = false" )
    List<ProductDTO> findAllByPrice(SpringDataWebProperties.Pageable pageable);
}
