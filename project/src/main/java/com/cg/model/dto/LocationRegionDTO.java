package com.cg.model.dto;

import com.cg.model.LocationRegion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class LocationRegionDTO {

    private long id;
    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardId;
    private String wardName;

    @NotBlank(message = "Address is not blank")
    private String address;

    public LocationRegion toLocationRegion() {
        return new LocationRegion()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address);
    }
}
