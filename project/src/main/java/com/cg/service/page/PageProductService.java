package com.cg.service.page;

import com.cg.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface PageProductService {
    Page<ProductDTO> findAllProductDTONoImage( Pageable pageable);

    Page<ProductDTO> searchProductDTOByTitleAndOtherQuery (String title,  Pageable pageable);

    Page<ProductDTO> searchProductDTOByTitleAndOtherQueryDESC (String title,  Pageable pageable);

    Page<ProductDTO> searchProductDTOByTitleAndOtherQueryTitleASC (String title,  Pageable pageable);

    Page<ProductDTO> searchProductDTOByTitleAndOtherQueryPriceASC (String title,  Pageable pageable);

    Page<ProductDTO> searchProductDTOByTitleAndOtherQueryPriceDESC (String title,  Pageable pageable);

    Page<ProductDTO> findALl(int choice,int option ,String title,  Pageable pageable);
}
