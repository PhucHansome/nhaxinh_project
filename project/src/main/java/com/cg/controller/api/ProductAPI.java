package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.Product;
import com.cg.model.ProductMedia;
import com.cg.model.dto.*;
import com.cg.service.category.CategoryService;
import com.cg.service.productColor.ProductColorService;
import com.cg.service.productmedia.ProductMediaService;
import com.cg.service.productservice.ProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {
    @Autowired
    private ProductColorService productColorService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMediaService productMediaService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    private ResponseEntity<?> findAll() {
        try {
            List<ProductDTO> productDTOS = productService.findAllProductDTONoImage();

            return new ResponseEntity<>(productDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id) {
        IProductDTO productDTOOptional = productService.findIProductDTOById(id);
        if (productDTOOptional == null) {
            throw new DataInputException("Product is not found");
        }

        return new ResponseEntity<>(productDTOOptional, HttpStatus.OK);
    }

    @GetMapping("/product-color")
    private ResponseEntity<?> findAllProductColor() {
        try {
            List<ProductColorDTO> productColorDTOS = productColorService.findAllProductColorDTO();

            return new ResponseEntity<>(productColorDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product-image/{product_mediaID}")
    private ResponseEntity<?> getProductImage(@PathVariable String product_mediaID){
        List<ProductMediaDTO> productMedia = productMediaService.findAllByProductId(product_mediaID);
        return new ResponseEntity<>(productMedia, HttpStatus.OK);
    }

    @PostMapping("/{idCategory}/{idProductColor}")
    public ResponseEntity<?> create(ProductDTO productDTO, @PathVariable Long idCategory, @PathVariable Long idProductColor, BindingResult bindingResult) {
        Optional<CategoryDTO> optionalCategoryDTO = categoryService.findCategoryDTOById(idCategory);
        productDTO.setCategory(optionalCategoryDTO.get());

        Optional<ProductColorDTO> productColorDTO = productColorService.findProductColorDTOById(idProductColor);
        productDTO.setProductColor(productColorDTO.get());

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        try {
            Product createdProduct = productService.create(productDTO);

            Optional<ProductMedia> productMediaOptional = productMediaService.findTopByProductOrderByTsAsc(createdProduct);

            if (!productMediaOptional.isPresent()) {
                throw new DataInputException("Product creation information is not valid, please check the information again");
            }

            createdProduct.setImage(productMediaOptional.get().getFileUrl());
            productService.save(createdProduct);

            return new ResponseEntity<>(createdProduct.toProductDTO(), HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Product creation information is not valid, please check the information again");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws IOException {

        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            productService.delete(product.get());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new DataInputException("Invalid product information");
        }
    }
}
