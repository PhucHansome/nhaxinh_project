package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.dto.CartDTO;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface CartService extends IGeneralService<Cart> {
    Optional<CartDTO> findCartItemDTOByIdCustomerInfo(String id);
}
