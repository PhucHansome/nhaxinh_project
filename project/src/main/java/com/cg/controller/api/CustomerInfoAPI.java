package com.cg.controller.api;


import com.cg.exception.ResourceNotFoundException;
import com.cg.model.CustomerInfo;
import com.cg.model.dto.CustomerInfoDTO;
import com.cg.service.customerInfo.ICustomerInfoService;
import com.cg.service.locationRegion.ILocationRegionService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        Optional<CustomerInfoDTO> customerInfoDTO = customerInfoService.findUserDTOById(id);
        if (!customerInfoDTO.isPresent()) {
            throw new ResourceNotFoundException("Invalid User ID");
        }
        return new ResponseEntity<>(customerInfoDTO.get().toCustomerInfo(), HttpStatus.OK);
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<?> getCustomerByUserName(@PathVariable String userName) {
        Optional<CustomerInfoDTO> customerInfoDTO = customerInfoService.findUserDTOByUserName(userName);
        if (!customerInfoDTO.isPresent()) {
            throw new ResourceNotFoundException("Invalid User ID");
        }
        return new ResponseEntity<>(customerInfoDTO.get().toCustomerInfo(), HttpStatus.OK);
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

    @PutMapping("/edit")
    private ResponseEntity<?> doUpdate(@RequestBody CustomerInfoDTO customerInfoDTO, BindingResult bindingResult){
          if (bindingResult.hasFieldErrors()) {
              return appUtils.mapErrorToResponse(bindingResult);
          }
          customerInfoDTO.getLocationRegion().setId(0L);

          CustomerInfo customerInfoUpdate = customerInfoService.save(customerInfoDTO.toCustomerInfo());
          return new ResponseEntity<>(customerInfoUpdate.toCustomerInfoDTO(), HttpStatus.ACCEPTED);
      }




}
