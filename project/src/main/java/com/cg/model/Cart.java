package com.cg.model;


import com.cg.model.dto.CartDTO;
import com.cg.model.dto.CartItemsDTO;
import com.cg.model.dto.CustomerInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
@Accessors(chain = true)
public class Cart  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fullName;

    private String status;

    private String phone;

    private String content;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToOne
    @JoinColumn(name = "customerinf_id")
    private CustomerInfo customerInfo;

    @OneToMany
    @JoinColumn(name = "cart_item_id")
    private List<CartItem> cartItems;

    public CartDTO toCartDTO(){
        return new CartDTO()
                .setId(String.valueOf(id))
                .setFullName(fullName)
                .setStatus(status)
                .setPhone(phone)
                .setContent(content)
                .setCustomerInfo(customerInfo.toCustomerInfoDTO())
                .setDeleted(deleted)
                .setCartItems((CartItemsDTO) cartItems);
    }
}
