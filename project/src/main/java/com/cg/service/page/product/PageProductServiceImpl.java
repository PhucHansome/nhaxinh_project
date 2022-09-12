package com.cg.service.page.product;

import com.cg.model.dto.ProductDTO;
import com.cg.repository.page.PaginAndSortTingProductRepository;
import com.cg.service.page.product.PageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PageProductServiceImpl implements PageProductService {
    @Autowired
    private PaginAndSortTingProductRepository paginAndSortTingProductRepository;

    @Override
    public Page<ProductDTO> findAllProductDTONoImage(Pageable pageable) {
        return paginAndSortTingProductRepository.findAllProductDTONoImage(pageable);
    }

    public Page<ProductDTO> findALl(int choice, int option, String title, Pageable pageable) {
        if (option == 1) {
            if (choice == 1) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000) , title, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
            }
            return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, pageable);
        }
        if (option == 2) {
            if (choice == 1) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000) , title, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
            }
            return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, pageable);
        }
        if (option == 3) {
            if (choice == 1) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
            }
            return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, pageable);
        }
        if (option == 4) {
            if (choice == 1) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
            }
            return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, pageable);
        }
        return null;
    }
}
