package com.cg.service.orderdetail;

import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.Product;
import com.cg.model.dto.CustomerInfoDTO;
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
    public  List<OrderDetailDTO> findAllOrderByCreatedAtDesc() {
        return orderDetailRepository.findAllOrderByCreatedAtDesc();
    }

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
            orderDTO.setStatusOrder("????n h??ng ???? duy???t");
            orderRepository.save(orderDTO.toOrder());
        }
        orderDetail.setStatusOrderDetail("????n h??ng ???? duy???t");
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail cancelOrder(OrderDetail orderDetail, String userName) {
        List<OrderDTO> orderList = orderRepository.findAllOrderDTOByOrderDetailId(orderDetail.getId());
        for (OrderDTO orderDTO : orderList){
            orderDTO.setStatusOrder("???? H???y ????n h??ng");
            orderRepository.save(orderDTO.toOrder());
            List<ProductDTO> productDTO = productRepository.findAllProductDTOByCOde(orderDTO.getProductCode());
            for (ProductDTO productDTO1: productDTO){
                productDTO1.setQuantity(productDTO1.getQuantity().add(orderDTO.getQuantity()));
                productRepository.save(productDTO1.toProduct());
            }
        }
        orderDetail.setStatusOrderDetail("???? H???y ????n h??ng");
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail deliveryOrder(OrderDetail orderDetail, String userName) {
        List<OrderDTO> orderList = orderRepository.findAllOrderDTOByOrderDetailId(orderDetail.getId());
        for (OrderDTO orderDTO : orderList){
            orderDTO.setStatusOrder("??ang giao h??ng");
            orderRepository.save(orderDTO.toOrder());
        }
        orderDetail.setStatusOrderDetail("??ang giao h??ng");
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail successDeliveryOrder(OrderDetail orderDetail, String userName) {
        List<OrderDTO> orderList = orderRepository.findAllOrderDTOByOrderDetailId(orderDetail.getId());
        for (OrderDTO orderDTO : orderList){
            orderDTO.setStatusOrder("???? giao h??ng th??nh c??ng");
            orderRepository.save(orderDTO.toOrder());
        }
        Optional<CustomerInfoDTO> customerInfoDTO = customerInfoRepository.findUserDTOByUserName(userName);
        customerInfoDTO.get().setDebt(customerInfoDTO.get().getDebt().add(orderDetail.getGrandTotal()));
        customerInfoRepository.save(customerInfoDTO.get().toCustomerInfo());
        orderDetail.setStatusOrderDetail("???? giao h??ng th??nh c??ng");
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetailDTO> findAllOrderDetailByStatusAndUserName(String status, String username) {
        return orderDetailRepository.findAllOrderDetailByStatusAndUserName(status , username);
    }

    @Override
    public List<OrderDetailDTO> findOderByCreateMonthYear(int createMonth, int createYear) {
        return orderDetailRepository.findOderByCreateMonthYear(createMonth,createYear);
    }

    @Override
    public List<OrderDetailDTO> findOderByCreateMonthYearAndStatusOrder(int createMonth, int createYear, String statusOrderDetail) {
        return orderDetailRepository.findOderByCreateMonthYearAndStatusOrder(createMonth,createYear,statusOrderDetail);
    }

//    @Override
//    public List<OrderDetailDTO> findOderByCreateMonthYear(int createMonth, int createYear) {
//        return orderDetailRepository.findOderByCreateMonthYear(createMonth,createYear);
//    }

    @Override
    public List<OrderDetailDTO> findAllOrderDetailByStatusWait(String status) {
        return orderDetailRepository.findAllOrderDetailByStatusWait(status);
    }

    @Override
    public List<OrderDetailDTO> findOrderDetailByUserName(String userName) {
        return orderDetailRepository.findOrderDetailByUserName(userName) ;
    }


}
