package com.cg.controller.api;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import com.cg.service.category.CategoryService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

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
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Optional<CategoryDTO> categoryDTO = categoryService.findCategoryDTOById(id);
        if (!categoryDTO.isPresent()) {
            throw new ResourceNotFoundException("Invalid category ID");
        }
        return new ResponseEntity<>(categoryDTO.get().toCategory(), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Category category = categoryService.save(categoryDTO.toCategory());
        return new ResponseEntity<>(category.categoryDTO(), HttpStatus.OK);
    }
    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<?> deleteCategory( @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
