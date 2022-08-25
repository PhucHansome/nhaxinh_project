package com.cg.service.productColor;

import com.cg.model.dto.ProductColorDTO;
import com.cg.repository.ProductColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductColorServiceImpl implements ProductColorService{
    @Autowired
    private ProductColorRepository productColorRepository;

    @Override
    public List<ProductColorService> findAll() {
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Optional<ProductColorService> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductColorService getById(Long id) {
        return null;
    }

    @Override
    public ProductColorService save(ProductColorService productColorService) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(ProductColorService productColorService) {

    }

    @Override
    public List<ProductColorDTO> findAllProductColorDTO() {
        return productColorRepository.findAllProductColorDTO();
    }

    @Override
    public Optional<ProductColorDTO> findProductColorDTOById(Long id) {
        return productColorRepository.findProductColorDTOById(id);
    }
}
