package com.cg.model.dto;


import com.cg.model.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductSizeDTO {
    private Long id;
    private String size;

    public ProductSize toProductSize(){
        return new ProductSize()
                .setId(id)
                .setSize(size);
    }
}
