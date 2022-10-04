package com.cg.repository;

import com.cg.model.OrderDetail;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.model.dto.OrderDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.userName, " +
            "od.phone, " +
            "od.address, " +
            "od.districtName, " +
            "od.provinceName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.priceFormat,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od WHERE od.statusOrderDetail = ?1 ")
    Optional<OrderDetailDTO> findOrderDetailNew(String status);

    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.userName, " +
            "od.phone, " +
            "od.address, " +
            "od.districtName, " +
            "od.provinceName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.priceFormat,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od WHERE od.statusOrderDetail = ?1 ")
    List<OrderDetailDTO> findAllOrderDetailByStatusWait(String status);

    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.userName, " +
            "od.phone, " +
            "od.address, " +
            "od.districtName, " +
            "od.provinceName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.priceFormat,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od WHERE od.statusOrderDetail like ?1 And od.userName = ?2 ")
    List<OrderDetailDTO> findAllOrderDetailByStatusAndUserName(String status, String username);

    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.userName, " +
            "od.phone, " +
            "od.address, " +
            "od.districtName, " +
            "od.provinceName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.priceFormat,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od WHERE od.userName = ?1 ")
    List<OrderDetailDTO> findOrderDetailByUserName(String userName);


    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.userName, " +
            "od.phone, " +
            "od.address, " +
            "od.districtName, " +
            "od.provinceName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.priceFormat,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od ORDER BY od.createdAt desc  ")
    List<OrderDetailDTO> findAllOrderByCreatedAtDesc();


    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.userName, " +
            "od.phone, " +
            "od.address, " +
            "od.districtName, " +
            "od.provinceName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.priceFormat,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od " +
            "where FUNCTION('MONTH', od.createdAt) = :createMonth " +
            "AND FUNCTION('YEAR', od.createdAt) = :createYear " +
            " ")
    List<OrderDetailDTO> findOderByCreateMonthYear(@Param("createMonth") int createMonth, @Param("createYear") int createYear );
    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.userName, " +
            "od.phone, " +
            "od.address, " +
            "od.districtName, " +
            "od.provinceName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.priceFormat,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od " +
            "where FUNCTION('MONTH', od.createdAt) = :createMonth " +
            "AND FUNCTION('YEAR', od.createdAt) = :createYear " +
            "AND od.statusOrderDetail = :statusOrderDetail " +
            " ")
    List<OrderDetailDTO> findOderByCreateMonthYearAndStatusOrder(@Param("createMonth") int createMonth, @Param("createYear") int createYear, @Param("statusOrderDetail") String statusOrderDetail);

//    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
//            "od.id, " +
//            "od.fullName, " +
//            "od.userName, " +
//            "od.phone, " +
//            "od.address, " +
//            "od.districtName, " +
//            "od.provinceName, " +
//            "od.statusOrderDetail,  " +
//            "od.grandTotal,  " +
//            "od.priceFormat,  " +
//            "od.createdAt ," +
//            "od.updatedAt" +
//            ")  " +
//            "FROM OrderDetail od " +
//            "where FUNCTION('MONTH', od.createdAt) = :createMonth " +
//            "AND FUNCTION('YEAR', od.createdAt) = :createYear "
//    )
//    List<OrderDetailDTO> findOderByCreateMonthYear(@Param("createMonth") int createMonth, @Param("createYear") int createYear);
}