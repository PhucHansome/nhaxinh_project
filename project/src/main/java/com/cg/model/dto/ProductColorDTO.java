package com.cg.model.dto;


import com.cg.model.ProductColor;
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
public class ProductColorDTO {
    private Long id;
    private String color;

    public ProductColor toProductColor(){
        return new ProductColor()
                .setId(id)
                .setColor(color);
    }
}
