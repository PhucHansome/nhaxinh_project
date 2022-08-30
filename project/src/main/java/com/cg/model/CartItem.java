package com.cg.model;

import com.cg.model.dto.CartItemsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartItems")
@Accessors(chain = true)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal price;

    private BigDecimal quantity;

    private BigDecimal grandTotal;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;


    public CartItemsDTO toCartItemDTO() {
        return new CartItemsDTO()
                .setId(id)
                .setProduct(product.toProductDTO())
                .setPrice(price)
                .setQuantity(quantity)
                .setGrandTotal(grandTotal)
                .setUserName(userName);
    }
}
