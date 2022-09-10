package com.cg.model.dto;


import com.cg.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CategoryDTO {

    private Long id;

    @NotNull(message = "Tên thể loại sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Tên thể loại sản phẩm không được để trống")
    private String code;

    public Category toCategory(){
        return new Category()
                .setId(id)
                .setName(name)
                .setCode(code);

    }
}
