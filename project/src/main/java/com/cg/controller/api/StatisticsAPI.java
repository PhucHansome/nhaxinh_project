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

    @GetMapping("/sales-statistics-by-date/{date1}")
    public ResponseEntity<?> getSalesStatistics(@PathVariable Date date1) {
        Calendar calendar = new GregorianCalendar(/* remember about timezone! */);
        calendar.setTime(date1);
        date1 = calendar.getTime();
        return new ResponseEntity<>(orderService.findOderByCreateBetween(new Date(), new Date()), HttpStatus.ACCEPTED);
    }


    @GetMapping("/sales-statistics-by-month/{month}/{year}")
    public ResponseEntity<?> getSalesStatistics(@PathVariable int month, @PathVariable int year) {

        List<OrderDTO> orderDTOS = orderService.findOderByCreateMonthYear(month, year);

        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }
}
