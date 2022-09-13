package com.cg.service.page.tag;

import com.cg.model.dto.TagDTO;
import com.cg.repository.page.PaginAndSortTingTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PageTagServiceImpl implements PageTagService {
    @Autowired
    private PaginAndSortTingTagRepository paginAndSortTingTagRepository;

    @Override
    public Page<TagDTO> findAllProductDTONoImage(Pageable pageable) {
        return paginAndSortTingTagRepository.findAllProductDTONoImage(pageable);
    }

    public Page<TagDTO> findALl(int choice, int option, String title, Pageable pageable) {
        if (option == 1) {
            if (choice == 1) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
            }
            return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQuery(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, pageable);
        }
        if (option == 2) {
            if (choice == 1) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
            }
            return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, pageable);
        }
        if (option == 3) {
            if (choice == 1) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
            }
            return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceASC(BigDecimal.valueOf(0), BigDecimal.valueOf(1000000000), title, pageable);
        }
        if (option == 4) {
            if (choice == 1) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(10000000), title, pageable);
            }
            if (choice == 2) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(10000000), BigDecimal.valueOf(25000000), title, pageable);
            }
            if (choice == 3) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(25000000), BigDecimal.valueOf(50000000), title, pageable);
            }
            if (choice == 4) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(50000000), BigDecimal.valueOf(100000000), title, pageable);
            }
            if (choice == 5) {
                return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(100000000), BigDecimal.valueOf(1000000000), title, pageable);
            }
            return paginAndSortTingTagRepository.searchProductDTOByTitleAndOtherQueryPriceDESC(BigDecimal.valueOf(0), BigDecimal.valueOf(100000000), title, pageable);
        }
        return null;
    }
}
