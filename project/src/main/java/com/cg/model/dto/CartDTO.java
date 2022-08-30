package com.cg.model.dto;


import com.cg.model.*;
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

    private UserDTO user;

    private CustomerInfoDTO customerInfo;


    public Cart toCart() {
        return new Cart()
                .setId(id)
                .setUser(user.toUser())
                .setCustomerInfo(customerInfo.toCustomerInfo())
               ;
    }
}
