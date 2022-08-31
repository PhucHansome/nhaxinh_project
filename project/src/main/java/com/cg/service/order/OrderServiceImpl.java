package com.cg.service.order;

import com.cg.model.Cart;
import com.cg.model.Order;
import com.cg.model.dto.CartDTO;
import com.cg.model.dto.CartItemsDTO;
import com.cg.model.dto.UserDTO;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CartRepository cartRepoSitory;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;


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
        List<CartItemsDTO> cartItemsDTOList = cartItemRepository.findCartItemDTOById(order.getCustomerInfo().getUserName());
        for (CartItemsDTO cartItemsDTO : cartItemsDTOList){
            order.setId(0L);
            order.setQuantity(cartItemsDTO.getQuantity());
            order.setProductCode(cartItemsDTO.getProduct().getCode());
            order.setGrandTotal(cartItemsDTO.getGrandTotal());
            cartItemRepository.deleteById(cartItemsDTO.getId());
            orderRepository.save(order);
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void softDelete(Order order) {

    }
}
