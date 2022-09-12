package com.cg.model;


import com.cg.model.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@Table(name = "order_details")
public class OrderDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statusOrderDetail;

    private String fullName;

    private String userName;

    private String phone;

    private String address;

    private String districtName;

    private String provinceName;

    private BigDecimal grandTotal;

    private String priceFormat;


    public OrderDetailDTO toOrderDetailDTO(){
        return new OrderDetailDTO()
                .setId(id)
                .setStatusOrderDetail(statusOrderDetail)
                .setFullName(fullName)
                .setGrandTotal(grandTotal)
                .setAddress(address)
                .setDistrictName(districtName)
                .setProvinceName(provinceName)
                .setUserName(userName)
                .setPhone(phone)
                ;
    }
}