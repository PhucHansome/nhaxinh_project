package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.dto.CartDTO;
import com.cg.model.dto.CartItemsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT NEW com.cg.model.dto.CartDTO (" +
            "c.id, " +
            "c.content, " +
            "c.user , " +
            "c.customerInfo" +
            " )  " +
            "FROM Cart c  WHERE c.customerInfo.id = ?1 ")
    List<CartDTO> getCartItemDTOByIdCustomerInfo(String id);


    @Query("SELECT NEW com.cg.model.dto.CartDTO (" +
            "c.id, " +
            "c.content, " +
            "c.user , " +
            "c.customerInfo" +
            " )  " +
            "FROM Cart c  WHERE c.customerInfo.id = ?1 ")
    Optional<CartDTO> findCartItemDTOByIdCustomerInfo(String id);



}
