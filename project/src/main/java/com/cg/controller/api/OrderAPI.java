package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.dto.CartDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.model.dto.OrderDetailDTO;
import com.cg.repository.CartRepository;
import com.cg.repository.OrderDetailRepository;
import com.cg.service.order.OrderService;
import com.cg.service.orderdetail.OrderDetailService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderAPI {
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{username}")
    public ResponseEntity<?> findOrderByUserName(@PathVariable String username) {
        List<OrderDTO> orderList = orderService.findOrderDTOByUserName(username);
        return new ResponseEntity<>(orderList,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findAllOrder(){
        List<OrderDTO> orderDTOS = orderService.findOrderDTO();
        return new ResponseEntity<>(orderDTOS,HttpStatus.OK);
    }
//    @GetMapping("/max")
//    public ResponseEntity<?> findAllOrderMax(){
//        List<OrderDTO> orderDTOS = orderService.findOrderMaxDTO();
//        return new ResponseEntity<>(orderDTOS,HttpStatus.OK);
//    }

    @GetMapping("/order-detail/{id}")
    public ResponseEntity<?> findAllOrderDetailById(@PathVariable Long id){
        Optional<OrderDetail> orderDetailDTOS = orderDetailService.findById(id);
        return new ResponseEntity<>(orderDetailDTOS.get().toOrderDetailDTO(),HttpStatus.OK);
    }

    @GetMapping("/order-detail/findAll/")
    public ResponseEntity<?> findAllOrderDetail(){
        List<OrderDetail> orderDetailDTOS = orderDetailService.findAll();
        return new ResponseEntity<>(orderDetailDTOS,HttpStatus.OK);
    }

    @GetMapping("/order/getOrder/{id}")
    public ResponseEntity<?> findAllOrderByOrderDetailId(@PathVariable Long id){
        List<OrderDTO> orderDTOS = orderService.findAllOrderDTOByOrderDetailId(id);
        return new ResponseEntity<>(orderDTOS,HttpStatus.OK);
    }

    @GetMapping("/order/{username}")
    public ResponseEntity<?> findAllOrderByUserNameAndStatus(@PathVariable String username){
        String userNameCus = '%' + username + '%';
        String status = '%' + "Đang chờ duyệt" + '%';
        List<OrderDTO> orderDetailDTOS = orderService.findOrderDTOByUserNameAndStatus(userNameCus,status);
        return new ResponseEntity<>(orderDetailDTOS,HttpStatus.OK);
    }

    @GetMapping("/order-detail/status/")
    public ResponseEntity<?> findAllOrderById(){
        List<OrderDetailDTO> orderDetailDTOS = orderDetailService.findAllOrderDetailByStatusWait("Đang chờ duyệt");
        return new ResponseEntity<>(orderDetailDTOS,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> doCreateOrder(@RequestBody OrderDTO orderDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
       orderService.save(orderDTO.toOrder());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/order-detail/checkout/{username}")
    public ResponseEntity<?> doCheckOutOrder(@RequestBody OrderDetailDTO orderDetailDTO,@PathVariable String username, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        OrderDetail orderDetail =  orderDetailService.changeStatusCheckOut(orderDetailDTO.toOrderDetail(),username);
        return new ResponseEntity<>(orderDetail.toOrderDetailDTO(), HttpStatus.ACCEPTED);
    }

    @PutMapping("/order-detail/cancel/{username}")
    public ResponseEntity<?> doCancelOrder(@RequestBody OrderDetailDTO orderDetailDTO,@PathVariable String username, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        OrderDetail orderDetail =  orderDetailService.cancelOrder(orderDetailDTO.toOrderDetail(),username);
        return new ResponseEntity<>(orderDetail.toOrderDetailDTO(), HttpStatus.ACCEPTED);
    }
}
