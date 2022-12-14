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
import java.util.UUID;

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
            throw new EmailExistsException("Email ???? t???n t???i! Vui l??ng nh???p email kh??c");
        }
        Boolean exitByPhone = customerInfoService.existsByPhone(customerInfoDT0.getPhone());
        if (exitByPhone) {
            throw new EmailExistsException("S??? ??i???n tho???i ???? t???n t???i! Vui l??ng nh???p s??? ??i???n tho???i kh??c");
        }
        customerInfoDT0.getLocationRegion().setId(0L);
        UUID uuid = UUID.randomUUID();
        customerInfoDT0.setId(String.valueOf(uuid));
        try {
            CustomerInfo customerInfo = customerInfoService.save(customerInfoDT0.toCustomerInfo());
            return new ResponseEntity<>(customerInfo.toCustomerInfoDTO(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Kh??ng th??? t???o ???????c kh??ch h??ng", HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/edit")
    private ResponseEntity<?> doUpdate(@Valid @RequestBody CustomerInfoDTO customerInfoDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        new CustomerInfoDTO().validate(customerInfoDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        customerInfoDTO.setUserName(appUtils.getPrincipal());
        customerInfoDTO.getLocationRegion().setId(0L);
        try {
            CustomerInfo customerInfoUpdate = customerInfoService.save(customerInfoDTO.toCustomerInfo());
            return new ResponseEntity<>(customerInfoUpdate.toCustomerInfoDTO(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("?????i th??ng tin kh??ch h??ng th???t b???i", HttpStatus.NO_CONTENT);
        }


    }

    @DeleteMapping("/delete-soft-customer/{id}")
    public ResponseEntity<?> deleteSoft(@PathVariable String id) {
        Optional<CustomerInfoDTO> customerInfoDTO = customerInfoService.findUserDTOById(id);
        if (!customerInfoDTO.isPresent()) {
            throw new DataInputException("Ng?????i d??ng n??y kh??ng t???n t???i");
        }
        try {
            customerInfoService.deleteSoft(customerInfoDTO.get().toCustomerInfo());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
