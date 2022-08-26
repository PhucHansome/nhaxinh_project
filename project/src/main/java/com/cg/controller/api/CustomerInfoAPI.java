package com.cg.controller.api;


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

import java.util.List;

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
        if (customerInfos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerInfos, HttpStatus.OK);
    }


      @PostMapping("/create")
      public ResponseEntity<?> doCreate(@RequestBody CustomerInfoDTO customerInfoDT0, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }
          customerInfoDT0.getLocationRegion().setId(0L);

          CustomerInfo customerInfo = customerInfoService.save(customerInfoDT0.toCustomerInfo());
        return new ResponseEntity<>(customerInfo.toCustomerInfoDTO(), HttpStatus.OK);
      }
}
