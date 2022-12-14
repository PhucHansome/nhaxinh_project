package com.cg.repository;

import com.cg.model.CustomerInfo;
import com.cg.model.dto.CustomerInfoDTO;
import com.cg.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo,Long> {

    @Query("SELECT NEW com.cg.model.dto.CustomerInfoDTO (c.id," +
            " c.userName," +
            "c.fullName, " +
            "c.phone," +
            "c.debt," +
            "c.locationRegion)" +
            "  FROM CustomerInfo c" +
            "  WHERE c.deleted = false ")
    List<CustomerInfoDTO> findAllCustomerInfoDTOByDeletedIsFailse();
    Boolean existsByUserName(String username);
    Boolean existsByPhone(String phone);

    @Query("SELECT NEW com.cg.model.dto.CustomerInfoDTO (c.id," +
            " c.userName," +
            "c.fullName ," +
            "c.phone," +
            "c.debt," +
            "c.locationRegion) " +
            "FROM CustomerInfo c WHERE c.id = ?1")
    Optional<CustomerInfoDTO> findUserDTOById(String id);

    @Query("SELECT NEW com.cg.model.dto.CustomerInfoDTO (c.id," +
            " c.userName," +
            "c.fullName," +
            "c.phone," +
            "c.debt," +
            "c.locationRegion) " +
            "FROM CustomerInfo c WHERE c.userName = ?1")
    Optional<CustomerInfoDTO> findUserDTOByUserName(String userName);

}