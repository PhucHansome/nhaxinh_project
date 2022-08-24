package com.cg.repository;

import com.cg.model.CustomerInfo;
import com.cg.model.dto.CustomerInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo,Long> {

    @Query("SELECT NEW com.cg.model.dto.CustomerInfoDTO (c.id, c.userName, c.phone,c.debt,c.locationRegion,c.createdAt)  FROM CustomerInfo c  WHERE c.deleted = false ")
    List<CustomerInfoDTO> findAllCustomerInfoDTOByDeletedIsFailse();

}