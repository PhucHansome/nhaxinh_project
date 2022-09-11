package com.cg.repository.page;

import com.cg.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

public interface ProductPageable extends JpaRepository<Product, Long> {
//    @Query(value="select * from products order by price :option", countName = "select * from products order by price ?3", nativeQuery = true)
//    Page<Product> selectProductPageable(String keyword, BigDecimal price, @Param("option") int option, Pageable pageable);
}
