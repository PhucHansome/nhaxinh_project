package com.cg.service.orderdetail;

import com.cg.model.OrderDetail;
import com.cg.model.dto.OrderDetailDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface OrderDetailService extends IGeneralService<OrderDetail> {

    OrderDetail changeStatusCheckOut(OrderDetail orderDetail, String userName);

    OrderDetail cancelOrder(OrderDetail orderDetail, String userName);

    List<OrderDetailDTO> findAllOrderDetailByStatusWait(String status);

    List<OrderDetail>findOrderDetailByUserName(String userName);
}
