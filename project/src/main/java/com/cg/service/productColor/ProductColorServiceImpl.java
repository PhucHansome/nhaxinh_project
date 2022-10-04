package com.cg.service.productColor;

import com.cg.model.ProductColor;
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
    public List<ProductColor> findAll() {
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Optional<ProductColor> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductColor getById(Long id) {
        return null;
    }

    @Override
    public ProductColor save(ProductColor productColor) {
        return productColorRepository.save(productColor);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(ProductColor ProductColor) {

    }

    @Override
    public List<ProductColorDTO> findAllProductColorDTO() {
        return productColorRepository.findAllProductColorDTO();
    }

    @Override
    public Optional<ProductColorDTO> findProductColorDTOById(Long id) {
        return productColorRepository.findProductColorDTOById(id);
    }

    @Override
    public void deleteColor(Long id) {
        productColorRepository.deleteById(id);
    }

    @Override
    public Boolean existsByColor(String color) {
        return productColorRepository.existsByColor(color);
    }
}
