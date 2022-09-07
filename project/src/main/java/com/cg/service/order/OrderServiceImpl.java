package com.cg.service.order;

import com.cg.model.Cart;
import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.dto.*;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
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
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Boolean existById(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public Order save(Order order) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(0L);
        orderDetail.setStatusOrderDetail("abc");
        orderDetailRepository.save(orderDetail);
        BigDecimal sum = BigDecimal.valueOf(0);
        Optional<OrderDetailDTO> orderNew = orderDetailRepository.findOrderDetailNew("abc");
        List<CartItemsDTO> cartItemsDTOList = cartItemRepository.findCartItemDTOById(order.getCustomerInfo().getUserName());
        for (CartItemsDTO cartItemsDTO : cartItemsDTOList){
            Optional<ProductDTO> productDTO =  productRepository.findProductDTOById(cartItemsDTO.getProduct().getId());
            productDTO.get().setQuantity(productDTO.get().getQuantity().subtract(cartItemsDTO.getQuantity()));
            productRepository.save(productDTO.get().toProduct());
            order.setId(0L);
            order.setOrderDetail(orderNew.get().toOrderDetail());
            order.setQuantity(cartItemsDTO.getQuantity());
            order.setProductCode(cartItemsDTO.getProduct().getCode());
            order.setProductTitle(cartItemsDTO.getProduct().getTitle());
            order.setProductImage(cartItemsDTO.getProduct().getImage());
            order.setGrandTotal(cartItemsDTO.getGrandTotal());
            sum = order.getGrandTotal().add(sum);
            cartItemRepository.deleteById(cartItemsDTO.getId());
            orderRepository.save(order);
        }

        List<CartDTO> cartDTOList = cartRepoSitory.getCartItemDTOByIdCustomerInfo(order.getCustomerInfo().getId());
        for (CartDTO cartDTO : cartDTOList){
            if (cartDTO.toCart().getCustomerInfo().getId().equals(order.getCustomerInfo().getId())){
                cartRepoSitory.deleteById(cartDTO.getId());
            }
        }
        List<OrderDTO> orderDTOS = orderRepository.findOrderDTOByUserName(order.getCustomerInfo().getUserName());
        for (OrderDTO order1 : orderDTOS){
            orderNew.get().setStatusOrderDetail(order1.getStatusOrder());
            orderNew.get().setFullName(order1.getCustomerInfo().getFullName());
        }
        orderNew.get().setGrandTotal(sum);
        orderDetailRepository.save(orderNew.get().toOrderDetail());

        return null;
    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void softDelete(Order order) {

    }

    @Override
    public List<OrderDTO> findOrderDTOByUserName(String userName) {
        return orderRepository.findOrderDTOByUserName(userName);
    }

    @Override
    public List<OrderDTO> findOrderDTO() {
        return orderRepository.findOrderDTO();
    }

    @Override
    public List<OrderDTO> findOrderDTOByUserNameAndStatus(String userName, String status) {
        List<OrderDTO> order = orderRepository.findOrderDTOByUserName(userName);
//        for (OrderDTO orderDTO : order) {
////            List<OrderDetailDTO> orderDetailDTOS = orderDetailRepository.findAllOrderDetailDTOByOrderId(orderDTO.getId());
////            for (OrderDetailDTO orderDetailDTOSs : orderDetailDTOS) {
//
//            }
//        }

        return order;
    }

    @Override
    public List<OrderDTO> findAllOrderDTOByOrderDetailId(Long id) {
        return orderRepository.findAllOrderDTOByOrderDetailId(id);
    }
}
