package com.cg.repository;


import com.cg.model.Order;
import com.cg.model.dto.OrderDTO;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
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
            "o.updatedAt, " +
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
            "o.updatedAt, " +
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
            "o.updatedAt, " +
            "o.statusOrder," +
            "o.orderDetail " +
            " )  " +
            "FROM Order o  WHERE o.customerInfo.userName Like ?1 AND o.statusOrder Like ?2")
    List<OrderDTO> findOrderDTOByUserNameAndStatus(String userName, String status);


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
            "o.updatedAt, " +
            "o.statusOrder," +
            "o.orderDetail " +
            " )  " +
            "FROM Order o  WHERE  o.orderDetail.id = ?1  ")
    List<OrderDTO> findAllOrderDTOByOrderDetailId(Long id);

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
            "o.updatedAt, " +
            "o.statusOrder," +
            "o.orderDetail " +
            " )  " +
            "FROM Order o  WHERE  o.orderDetail.id = ?1 and o.customerInfo.userName = ?2 ")
    List<OrderDTO> findAllOrderDTOByOrderDetailId(Long id, String username);

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
            "o.updatedAt, " +
            "o.statusOrder," +
            "o.orderDetail " +
            " )  " +
            "FROM Order o  WHERE  o.orderDetail.id = ?1 and o.statusOrder = ?2 " )
    List<OrderDTO> findAllOrderDTOByOrderDetailIdAndStatus(Long id,String status);


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
            "o.updatedAt, " +
            "o.statusOrder, " +
            "o.orderDetail " +
            " )  " +
            "FROM Order o ")
    List<OrderDTO> findOrderDTO();

//    @Query("SELECT NEW com.cg.model.dto.OrderDTO(count (o.quantity)" +
//            ""   +
//            "o.productTitle " +
//            " )  " +
//            "FROM Order as o " +
//            "GROUP BY o.quantity")
//    List<OrderDTO> findOrderDTOStatistical();

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
            "o.updatedAt, " +
            "o.statusOrder, " +
            "o.orderDetail " +
            " )  " +
            "FROM Order o where o.statusOrder = ?1")
    List<OrderDTO> findOrderDTOByTop5Product(String order);

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
//            "FROM Order o " +
//            "where FUNCTION('YEAR', o.createdAt) = :createYear " +
//            " ")
//    List<OrderDTO> findOderByCreateYear(@Param("createYear") int createYear);

    @Query("SELECT new com.cg.model.dto.OrderDTO(" +
            "o.id, " +
            "o.description, " +
            "o.grandTotal , " +
            "o.quantity ," +
            "o.productCode," +
            "o.productImage, " +
            "o.productTitle, " +
            "o.customerInfo," +
            "o.createdAt, " +
            "o.updatedAt, " +
            "o.statusOrder, " +
            "o.orderDetail " +
            " )" +
            "FROM Order o " +
            "where FUNCTION('MONTH', o.createdAt) = :createMonth " +
            "AND FUNCTION('YEAR', o.createdAt) = :createYear " +
            " ")
    List<OrderDTO> findOderByCreateMonthYear(@Param("createMonth") int createMonth, @Param("createYear") int createYear);


    @Query("SELECT new com.cg.model.dto.OrderDTO(" +
            "o.id, " +
            "o.description, " +
            "o.grandTotal , " +
            "o.quantity ," +
            "o.productCode," +
            "o.productImage, " +
            "o.productTitle, " +
            "o.customerInfo," +
            "o.createdAt, " +
            "o.updatedAt, " +
            "o.statusOrder, " +
            "o.orderDetail " +
            " )" +
            "FROM Order o " +
            "where FUNCTION('MONTH', o.createdAt) = :createMonth " +
            "AND FUNCTION('YEAR', o.createdAt) = :createYear " +
            "AND o.statusOrder = :statusOrder " +
            " ")
    List<OrderDTO> findOderByCreateMonthYearAndStatusOrder(@Param("createMonth") int createMonth, @Param("createYear") int createYear, @Param("statusOrder") String statusOrder);

    @Query("SELECT new com.cg.model.dto.OrderDTO(" +
            "o.id, " +
            "o.description, " +
            "o.grandTotal , " +
            "o.quantity ," +
            "o.productCode," +
            "o.productImage, " +
            "o.productTitle, " +
            "o.customerInfo," +
            "o.createdAt, " +
            "o.updatedAt, " +
            "o.statusOrder, " +
            "o.orderDetail " +
            " )" +
            "FROM Order o " +
            "where o.createdAt between ?1 and ?2" +
            " ")
    List<OrderDTO> findOderByCreateBetween(Date date1, Date date2);


}

