package com.cg.controller.api;

import com.cg.model.dto.OrderDTO;
import com.cg.model.dto.OrderDetailDTO;
import com.cg.service.order.OrderService;
import com.cg.service.orderdetail.OrderDetailService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsAPI {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;


    @GetMapping("/sales-statistics-by-month/{month}/{year}")
    public ResponseEntity<?> getSalesStatisticsByMonth(@PathVariable int month, @PathVariable int year,@Param("statusOrderDetail") String statusOrderDetail) {
        List<OrderDetailDTO> orderDetailDTOS = orderDetailService.findOderByCreateMonthYearAndStatusOrder(month, year,"Đã giao hàng thành công");
        return new ResponseEntity<>(orderDetailDTOS, HttpStatus.OK);
    }

//    @GetMapping("/sales-statistics-by-betweenDate/{date1}/{date2}")
//    public ResponseEntity<?> getSalesStatisticsByBetweenDate(@PathVariable Date date1, @PathVariable Date date2) {
//        List<OrderDTO> orderDTOS = orderService.findOderByCreateBetween(date1,date2);
//        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
//    }



}
