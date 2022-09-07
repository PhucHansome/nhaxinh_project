package com.cg.service.order;

import com.cg.model.Order;
import com.cg.model.dto.OrderDTO;
import com.cg.service.IGeneralService;
import org.hibernate.id.IdentifierGenerator;

import java.util.List;

public interface OrderService extends IGeneralService<Order> {
    List<OrderDTO> findOrderDTOByUserName(String userName);

    List<OrderDTO> findOrderDTO();

    List<OrderDTO> findOrderDTOByUserNameAndStatus(String userName,String status);

    List<OrderDTO> findAllOrderDTOByOrderDetailId (Long id);
}
