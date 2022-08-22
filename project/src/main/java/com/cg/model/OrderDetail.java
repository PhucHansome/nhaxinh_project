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
public class OrderDetail{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public OrderDetailDTO toOrderDetailDTO(){
        return new OrderDetailDTO()
                .setId(id)
                .setOrder(order.toOrderDTO())
                .setProduct(product.toProductDTO())
                ;
    }
}