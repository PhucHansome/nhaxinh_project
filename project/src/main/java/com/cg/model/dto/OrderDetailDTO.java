package com.cg.model.dto;

import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDetailDTO {
    private Long id;

    private OrderDTO order;

    private ProductDTO product;

    public OrderDetail toOrderDetail(){
        return new OrderDetail()
                .setId(id)
                .setOrder(order.toOrder())
                .setProduct(product.toProduct())
               ;
    }
}
