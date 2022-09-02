package com.cg.service.order;

import com.cg.model.Order;
import com.cg.model.dto.OrderDTO;
import com.cg.service.IGeneralService;
import org.hibernate.id.IdentifierGenerator;

import java.util.List;

public interface OrderService extends IGeneralService<Order> {
    List<OrderDTO> findOrderDTOByUserName(String userName);
}
