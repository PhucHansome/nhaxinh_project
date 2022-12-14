package com.cg.model;


import com.cg.model.dto.CustomerInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "customerInfos")
public class CustomerInfo extends BaseEntity{

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String userName;

   private String fullName;
    
    private String phone;

    private BigDecimal debt;


    @OneToOne
    @JoinColumn(name = "location_region_id", nullable = false)
    private LocationRegion locationRegion;


    public CustomerInfoDTO toCustomerInfoDTO(){
        return  new CustomerInfoDTO()
                .setId(id)
                .setUserName(userName)
                .setFullName(fullName)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                .setDebt(debt)
                ;
    }

}
