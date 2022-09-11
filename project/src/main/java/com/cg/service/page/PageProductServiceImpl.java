package com.cg.service.page;

import com.cg.model.dto.ProductDTO;
import com.cg.repository.page.PaginAndSortTingProductRepository;
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

    @Override
    public Page<ProductDTO> searchProductDTOByTitleAndOtherQuery(String title, Pageable pageable) {
        return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQuery(title, pageable);
    }

    @Override
    public Page<ProductDTO> searchProductDTOByTitleAndOtherQueryDESC(String title, Pageable pageable) {
        return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryDESC(title, pageable);
    }

    @Override
    public Page<ProductDTO> searchProductDTOByTitleAndOtherQueryTitleASC(String title, Pageable pageable) {
        return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryTitleASC(title, pageable);
    }

    @Override
    public Page<ProductDTO> searchProductDTOByTitleAndOtherQueryPriceASC(String title, Pageable pageable) {
        return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceASC(title, pageable);
    }

    @Override
    public Page<ProductDTO> searchProductDTOByTitleAndOtherQueryPriceDESC(String title, Pageable pageable) {
        return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(title, pageable);
    }

    @Override
    public Page<ProductDTO> findALl(int option, String title, Pageable pageable) {
        if (option == 1) {
            return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQuery(title, pageable);
        }
        if (option == 2) {
            return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryDESC(title, pageable);
        }
        if (option == 3) {
            return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceASC(title, pageable);
        }
        if (option == 4) {
            return paginAndSortTingProductRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(title, pageable);
        }

        return null;
    }
}
