package com.cg.model;


import com.cg.model.dto.CustomerInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "customerIfs")
public class CustomerInfo extends BaseEntity{

    @javax.persistence.Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String userName;

    private String password;
    
    private Long phone;

    @OneToOne
    @JoinColumn(name = "location_region_id", nullable = false)
    private LocationRegion locationRegion;

    public CustomerInfoDTO toCustomerinfoDTO(){
        return  new CustomerInfoDTO()
                .setId(id)
                .setUserName(userName)
                .setPassword(password)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                ;
    }

}
