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
    private List<Product> product;

    public Tag toTag(){
        return new Tag()
                .setId(id)
                .setName(name)
                .setProduct(product)
                .setDeleted(deleted);
    }
}
