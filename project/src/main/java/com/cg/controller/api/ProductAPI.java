package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.Product;
import com.cg.model.dto.IProductDTO;
import com.cg.model.dto.ProductDTO;
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
    private ProductService productService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<Iterable<?>> findAll(){
        try{
            Iterable<IProductDTO> iProductDTOS = productService.findAllIProductDTO();

            if(((List) iProductDTOS).isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(iProductDTOS, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id){
        IProductDTO productDTOOptional = productService.findIProductDTOById(id);
        if (productDTOOptional == null) {
            throw new DataInputException("Product is not found");
        }

        return new ResponseEntity<>(productDTOOptional, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(ProductDTO productDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        try {
            Product createdProduct = productService.create(productDTO);

            IProductDTO iProductDTO =  productService.findIProductDTOById(createdProduct.getId());

            return new ResponseEntity<>(iProductDTO, HttpStatus.CREATED);

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
