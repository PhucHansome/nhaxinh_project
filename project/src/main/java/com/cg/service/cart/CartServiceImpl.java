package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.repository.CartRepoSitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepoSitory cartRepoSitory;

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart getById(Long id) {
        return null;
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepoSitory.save(cart);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(Cart cart) {

    }
}
