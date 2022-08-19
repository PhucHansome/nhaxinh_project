package com.cg.model.dto;


import com.cg.model.CustomerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerInfoDTO {

    private String id;

    private String userName;

    private String phone;

    private BigDecimal debt;

    private LocationRegionDTO locationRegion;

    public CustomerInfo toCustomerInfo() {
        return  new CustomerInfo()
                .setId(id)
                .setUserName(userName)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setDebt(debt)
                ;
    }

}
