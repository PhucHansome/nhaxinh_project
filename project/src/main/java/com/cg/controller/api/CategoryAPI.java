package com.cg.controller.api;

import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import com.cg.service.category.CategoryService;
import com.cg.service.productservice.ProductService;
import com.cg.utils.AppUtils;
import org.hibernate.loader.plan.spi.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryAPI {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    private ResponseEntity<?> findAll() {
        try {
            List<CategoryDTO> categoryList = categoryService.findAllCategoryDTO();

            return new ResponseEntity<>(categoryList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
