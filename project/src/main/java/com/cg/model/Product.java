package com.cg.model;

import com.cg.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "products")
public class Product extends  BaseEntity{

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String code;

    private String title;

    private BigDecimal price;

    private BigDecimal quantity;

    private String status;

    private String description;

    private String slug;

    private String size;

    private String material;

    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToOne
    @JoinColumn(name = "productColor_id")
    private ProductColor productColor;


    @Column(columnDefinition = "BIGINT(20) DEFAULT 0")
    private Long ts = new Date().getTime();

    @OneToMany(mappedBy = "product")
    private List<ProductMedia> productMedia;

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", ts=" + ts +
                ", productMedia=" + productMedia +
                '}';
    }

    public ProductDTO toProductDTO(){
        return new ProductDTO()
                .setId(id)
                .setCode(code)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setStatus(status)
                .setDescription(description)
                .setSlug(slug)
                .setSize(size)
                .setMaterial(material)
                .setImage(image)
                .setProductColor(productColor.toProductColorDTO())
                .setCategory(category.categoryDTO())
                ;
    }
}
