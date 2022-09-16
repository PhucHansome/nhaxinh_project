package com.cg.repository.page;

import com.cg.model.Tag;
import com.cg.model.dto.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaginAndSortTingTagRepository extends PagingAndSortingRepository<Tag, Long> {

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE c.deleted = false order by c.product.createdAt DESC")
    Page<TagDTO> findAllProductDTONoImage(Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            ")" +
            "AND c.product.category.name LIKE ?4 " +
            "AND c.product.productColor.color LIKE ?5 " +
            " AND c.product.price BETWEEN ?1 AND ?2 " +
            "")
    Page<TagDTO> searchProductDTOByTitleAndOtherQuery(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color,  Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            ")" +
            "AND c.product.productColor.color LIKE ?5 " +
            " AND c.product.price BETWEEN ?1 AND ?2 " +
            "")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryCategoriesNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color,  Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ")" +
            "AND c.product.category.name LIKE ?4 " +
            "AND c.product.price BETWEEN ?1 AND ?2 " +
            "")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color,  Pageable pageable);



    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            ")" +
            "And c.product.category.name LIKE ?4 " +
            "And c.product.productColor.color LIKE ?5 " +
            " AND c.product.price BETWEEN ?1 AND ?2 " +
            "ORDER BY c.product.price DESC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ")" +
            "And c.product.category.name LIKE ?4 " +
            " AND c.product.price BETWEEN ?1 AND ?2 " +
            "ORDER BY c.product.price DESC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryPriceDESCColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            ") " +
            "and c.product.category.name LIKE ?4 " +
            "and c.product.productColor.color LIKE ?5 " +
            " AND (c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.price ASC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ") " +
            "and c.product.category.name LIKE ?4 " +
            " AND (c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.price ASC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryPriceASCColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            ") " +
            "And c.product.category.name LIKE ?4 " +
            "And c.product.productColor.color LIKE ?5 " +
            " AND (c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.title ASC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryTitleASC(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ") " +
            "And c.product.category.name LIKE ?4 " +
            " AND (c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.title ASC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryTitleASCColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            ") " +
            "and c.product.category.name LIKE ?4 " +
            "and c.product.productColor.color LIKE ?5 " +
            " AND( c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.createdAt DESC " +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryDESC(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ") " +
            "and c.product.category.name LIKE ?4 " +
            " AND( c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.createdAt DESC " +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryDESCColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            ") " +
            "and c.product.productColor.color LIKE ?5 " +
            " AND c.product.price BETWEEN ?1 AND ?2 " +
            "")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryCategoryNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color,  Pageable pageable);


    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.product.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            ")" +
            "And c.product.category.name LIKE ?4 " +
            "And c.product.productColor.color LIKE ?5 " +
            " AND (c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.price DESC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            ") " +
            "and c.product.productColor.color LIKE ?5 " +
            " AND (c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.price ASC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryPriceASCCategoryNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            ") " +
            "and c.product.productColor.color LIKE ?5 " +
            " AND (c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.title ASC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryTitleASCCategoryNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            ") " +
            "and c.product.productColor.color LIKE ?5 " +
            " AND( c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.createdAt DESC " +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryDESCCategoryNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);


    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ") " +
            " AND( c.product.price BETWEEN ?1 AND ?2) " +
            "")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryCategoryNullColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color,  Pageable pageable);


    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ") " +
            " AND( c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.price DESC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryPriceDESCCategoryNullColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ") " +
            " AND( c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.price ASC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryPriceASCCategoryNullColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ") " +
            " AND( c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.title ASC" +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryTitleASCCategoryNullColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

    @Query("SELECT NEW com.cg.model.dto.TagDTO (" +
            "c.id, " +
            "c.name, " +
            "c.product " +
            ")  " +
            "FROM Tag c WHERE " +
            "c.deleted = false " +
            "AND (c.product.title LIKE ?3 " +
            "OR c.name LIKE ?3 " +
            "OR c.product.category.name LIKE ?4 " +
            "OR c.product.productColor.color LIKE ?5 " +
            ") " +
            " AND( c.product.price BETWEEN ?1 AND ?2) " +
            "ORDER BY c.product.createdAt DESC " +
            " ")
    Page<TagDTO> searchProductDTOByTitleAndOtherQueryDESCCategoryNullColorNull(BigDecimal priceA, BigDecimal priceB, String title,String Categories,String Color, Pageable pageable);

}
