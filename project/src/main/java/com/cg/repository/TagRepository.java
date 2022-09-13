package com.cg.repository;

import com.cg.model.Category;
import com.cg.model.Tag;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "t.id, " +
            "t.name, " +
            "t.product" +
            ")  " +
            "FROM Tag t  WHERE t.product.id = ?1  And t.deleted = false ")
    Optional<TagDTO> findTagDTOByProductId(String id);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?1 " +
            "OR c.product.material LIKE ?1 " +
            "OR c.product.category.name LIKE ?1 " +
            "OR c.name LIKE ?1 " +
            "OR c.product.productColor.color LIKE ?1)"
    )
    List<TagDTO> searchProductDTOByTitle(String title);
}
