package com.cg.service.customerInfo;

import com.cg.model.CustomerInfo;
import com.cg.model.dto.CustomerInfoDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICustomerInfoService extends IGeneralService<CustomerInfo> {

    List<CustomerInfoDTO> findAllCustomerInfoDTOByDeletedIsFailse();

}
