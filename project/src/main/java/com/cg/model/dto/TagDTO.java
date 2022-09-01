package com.cg.model.dto;

import com.cg.model.Product;
import com.cg.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TagDTO {
    private Long id;
    private String name;
    private boolean deleted;
    private ProductDTO product;

    public TagDTO (Long id, String name,Product product){
        this.id = id;
        this.name = name;
        this.product = product.toProductDTO();
    }

    public Tag toTag(){
        return new Tag()
                .setId(id)
                .setName(name)
                .setDeleted(deleted)
                .setProduct(product.toProduct());
    }
}
