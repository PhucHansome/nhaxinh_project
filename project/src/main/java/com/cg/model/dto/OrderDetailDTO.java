package com.cg.model.dto;

import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDetailDTO {
    private Long id;

    private String fullName;

    private String userName;

    private String phone;

    private String address;

    private String districtName;

    private String provinceName;

    private String statusOrderDetail;

    private BigDecimal grandTotal;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;


//    public OrderDetailDTO(Long id ,String fullName,String statusOrderDetail ,Date createdAt, Date updatedAt) {
//        this.id = id;
//        this.fullName = fullName;
//        this.statusOrderDetail = statusOrderDetail;
//        this.createdAt  = createdAt;
//        this.updatedAt = updatedAt;
//    }

    public OrderDetail toOrderDetail(){
        return new OrderDetail()
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