package com.cg.service.order;

import com.cg.model.Order;
import com.cg.model.dto.OrderDTO;
import com.cg.service.IGeneralService;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderService extends IGeneralService<Order> {
    List<OrderDTO> findOrderDTOByUserName(String userName);

    List<OrderDTO> findOrderDTO();

    List<OrderDTO> findOrderDTOByUserNameAndStatus(String userName,String status);

    List<OrderDTO> findOrderDTOByUserNameAndStatus2(String userName,String status);

    List<OrderDTO> findAllOrderDTOByOrderDetailId (Long id);

    List<OrderDTO> findAllOrderDTOByOrderDetailId(Long id, String username);

    List<OrderDTO> findOderByCreateBetween(Date date1, Date date2);

    List<OrderDTO> findOderByCreateMonthYear(@Param("createMonth") int createMonth, @Param("createYear") int createYear);

    List<OrderDTO> findOderByCreateMonthYearAndStatusOrder(@Param("createMonth") int createMonth, @Param("createYear") int createYear,@Param("statusOrder") String statusOrder );

    List<OrderDTO> findOrderDTOByUserNameByTime(String userName);
//    List<OrderDTO> findOderByCreateYear(@Param("createYear") int createYear);


    List<OrderDTO> findOrderDTOByTop5Product(String order);

    List<OrderDTO> findAllOrderDTOByOrderDetailIdAndStatus(Long id,String status);

    Order CreateOrderInDashBoard(Order order,String username);

//    List<OrderDTO> findOrderDTOStatistical();

}
