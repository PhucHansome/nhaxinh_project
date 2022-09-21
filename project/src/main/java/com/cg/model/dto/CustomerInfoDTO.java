package com.cg.model.dto;


import com.cg.model.CustomerInfo;
import com.cg.model.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerInfoDTO implements Validator {

    private String id;

    @NotBlank(message = "email không được để trống")
    private String userName;

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    private BigDecimal debt;

    private String formatDebt;

    @Valid
    private LocationRegionDTO locationRegion;


    public CustomerInfoDTO(String id, String userName, String fullName, String phone, BigDecimal debt, LocationRegion locationRegion) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.phone = phone;
        this.debt = debt;
        this.locationRegion = locationRegion.toLocationRegionDTO();
    }

    public CustomerInfoDTO(String id, String userName, String fullName, String phone, LocationRegion locationRegion) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.phone = phone;
        this.locationRegion = locationRegion.toLocationRegionDTO();

    }

    public CustomerInfo toCustomerInfo() {
        return  new CustomerInfo()
                .setId(id)
                .setUserName(userName)
                .setFullName(fullName)
                .setPhone(phone)
                .setLocationRegion(locationRegion.toLocationRegion())
                .setDebt(debt)
                ;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerInfoDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerInfoDTO customerInfoDTO = (CustomerInfoDTO) target;

        String fullNameCheck = customerInfoDTO.getFullName();
        String usernameCheck = customerInfoDTO.getUserName();
        String phoneCheck = customerInfoDTO.getPhone();
        if ((fullNameCheck.trim().isEmpty())) {
            errors.rejectValue("fullName", "fullName.isEmpty","Tên khách hàng không được để trống");
            return;
        }

        if ((fullNameCheck.length() < 3 || fullNameCheck.length() > 100)) {
            errors.rejectValue("fullName", "fullName.length", "Tên Từ 3 Đến 100 Ký Tự");
            return;
        }

//        if (!phoneCheck.matches( "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")){
//            errors.rejectValue("phone", "phone.matches", "Số điện thoại bắt đầu bằng số 0 và 10 số");
//
//        }
    }


}
