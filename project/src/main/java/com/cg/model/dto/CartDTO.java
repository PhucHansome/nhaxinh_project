package com.cg.model.dto;


import com.cg.model.Cart;
import com.cg.model.LocationRegion;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CartDTO {

    private Long id;

    private User user;

    private String fullName;

    private String status;

    private String phone;

    private String content;

    private LocationRegionDTO locationRegion;

    public Cart toCart() {
        return new Cart()
                .setId(id)
                .setFullName(fullName)
                .setStatus(status)
                .setPhone(phone)
                .setContent(content)
                .setLocationRegion(locationRegion.toLocationRegion());
    }
}
