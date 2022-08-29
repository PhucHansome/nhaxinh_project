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

    @OneToOne
    @JoinColumn(name = "customerinf_id")
    private CustomerInfo customerInfo;


    public CartDTO toCartDTO(){
        return new CartDTO()
                .setId(String.valueOf(id))
                .setUser(user.toUserDTO())
                .setCustomerInfo(customerInfo.toCustomerInfoDTO())
                ;
    }
}
