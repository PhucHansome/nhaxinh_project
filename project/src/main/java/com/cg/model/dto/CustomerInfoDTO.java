package com.cg.model.dto;


import com.cg.model.CustomerInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerInfoDTO {

    private String id;

    private String userName;

    private String password;

    private Long phone;

    private LocationRegionDTO locationRegion;

    public CustomerInfo toCustomerInfo() {
        return  new CustomerInfo()
                .setId(id)
                .setUserName(userName)
                .setPassword(password)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                ;
    }

}
