package com.cg.service.orderdetail;

import com.cg.model.OrderDetail;
import com.cg.model.dto.OrderDetailDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService extends IGeneralService<OrderDetail> {

    OrderDetail changeStatusCheckOut(OrderDetail orderDetail, String userName);

    OrderDetail cancelOrder(OrderDetail orderDetail, String userName);

    List<OrderDetailDTO> findAllOrderDetailByStatusWait(String status);

    List<OrderDetailDTO>findOrderDetailByUserName(String userName);

    List<OrderDetailDTO> findAllOrderByCreatedAtDesc();

    OrderDetail deliveryOrder(OrderDetail orderDetail, String userName);

    OrderDetail successDeliveryOrder(OrderDetail orderDetail, String userName);

    List<OrderDetailDTO> findAllOrderDetailByStatusAndUserName(String status, String username);
    List<OrderDetailDTO> findOderByCreateMonthYear(@Param("createMonth") int createMonth, @Param("createYear") int createYear );

//    List<OrderDetailDTO> findOderByCreateMonthYear(@Param("createMonth") int createMonth, @Param("createYear") int createYear);

    List<OrderDetailDTO> findOderByCreateMonthYearAndStatusOrder(@Param("createMonth") int createMonth, @Param("createYear") int createYear, @Param("statusOrderDetail") String statusOrderDetail );

}
