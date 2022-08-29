package com.cg.model.dto;


import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.LocationRegion;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartDTO {

    private String id;

    private User user;

    private String fullName;

    private String status;

    private String phone;

    private String content;

    private boolean deleted;

    private CustomerInfoDTO customerInfo;

    private CartItemsDTO cartItems;

    public Cart toCart() {
        return new Cart()
                .setId(Long.valueOf(id))
                .setFullName(fullName)
                .setStatus(status)
                .setPhone(phone)
                .setContent(content)
                .setCustomerInfo(customerInfo.toCustomerInfo())
                .setDeleted(deleted)
                .setCartItems((List<CartItem>) cartItems)
               ;
    }
}
