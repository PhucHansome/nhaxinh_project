package com.cg.service.page;

import com.cg.model.dto.ProductDTO;
import com.cg.repository.page.PaginAndSortTingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PageProductServiceImpl implements PageProductService{
    @Autowired
    private PaginAndSortTingProductRepository paginAndSortTingProductRepository;

    @Override
    public Page<ProductDTO> findAllProductDTONoImage(Pageable pageable) {
        return paginAndSortTingProductRepository.findAllProductDTONoImage(pageable);
    }
}
