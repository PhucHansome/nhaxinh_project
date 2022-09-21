package com.cg.controller.api;

import com.cg.model.dto.OrderDTO;
import com.cg.service.order.OrderService;
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


    @GetMapping("/sales-statistics-by-month/{month}/{year}")
    public ResponseEntity<?> getSalesStatisticsByMonth(@PathVariable int month, @PathVariable int year) {
        List<OrderDTO> orderDTOS = orderService.findOderByCreateMonthYear(month, year);
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

    @GetMapping("/sales-statistics-by-year/{year}")
    public ResponseEntity<?> getSalesStatisticsByYear(@PathVariable int year) {
        List<OrderDTO> orderDTOS = orderService.findOderByCreateYear(year);
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }



}
