package com.cg.repository;

import com.cg.model.OrderDetail;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.OrderDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    @Query("SELECT NEW com.cg.model.dto.OrderDetailDTO (" +
            "od.id, " +
            "od.order, " +
            "od.createdAt " +
            ")  " +
            "FROM OrderDetail od ")
    List<OrderDetailDTO> findAllOrderDetailDTO();
}