package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.CustomerInfo;
import com.cg.model.LocationRegion;
import com.cg.repository.CartRepoSitory;
import com.cg.repository.CustomerInfoRepository;
import com.cg.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepoSitory cartRepoSitory;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

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
        cart.setId(String.valueOf(0));
        LocationRegion locationRegion = locationRegionRepository.save(cart.getCustomerInfo().getLocationRegion());
        cart.getCustomerInfo().setLocationRegion(locationRegion);
        CustomerInfo customerInfo = customerInfoRepository.save(cart.getCustomerInfo());
        cart.setCustomerInfo(customerInfo);
        return cartRepoSitory.save(cart);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(Cart cart) {

    }
}
