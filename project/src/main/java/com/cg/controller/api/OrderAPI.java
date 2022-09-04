package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.Order;
import com.cg.model.dto.CartDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.repository.CartRepository;
import com.cg.service.order.OrderService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{username}")
    public ResponseEntity<?> findOrderByUserName(@PathVariable String username) {
        List<OrderDTO> orderList = orderService.findOrderDTOByUserName(username);
        return new ResponseEntity<>(orderList,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> doCreateOrder(@RequestBody OrderDTO orderDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
       orderService.save(orderDTO.toOrder());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
