package com.cg.model;


import com.cg.model.dto.CartDTO;
import com.cg.model.dto.CustomerInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
@Accessors(chain = true)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fullName;

    private String status;

    private String phone;

    private String content;

    @OneToOne
    @JoinColumn(name = "customerins_id", nullable = false)
    private CustomerInfo customerInfo;


    public CartDTO toCartDTO(){
        return new CartDTO()
                .setId(id)
                .setFullName(fullName)
                .setStatus(status)
                .setPhone(phone)
                .setContent(content)
                .setCustomerInfo(customerInfo.toCustomerinfoDTO());
    }
}
