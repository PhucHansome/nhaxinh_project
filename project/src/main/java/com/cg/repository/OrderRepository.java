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
            "o.createdAt, " +
            "o.statusOrder, " +
            "o.orderDetail " +
            " )  " +
            "FROM Order o  WHERE o.customerInfo.userName Like %?1%  ")
    List<OrderDTO> findOrderDTOByUserName(String userName);

    @Query("SELECT NEW com.cg.model.dto.OrderDTO(" +
            "o.id, " +
            "o.description, " +
            "o.grandTotal , " +
            "o.quantity ," +
            "o.productCode," +
            "o.productImage, " +
            "o.productTitle, " +
            "o.customerInfo," +
            "o.createdAt, " +
            "o.statusOrder, " +
            "o.orderDetail " +
            " )  " +
            "FROM Order o  WHERE o.customerInfo.userName Like %?1% order by o.createdAt desc ")
    List<OrderDTO> findOrderDTOByUserNameByTime(String userName);

    @Query("SELECT NEW com.cg.model.dto.OrderDTO(" +
            "o.id, " +
            "o.description, " +
            "o.grandTotal , " +
            "o.quantity ," +
            "o.productCode," +
            "o.productImage, " +
            "o.productTitle, " +
            "o.customerInfo," +
            "o.createdAt, " +
            "o.statusOrder," +
            "o.orderDetail " +
            " )  " +
            "FROM Order o  WHERE o.customerInfo.userName Like ?1 AND o.statusOrder Like ?2")
    List<OrderDTO> findOrderDTOByUserNameAndStatus(String userName,String status);


    @Query("SELECT NEW com.cg.model.dto.OrderDTO(" +
            "o.id, " +
            "o.description, " +
            "o.grandTotal , " +
            "o.quantity ," +
            "o.productCode," +
            "o.productImage, " +
            "o.productTitle, " +
            "o.customerInfo," +
            "o.createdAt, " +
            "o.statusOrder," +
            "o.orderDetail " +
            " )  " +
            "FROM Order o  WHERE  o.orderDetail.id = ?1")
    List<OrderDTO> findAllOrderDTOByOrderDetailId (Long id);


    @Query("SELECT NEW com.cg.model.dto.OrderDTO(" +
            "o.id, " +
            "o.description, " +
            "o.grandTotal , " +
            "o.quantity ," +
            "o.productCode," +
            "o.productImage, " +
            "o.productTitle, " +
            "o.customerInfo," +
            "o.createdAt, " +
            "o.statusOrder, " +
            "o.orderDetail " +
            " )  " +
            "FROM Order o ")
    List<OrderDTO> findOrderDTO();

//    @Query("SELECT new com.cg.model.dto.OrderDTO(" +
//            "o.id, " +
//            "o.description, " +
//            "o.grandTotal , " +
//            "o.quantity ," +
//            "o.productCode," +
//            "o.productImage, " +
//            "o.productTitle, " +
//            "o.customerInfo," +
//            "o.createdAt, " +
//            "o.statusOrder, " +
//            "o.orderDetail " +
//            " )" +
//            "FROM Order o where o.quantity>5" +
//            "order by  o.quantity  DESC " +
//            " LIMIT 5")
//              List<OrderDTO> findOrderMaxDTO();

}

