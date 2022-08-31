package com.cg.repository;

import com.cg.model.CartItem;
import com.cg.model.dto.CartItemsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("SELECT NEW com.cg.model.dto.CartItemsDTO(" +
            "c.id, " +
            "c.userName, " +
            "c.product , " +
            "c.price ," +
            "c.quantity, " +
            "c.grandTotal" +
            " )  " +
            "FROM CartItem c  WHERE c.userName Like ?1   And c.deleted = false ")
    List<CartItemsDTO> findCartItemDTOById(String userName);

    @Query("SELECT NEW com.cg.model.dto.CartItemsDTO(" +
            "c.id, " +
            "c.userName, " +
            "c.product , " +
            "c.price ," +
            "c.quantity, " +
            "c.grandTotal " +
            " )  " +

            "FROM CartItem c  WHERE c.userName Like ?1 And c.product.code like ?2  And c.deleted = false ")
    Optional<CartItemsDTO> getCartItemDTOByCode(String userName, String code);


    @Query("SELECT NEW com.cg.model.dto.CartItemsDTO(" +
            "c.id, " +
            "c.userName, " +
            "c.product , " +
            "c.price ," +
            "c.quantity, " +
            "c.grandTotal " +
            " )  " +
            "FROM CartItem c  WHERE c.id = ?1  And c.deleted = false ")
    Optional<CartItemsDTO> getCartItemDTOById(Long id);
}
