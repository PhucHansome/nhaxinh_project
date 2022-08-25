package com.cg.repository;

import com.cg.model.ProductColor;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.ProductColorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
    @Query("SELECT NEW com.cg.model.dto.ProductColorDTO (" +
            "c.id, " +
            "c.color)  " +
            "FROM ProductColor c ")
    List<ProductColorDTO> findAllProductColorDTO();

    @Query("SELECT NEW com.cg.model.dto.ProductColorDTO (" +
            "c.id, " +
            "c.color)  " +
            "FROM ProductColor c  WHERE c.id = ?1   ")
    Optional<ProductColorDTO> findProductColorDTOById(Long id);
}
