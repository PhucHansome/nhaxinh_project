package com.cg.repository;

import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
    @Query("SELECT NEW com.cg.model.dto.CategoryDTO (" +
            "c.id, " +
            "c.name, " +
            "c.code)  " +
            "FROM Category c  WHERE c.deleted = false ")
    List<CategoryDTO> findAllCategoryDTO();

    @Query("SELECT NEW com.cg.model.dto.CategoryDTO (" +
            "c.id, " +
            "c.name, " +
            "c.code)  " +
            "FROM Category c  WHERE c.id = ?1   ")


    Optional<CategoryDTO> findCategoryDTOById(Long id);
}
