package com.cg.model;

import com.cg.model.dto.ProductSizeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "ProductSizes")
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String size;

    public ProductSizeDTO toProductSizeDTO(){
        return new ProductSizeDTO()
                .setId(id)
                .setSize(size);
    }
}
