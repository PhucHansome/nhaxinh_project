package com.cg.controller.api;


import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Category;
import com.cg.model.ProductColor;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.ProductColorDTO;
import com.cg.service.productColor.ProductColorService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/colorProduct")
public class ProductColorAPI {

    @Autowired
    private ProductColorService productColorService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    private ResponseEntity<?> findAll() {
        try {
            List<ProductColorDTO> productColorDTOS = productColorService.findAllProductColorDTO();

            return new ResponseEntity<>(productColorDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getColorProductById(@PathVariable Long id) {
        Optional<ProductColorDTO> productColorDTO = productColorService.findProductColorDTOById(id);
        if (!productColorDTO.isPresent()) {
            throw new ResourceNotFoundException("Invalid color ID");
        }
        return new ResponseEntity<>(productColorDTO.get().toProductColor(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Valid  @RequestBody ProductColorDTO productColorDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            ProductColor productColor = productColorService.save(productColorDTO.toProductColor());
            return new ResponseEntity<>(productColor.toProductColorDTO(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }


    }
    @DeleteMapping("/delete-color/{id}")
    public ResponseEntity<?> deleteColor( @PathVariable Long id) {
        productColorService.deleteColor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
