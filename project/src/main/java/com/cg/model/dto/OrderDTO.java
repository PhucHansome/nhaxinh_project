package com.cg.model.dto;


import com.cg.model.CustomerInfo;
import com.cg.model.LocationRegion;
import com.cg.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDTO {

    private Long id;

    private String description;

    private BigDecimal grandTotal;

    private String fullName;

    private String phone;

    private String email;

    private CustomerInfoDTO customerInfo;

    public Order toOrder(){
        return new Order()
                .setId(id)
                .setDescription(description)
                .setGrandTotal(grandTotal)
                .setFullName(fullName)
                .setPhone(phone)
                .setEmail(email)
                .setCustomerInfo(customerInfo.toCustomerInfo());

    }
}
