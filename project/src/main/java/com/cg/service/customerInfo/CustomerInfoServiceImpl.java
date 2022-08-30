package com.cg.service.customerInfo;

import com.cg.model.CustomerInfo;
import com.cg.model.LocationRegion;
import com.cg.model.dto.CustomerInfoDTO;
import com.cg.repository.CustomerInfoRepository;
import com.cg.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerInfoServiceImpl implements ICustomerInfoService {

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;
    @Override
    public List<CustomerInfo> findAll() {
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Optional<CustomerInfo> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CustomerInfo getById(Long id) {
        return null;
    }

    @Override
    public CustomerInfo save(CustomerInfo customerInfo) {
        LocationRegion locationRegion = locationRegionRepository.save(customerInfo.getLocationRegion());
        customerInfo.setLocationRegion(locationRegion);
        return customerInfoRepository.save(customerInfo);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(CustomerInfo customerInfo) {

    }

    @Override
    public List<CustomerInfoDTO> findAllCustomerInfoDTOByDeletedIsFailse() {
        return customerInfoRepository.findAllCustomerInfoDTOByDeletedIsFailse();
    }

    @Override
    public Optional<CustomerInfoDTO> findUserDTOById(Long id) {
        return customerInfoRepository.findUserDTOById(id);
    }
}
