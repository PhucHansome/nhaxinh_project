package com.cg.repository;

import com.cg.model.OrderDetail;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.OrderDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od WHERE od.statusOrderDetail = ?1 ")
    Optional<OrderDetailDTO> findOrderDetailNew(String status);

    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.fullName, " +
            "od.statusOrderDetail,  " +
            "od.grandTotal,  " +
            "od.createdAt ," +
            "od.updatedAt" +
            ")  " +
            "FROM OrderDetail od WHERE od.statusOrderDetail = ?1 ")
    List<OrderDetailDTO> findAllOrderDetailByStatusWait(String status);
}