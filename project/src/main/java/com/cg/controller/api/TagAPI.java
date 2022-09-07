package com.cg.controller.api;


import com.cg.model.Tag;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.TagDTO;
import com.cg.service.Tag.TagService;
import com.cg.service.product.ProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/tag")
public class TagAPI {
    @Autowired
    private TagService tagService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("/{id}")
    private ResponseEntity<?> getTag(@PathVariable String id){
        Optional<TagDTO> tagDTO = tagService.findTagDTOByProductId(id);
        return  new ResponseEntity<>(tagDTO.get().toTag(), HttpStatus.OK);
    }

    @PostMapping("/id/{productId}")
    private ResponseEntity<?> createTag(@RequestBody TagDTO tagDTO, @PathVariable String productId, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        Optional<ProductDTO> productDTO = productService.findProductDTOById(productId);
        tagDTO.setProduct(productDTO.get());
        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);
        Tag tagDTO1 = tagService.save(tagDTO.toTag());
        return  new ResponseEntity<>(tagDTO1.toTagDTO(), HttpStatus.CREATED);
    }

    @PutMapping("/id/{productId}")
    private ResponseEntity<?> updateTag(@RequestBody TagDTO tagDTO, @PathVariable String productId, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        Optional<ProductDTO> productDTO = productService.findProductDTOById(productId);
        tagDTO.setProduct(productDTO.get());
        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Tag tagDTO1 = tagService.save(tagDTO.toTag());
        return  new ResponseEntity<>(tagDTO1.toTagDTO(), HttpStatus.CREATED);
    }

}
