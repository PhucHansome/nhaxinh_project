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
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable = false)
    private Cart cart;

    private BigDecimal price;

    private BigDecimal quantity;

    private String content;

    public CartItemsDTO toCartItemDTO() {
        return new CartItemsDTO()
                .setId(id)
                .setProduct(product.toProductDTO())
                .setCart(cart.toCartDTO())
                .setPrice(price)
                .setQuantity(quantity)
                .setContent(content);
    }
}
