package com.cg.service.productmedia;

import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.dto.ProductMediaDTO;
import com.cg.repository.ProductMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class ProductMediaServiceImpl implements ProductMediaService{
    @Autowired
    private ProductMediaRepository productMediaRepository;

    @Override
    public Iterable<ProductMedia> findAll() {
        return productMediaRepository.findAll();
    }

    @Override
    public ProductMedia create(ProductMedia productMedia) {
        return productMediaRepository.save(productMedia);
    }

    @Override
    public List<ProductMediaDTO> findAllByProductId(String productId) {
        return productMediaRepository.findAllByProductId(productId);
    }

    @Override
    public Optional<ProductMedia> findTopByProductOrderByTsAsc(Product product) {
        return productMediaRepository.findTopByProductOrderByTsAsc(product);
    }

    @Override
    public void delete(ProductMedia productMedia) {
        productMediaRepository.delete(productMedia);
    }

    @Override
    public List<ProductMediaDTO> findAllByProductIdOrderByTsDesc(String id) {
        return productMediaRepository.findAllByProductIdOrderByTsDesc(id);
    }

    @Override
    public List<ProductMediaDTO> findAllByProductIdOrderByTsAsc(String id) {
        return productMediaRepository.findAllByProductIdOrderByTsAsc(id);
    }
}
