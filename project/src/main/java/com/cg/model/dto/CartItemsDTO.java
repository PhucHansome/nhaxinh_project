package com.cg.model.dto;


import com.cg.model.Cart;
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

    private Long id;

    private String userName;

    private ProductDTO product;

    private BigDecimal price;

    private BigDecimal quantity;

    private BigDecimal grandTotal;

    private boolean deleted;

    public CartItemsDTO(Long id, String userName, Product product, BigDecimal price, BigDecimal quantity, BigDecimal grandTotal) {
        this.id = id;
        this.userName = userName;
        this.product = product.toProductDTO();
        this.price = price;
        this.quantity = quantity;
        this.grandTotal = grandTotal;
    }

    public CartItem toCartItem() {
        return new CartItem()
                .setId(id)
                .setProduct(product.toProduct())
                .setPrice(price)
                .setQuantity(quantity)
                .setGrandTotal(grandTotal)
                .setDeleted(deleted)
                .setUserName(userName)
                ;
    }
}
