package com.cg.service.cartItem;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.CartItemsDTO;
import com.cg.repository.CartItemRepository;
import com.cg.repository.CartRepoSitory;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public Boolean existById(Long id) {
        return cartItemRepository.existsById(id);
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public CartItem getById(Long id) {
        return null;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem saveOp(CartItem cartItem) {
        Optional<CartItemsDTO> cartItem1 = cartItemRepository.getCartItemDTOById(cartItem.getUserName(), cartItem.getProduct().getCode());
        cartItem.setId(cartItem1.get().getId());
        cartItem.setQuantity(new BigDecimal(String.valueOf(cartItem1.get().getQuantity().add(BigDecimal.valueOf(1)))));
        cartItem.setGrandTotal(new BigDecimal(String.valueOf(cartItem.getQuantity().multiply(cartItem.getPrice()))));
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public void softDelete(CartItem cartItem) {

    }

    @Override
    public List<CartItemsDTO> findCartItemDTOById(String userName) {
        return cartItemRepository.findCartItemDTOById(userName);
    }

    @Override
    public Optional<CartItemsDTO> getCartItemDTOById(String userName , String code) {
        return cartItemRepository.getCartItemDTOById(userName,code);
    }
}
