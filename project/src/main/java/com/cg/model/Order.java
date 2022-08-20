package com.cg.model;


import com.cg.model.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import sun.nio.cs.ext.Big5;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal grandTotal;

    private String fullName;

    private String phone;

    private String email;

    @OneToOne
    @JoinColumn(name = "customerifs_id", nullable = false)
    private CustomerInfo customerInfo;

    public OrderDTO toOrderDTO(){
        return new OrderDTO()
                .setId(id)
                .setDescription(description)
                .setGrandTotal(grandTotal)
                .setFullName(fullName)
                .setPhone(phone)
                .setEmail(email)
                .setCustomerInfo(customerInfo.toCustomerInfoDTO());

    }
}
