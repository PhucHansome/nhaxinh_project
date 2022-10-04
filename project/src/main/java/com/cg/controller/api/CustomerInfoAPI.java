package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
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

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
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
    public ResponseEntity<?> doCreate(@Valid @RequestBody CustomerInfoDTO customerInfoDT0, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Boolean exitByUserName = customerInfoService.existsByUserName(customerInfoDT0.getUserName());
        if (exitByUserName) {
            throw new EmailExistsException("Email đã tồn tại! Vui lòng nhập email khác");
        }
        Boolean exitByPhone = customerInfoService.existsByPhone(customerInfoDT0.getPhone());
        if (exitByPhone) {
            throw new EmailExistsException("Số điện thoại đã tồn tại! Vui lòng nhập số điện thoại khác");
        }

        customerInfoDT0.getLocationRegion().setId(0L);
        try {
            CustomerInfo customerInfo = customerInfoService.save(customerInfoDT0.toCustomerInfo());
            return new ResponseEntity<>(customerInfo.toCustomerInfoDTO(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Không thể tạo được khách hàng", HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/edit")
    private ResponseEntity<?> doUpdate(@Valid @RequestBody CustomerInfoDTO customerInfoDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        new CustomerInfoDTO().validate(customerInfoDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean exitByPhone = customerInfoService.existsByPhone(customerInfoDTO.getPhone());
        if (exitByPhone) {
            throw new EmailExistsException("Số điện thoại đã tồn tại! Vui lòng nhập số điện thoại khác");
        }

        customerInfoDTO.getLocationRegion().setId(0L);
        try {
            CustomerInfo customerInfoUpdate = customerInfoService.save(customerInfoDTO.toCustomerInfo());
            return new ResponseEntity<>(customerInfoUpdate.toCustomerInfoDTO(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Đổi thông tin khách hàng thất bại", HttpStatus.NO_CONTENT);
        }


    }

    @DeleteMapping("/delete-soft-customer/{id}")
    public ResponseEntity<?> deleteSoft(@PathVariable String id) {
        Optional<CustomerInfoDTO> customerInfoDTO = customerInfoService.findUserDTOById(id);
        if (!customerInfoDTO.isPresent()) {
            throw new DataInputException("Người dùng này không tồn tại");
        }
        try {
            customerInfoService.deleteSoft(customerInfoDTO.get().toCustomerInfo());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
