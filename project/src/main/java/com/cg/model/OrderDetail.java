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

    private BigDecimal grandTotal;

    public OrderDetailDTO toOrderDetailDTO(){
        return new OrderDetailDTO()
                .setId(id)
                .setStatusOrderDetail(statusOrderDetail)
                .setFullName(fullName)
                .setGrandTotal(grandTotal)
                ;
    }
}