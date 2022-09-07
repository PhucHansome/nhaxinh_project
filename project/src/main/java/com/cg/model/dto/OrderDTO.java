package com.cg.model.dto;


import com.cg.model.CustomerInfo;
import com.cg.model.LocationRegion;
import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDTO {

    private Long id;

    private String description;

    private BigDecimal grandTotal;

    private BigDecimal quantity;

    private String productCode;

    private String productImage;

    private String productTitle;

    private CustomerInfoDTO customerInfo;

    private String statusOrder;

    private OrderDetailDTO orderDetail;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;


    public OrderDTO(Long id, String description, BigDecimal grandTotal, BigDecimal quantity, String productCode, String productImage, String productTitle, CustomerInfo customerInfo, Date createdAt, String statusOrder,OrderDetail orderDetail) {
        this.id = id;
        this.description = description;
        this.grandTotal = grandTotal;
        this.quantity = quantity;
        this.productCode = productCode;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.customerInfo = customerInfo.toCustomerInfoDTO();
        this.createdAt = createdAt;
        this.statusOrder = statusOrder;
        this.orderDetail = orderDetail.toOrderDetailDTO();
    }

    public Order toOrder() {
        return new Order()
                .setId(id)
                .setDescription(description)
                .setGrandTotal(grandTotal)
                .setQuantity(quantity)
                .setProductCode(productCode)
                .setProductImage(productImage)
                .setProductTitle(productTitle)
                .setCustomerInfo(customerInfo.toCustomerInfo())
                .setStatusOrder(statusOrder)
                .setOrderDetail(orderDetail.toOrderDetail())
                ;
    }
}
