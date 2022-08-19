package com.cg.model;

import com.cg.model.dto.ProductColorDTO;
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
@Table(name = "ProductColor")
public class ProductColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String color;

    public ProductColorDTO toProductColorDTO(){
        return new ProductColorDTO()
                .setId(id)
                .setColor(color);
    }
}
