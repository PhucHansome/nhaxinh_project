package com.cg.service.orderdetail;

import com.cg.model.OrderDetail;
import com.cg.model.dto.OrderDetailDTO;
import com.cg.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl  implements OrderDetailService{

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findAll() {
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public OrderDetail getById(Long id) {
        return null;
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(OrderDetail orderDetail) {

    }

    @Override
    public List<OrderDetailDTO> findAllOrderDetailDTO() {
        return orderDetailRepository.findAllOrderDetailDTO();
    }
}
