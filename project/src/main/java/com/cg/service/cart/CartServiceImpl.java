package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.CustomerInfo;
import com.cg.model.LocationRegion;
import com.cg.model.dto.CustomerInfoDTO;
import com.cg.repository.CartRepository;
import com.cg.repository.CustomerInfoRepository;
import com.cg.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepoSitory;

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
        cart.setId(0L);
        List<CustomerInfoDTO> customerInfo = customerInfoRepository.findAllCustomerInfoDTOByDeletedIsFailse();
        for (CustomerInfoDTO customerInfoDTO : customerInfo){
            if (cart.getCustomerInfo().getUserName().equals(customerInfoDTO.getUserName())){
                LocationRegion locationRegion = locationRegionRepository.save(cart.getCustomerInfo().getLocationRegion());
                customerInfoDTO.setLocationRegion(locationRegion.toLocationRegionDTO());
                customerInfoDTO.setFullName(cart.getCustomerInfo().getFullName());
                customerInfoDTO.setPhone(cart.getCustomerInfo().getPhone());
                cart.setCustomerInfo(customerInfoDTO.toCustomerInfo());
                customerInfoRepository.save(customerInfoDTO.toCustomerInfo());
                return cartRepoSitory.save(cart);
            }
        }

        LocationRegion locationRegion = locationRegionRepository.save(cart.getCustomerInfo().getLocationRegion());
        cart.getCustomerInfo().setLocationRegion(locationRegion);
        CustomerInfo customerInfos = customerInfoRepository.save(cart.getCustomerInfo());
        cart.setCustomerInfo(customerInfos);
        return cartRepoSitory.save(cart);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void softDelete(Cart cart) {

    }
}
