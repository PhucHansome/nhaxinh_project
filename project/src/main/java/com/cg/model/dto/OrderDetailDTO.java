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

    private OrderDTO order;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    public OrderDetailDTO(Long id, Order order, Date createdAt) {
        this.id = id;
        this.order = order.toOrderDTO();
        this.createdAt  = createdAt;
    }

    public OrderDetail toOrderDetail(){
        return new OrderDetail()
                .setId(id)
                .setOrder(order.toOrder())
                ;
    }
}