package com.cg.model;


import com.cg.model.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "tags")
public class Tag extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String Slug;

    @ManyToMany
    @JoinColumn(name = "product_id")
    private List<Product> product;

    public TagDTO toTagDTO() {
        return new TagDTO()
                .setId(id)
                .setName(name)
                .setProduct(product);
    }
}
