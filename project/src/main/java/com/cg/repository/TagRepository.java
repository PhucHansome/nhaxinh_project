package com.cg.repository;

import com.cg.model.Category;
import com.cg.model.Tag;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

//    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
//            "t.id, " +
//            "t.name" +
//            "t.product" +
//            ")  " +
//            "FROM Tag t  WHERE t.product.id = ?1  And t.deleted = false ")
    Optional<TagDTO> findTagDTOByProductId(String id);
}
