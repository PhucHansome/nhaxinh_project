package com.cg.service.category;

import com.cg.model.Category;
import com.cg.model.CustomerInfo;
import com.cg.model.Product;
import com.cg.model.dto.CategoryDTO;
import com.cg.service.IGeneralService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CategoryService extends IGeneralService<Category>{
    List<CategoryDTO> findAllCategoryDTO();
    Optional<CategoryDTO> findCategoryDTOById(Long id);

    void deleteCategory( Long id);




}
