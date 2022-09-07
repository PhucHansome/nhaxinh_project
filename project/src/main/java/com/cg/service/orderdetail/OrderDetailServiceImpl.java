package com.cg.service.orderdetail;

import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.Product;
import com.cg.model.dto.OrderDTO;
import com.cg.model.dto.OrderDetailDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl  implements OrderDetailService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CartRepository cartRepoSitory;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
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
    public OrderDetail changeStatusCheckOut(OrderDetail orderDetail, String userName) {
        List<OrderDTO> orderList = orderRepository.findAllOrderDTOByOrderDetailId(orderDetail.getId());
        for (OrderDTO orderDTO : orderList){
            orderDTO.setStatusOrder("Đơn hàng đã duyệt");
            orderRepository.save(orderDTO.toOrder());

        }
        orderDetail.setStatusOrderDetail("Đơn hàng đã duyệt");
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail cancelOrder(OrderDetail orderDetail, String userName) {
        List<OrderDTO> orderList = orderRepository.findAllOrderDTOByOrderDetailId(orderDetail.getId());
        for (OrderDTO orderDTO : orderList){
            orderDTO.setStatusOrder("Đã Hủy đơn hàng");
            orderRepository.save(orderDTO.toOrder());
            List<ProductDTO> productDTO = productRepository.findAllProductDTOByCOde(orderDTO.getProductCode());
            for (ProductDTO productDTO1: productDTO){
                productDTO1.setQuantity(productDTO1.getQuantity().add(orderDTO.getQuantity()));
                productRepository.save(productDTO1.toProduct());
            }
        }
        orderDetail.setStatusOrderDetail("Đã Hủy đơn hàng");
        return orderDetailRepository.save(orderDetail);
    }


}
