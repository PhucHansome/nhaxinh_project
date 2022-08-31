package com.cg.controller.api;


import com.cg.exception.ResourceNotFoundException;
import com.cg.model.CustomerInfo;
import com.cg.model.dto.CustomerInfoDTO;
import com.cg.service.customerInfo.ICustomerInfoService;
import com.cg.service.jwt.JwtService;
import com.cg.service.locationRegion.ILocationRegionService;
import com.cg.utils.AppUtil;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customerInfo")
public class CustomerInfoAPI {

    @Autowired
    private ICustomerInfoService customerInfoService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ILocationRegionService locationRegionService;

    @GetMapping()
    public ResponseEntity<?> showListCustomerInfo() {
        List<CustomerInfoDTO> customerInfos = customerInfoService.findAllCustomerInfoDTOByDeletedIsFailse();
        if (customerInfos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerInfos, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody CustomerInfoDTO customerInfoDT0, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        customerInfoDT0.getLocationRegion().setId(0L);

        CustomerInfo customerInfo = customerInfoService.save(customerInfoDT0.toCustomerInfo());
        return new ResponseEntity<>(customerInfo.toCustomerInfoDTO(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        Optional<CustomerInfoDTO> customerInfo = customerInfoService.findUserDTOById(id);
        return new ResponseEntity<>(customerInfo.get().toCustomerInfo(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ModelAndView showCustomerInfoDetail(@PathVariable long id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/userDashboard/detail-user");
        Optional<CustomerInfo> customerInfo = customerInfoService.findById(id);
        modelAndView.addObject("customerInfo", customerInfo);
        return modelAndView;
    }

}
