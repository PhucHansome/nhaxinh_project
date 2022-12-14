package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.model.OrderDetail;
import com.cg.model.dto.OrderDTO;
import com.cg.model.dto.OrderDetailDTO;
import com.cg.repository.CartRepository;
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
import java.util.*;

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
        if (orderList.isEmpty()){
            throw new RuntimeException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderList,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> findAllOrder(){
        List<OrderDTO> orderDTOS = orderService.findOrderDTO();
        if (orderDTOS.isEmpty()){
            throw new RuntimeException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderDTOS,HttpStatus.OK);
    }

//    @GetMapping("/statistical")
//    public ResponseEntity<?> findAllOrderStatistical(){
//        List<OrderDTO> orderDTOS = orderService.findOrderDTOStatistical();
//        if (orderDTOS.isEmpty()){
//            throw new RuntimeException("Không tìm thấy order!");
//        }
//        return new ResponseEntity<>(orderDTOS,HttpStatus.OK);
//    }

    @GetMapping("/order-top-5/")
    public ResponseEntity<?> findAllOrderFortop5(){
        List<OrderDTO> orderDTOS = orderService.findOrderDTOByTop5Product("Đã giao hàng thành công");
        if (orderDTOS.isEmpty()){
            throw new RuntimeException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderDTOS,HttpStatus.OK);
    }


    @GetMapping("/order-detail/{id}")
    public ResponseEntity<?> findAllOrderDetailById(@PathVariable Long id){
        Optional<OrderDetail> orderDetailDTOS = orderDetailService.findById(id);
        if (!orderDetailDTOS.isPresent()){
            throw new RuntimeException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderDetailDTOS.get().toOrderDetailDTO(),HttpStatus.OK);
    }

    @GetMapping("/order-detail/user-name/{username}")
    public ResponseEntity<?> findAllOrderDetailByOrder(@PathVariable String username){
        List<OrderDetailDTO> orderDetailDTOS = orderDetailService.findOrderDetailByUserName(username);
        if(orderDetailDTOS.isEmpty()){
            throw new DataInputException("Rỗng");
        }
        return new ResponseEntity<>(orderDetailDTOS,HttpStatus.OK);
    }


    @GetMapping("/order-detail/findAll/")
    public ResponseEntity<?> findAllOrderDetail(){
        List<OrderDetail> orderDetailDTOS = orderDetailService.findAll();
        if (orderDetailDTOS.isEmpty()){
            throw new RuntimeException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderDetailDTOS,HttpStatus.OK);
    }

    @GetMapping("/order/getOrder/{id}")
    public ResponseEntity<?> findAllOrderByOrderDetailId(@PathVariable Long id){
        List<OrderDTO> orderDTOS = orderService.findAllOrderDTOByOrderDetailId(id);
        if (orderDTOS.isEmpty()){
            throw new RuntimeException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderDTOS,HttpStatus.OK);
    }

    @GetMapping("/order/{username}")
    public ResponseEntity<?> findAllOrderByUserNameAndStatus(@PathVariable String username){
        String userNameCus = '%' + username + '%';
        String status = '%' + "Đang chờ duyệt" + '%';
        List<OrderDTO> orderDetailDTOS = orderService.findOrderDTOByUserNameAndStatus(userNameCus,status);
        if (orderDetailDTOS.isEmpty()){
            throw new RuntimeException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderDetailDTOS,HttpStatus.OK);
    }


    @GetMapping("/order-detail/status/")
    public ResponseEntity<?> findAllOrderById(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        List<OrderDetailDTO> orderDetailDTOS = orderDetailService.findOderByCreateMonthYearAndStatusOrder(gregorianCalendar.get(Calendar.MONTH) + 1,gregorianCalendar.get(Calendar.YEAR),"Đang chờ duyệt");
        if (orderDetailDTOS.isEmpty()){
            throw new RuntimeException("Không tìm thấy order!");
        }
        return new ResponseEntity<>(orderDetailDTOS,HttpStatus.OK);
    }

    /**
     * vdvdvd
     * @param orderDTO Dung de lam ..
     * @param bindingResult
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    @PostMapping
    public ResponseEntity<?> doCreateOrder(@RequestBody OrderDTO orderDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            orderService.save(orderDTO.toOrder());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-order-dashboard")
    public ResponseEntity<?> doCreateOrderInDashBoard(@RequestBody OrderDTO orderDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        String username = appUtils.getPrincipal();
        System.out.println(username);

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            orderService.saveOrderInDashBoard(orderDTO.toOrder(),username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/order-detail/checkout/{username}")
    public ResponseEntity<?> doCheckOutOrder(@RequestBody OrderDetailDTO orderDetailDTO,@PathVariable String username, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            OrderDetail orderDetail =  orderDetailService.changeStatusCheckOut(orderDetailDTO.toOrderDetail(),username);
            return new ResponseEntity<>(orderDetail.toOrderDetailDTO(), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/order-detail/cancel/{username}")
    public ResponseEntity<?> doCancelOrder(@RequestBody OrderDetailDTO orderDetailDTO,@PathVariable String username, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            OrderDetail orderDetail =  orderDetailService.cancelOrder(orderDetailDTO.toOrderDetail(),username);
            return new ResponseEntity<>(orderDetail.toOrderDetailDTO(), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/order-detail/delivery/{username}")
    public ResponseEntity<?> doDeliveryOrder(@RequestBody OrderDetailDTO orderDetailDTO,@PathVariable String username, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(orderDetailService.deliveryOrder(orderDetailDTO.toOrderDetail(),username), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/order-detail/success-delivery/{username}")
    public ResponseEntity<?> doSuccessDeliveryOrder(@RequestBody OrderDetailDTO orderDetailDTO,@PathVariable String username, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(orderDetailService.successDeliveryOrder(orderDetailDTO.toOrderDetail(),username), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


}
