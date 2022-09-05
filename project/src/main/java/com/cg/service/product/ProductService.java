package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.IProductDTO;
import com.cg.model.dto.ProductDTO;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Iterable<Product> findAll();

    Optional<Product> findById(String id);

    Iterable<IProductDTO> findAllIProductDTO();

    IProductDTO findIProductDTOById(String id);

    Product create(ProductDTO productDTO);

    Product updateProduct(ProductDTO productDTO);

    void delete(Product product) throws IOException;

    List<ProductDTO> findAllProductDTONoImage();

    Product save(Product Product);

    Optional<ProductDTO> findProductDTOById (String id);

    Optional<ProductDTO> findProductDTOByCode (String code);

    List<ProductDTO> searchProductDTOByTitle (String title);

    Product deleteSoft(Product product);

    Page<ProductDTO> findAllByPrice(SpringDataWebProperties.Pageable pageable);

}
