package com.cg.model.dto;


import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartItemsDTO {

    private String id;

    private ProductDTO product;

    private CartDTO cart;

    private BigDecimal price;

    private BigDecimal quantity;

    private boolean deleted;

    private String content;

    public CartItem toCartItem() {
        return new CartItem()
                .setId(Long.valueOf(id))
                .setProduct(product.toProduct())
                .setCart(cart.toCart())
                .setPrice(price)
                .setQuantity(quantity)
                .setContent(content)
                .setDeleted(deleted);
    }
}
