package com.cg.repository;


import com.cg.model.Order;
import com.cg.model.dto.CartItemsDTO;
import com.cg.model.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT NEW com.cg.model.dto.OrderDTO(" +
            "o.id, " +
            "o.description, " +
            "o.grandTotal , " +
            "o.quantity ," +
            "o.productCode," +
            "o.productImage, " +
            "o.productTitle, " +
            "o.customerInfo," +
            "o.createdAt" +
            " )  " +
            "FROM Order o  WHERE o.customerInfo.userName Like %?1% ")
    List<OrderDTO> findOrderDTOByUserName(String userName);
}
