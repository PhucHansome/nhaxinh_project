package com.cg.service.orderdetail;

import com.cg.model.OrderDetail;
import com.cg.model.dto.OrderDetailDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface OrderDetailService extends IGeneralService<OrderDetail> {
    List<OrderDetailDTO> findAllOrderDetailDTO();
}
