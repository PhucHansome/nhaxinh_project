package com.cg.service.cartItem;

import com.cg.model.CartItem;
import com.cg.model.dto.CartItemsDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface CartItemService extends IGeneralService<CartItem> {

    List<CartItemsDTO> findCartItemDTOById(String userName);

    Optional<CartItemsDTO> getCartItemDTOByCode(String userName, String code);

    CartItem saveOp(CartItem cartItem);

    CartItem saveInDetail(CartItem cartItem);

    Optional<CartItemsDTO> getCartItemDTOById(Long id);

    CartItem SaveReduce(CartItem cartItem);

    CartItem SaveIncreasing(CartItem cartItem);
}
