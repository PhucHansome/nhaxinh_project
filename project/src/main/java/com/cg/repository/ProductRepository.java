package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.IProductDTO;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


    @Query("SELECT " +
            "pm.product.id AS id, " +
            "pm.product.code AS code, " +
            "pm.product.title As title, " +
            "pm.product.price As price, " +
            "pm.product.quantity As quantity, " +
            "pm.product.size As size, " +
            "pm.product.material As material, " +
            "pm.product.description As description , " +
            "pm.product.status As status , " +
            "pm.product.category.name As category,  " +
            "pm.product.productColor.color As color, " +
            "pm.product.createdAt as createdAt, " +
            "pm.id AS fileId, " +
            "pm.fileName AS fileName, " +
            "pm.fileFolder AS fileFolder, " +
            "pm.fileUrl AS fileUrl, " +
            "pm.fileType AS fileType " +
            "FROM ProductMedia pm " +
            "ORDER BY pm.product.ts ASC"
    )
    Iterable<IProductDTO> findAllIProductDTO();


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
            "FROM Product c WHERE c.deleted = false order by c.ts asc ")
    List<ProductDTO> findAllProductDTONoImage();

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
            "c.id = ?1 " +
            "And c.deleted = false")
    Optional<ProductDTO> findProductDTOById (String id);

    @Query("SELECT " +
            "pm.product.id AS id, " +
            "pm.product.code AS code, " +
            "pm.product.title As title, " +
            "pm.product.price As price, " +
            "pm.product.quantity As quantity, " +
            "pm.product.size As size, " +
            "pm.product.material As material, " +
            "pm.product.description As description , " +
            "pm.product.status As status , " +
            "pm.product.category.name As category,  " +
            "pm.product.productColor.color As color, " +
            "pm.product.createdAt as createdAt, " +
            "pm.id AS fileId, " +
            "pm.fileName AS fileName, " +
            "pm.fileFolder AS fileFolder, " +
            "pm.fileUrl AS fileUrl, " +
            "pm.fileType AS fileType " +
            "FROM ProductMedia pm " +
            "WHERE pm.product.id = :id"
    )
    IProductDTO findIProductDTOById(@Param("id") String id);

}
