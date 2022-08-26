package com.cg.model.dto;


import com.cg.model.CustomerInfo;
import com.cg.model.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerInfoDTO {

    private String id;

    private String userName;

    private String fullName;

    private String phone;

    private BigDecimal debt;

    private LocationRegionDTO locationRegion;

    private Date createAt;

    public CustomerInfoDTO(String id, String userName, String phone, LocationRegion locationRegion) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    public CustomerInfoDTO(String id, String userName, String fullName, String phone, BigDecimal debt, LocationRegion locationRegion, Date createAt) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.phone = phone;
        this.debt = debt;
        this.locationRegion = locationRegion.toLocationRegionDTO();
        this.createAt = createAt;
    }

    public CustomerInfo toCustomerInfo() {
        return  new CustomerInfo()
                .setId(id)
                .setUserName(userName)
                .setFullName(fullName)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setDebt(debt)
                ;
    }

}
