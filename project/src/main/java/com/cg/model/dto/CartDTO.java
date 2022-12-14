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

    private Long id;

    private String content;

    private UserDTO user;

    private CustomerInfoDTO customerInfo;

    public CartDTO(Long id, String content,User user, CustomerInfo customerInfo){
        this.id = id;
        this.content = content;
        this.user = user.toUserDTO();
        this.customerInfo = customerInfo.toCustomerInfoDTO();
    }

    public Cart toCart() {
        return new Cart()
                .setId(id)
                .setContent(content)
                .setUser(user.toUser())
                .setCustomerInfo(customerInfo.toCustomerInfo())
               ;
    }
}
