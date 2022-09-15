package com.cg.service.cartItem;

import com.cg.exception.DataInputException;
import com.cg.model.CartItem;
import com.cg.model.dto.CartItemsDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.repository.CartItemRepository;
import com.cg.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

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
        Optional<ProductDTO> productDTO = productRepository.findProductDTOById(cartItem.getProduct().getId());
        if(productDTO.get().getQuantity().compareTo(BigDecimal.ZERO) < 0){
            productDTO.get().setStatus("Đã Hết Hàng");
            productRepository.save(productDTO.get().toProduct());
        }
        return cartItemRepository.save(cartItem);
    }

    public CartItem saveOp(CartItem cartItem) {
        Optional<CartItemsDTO> cartItem1 = cartItemRepository.getCartItemDTOByCode(cartItem.getUserName(), cartItem.getProduct().getCode());
        Optional<ProductDTO> productDTO = productRepository.findProductDTOById(cartItem.getProduct().getId());
        if(productDTO.get().getQuantity().compareTo(BigDecimal.ZERO) < 0){
            productDTO.get().setStatus("Đã Hết Hàng");
            productRepository.save(productDTO.get().toProduct());
        }
        cartItem.setId(cartItem1.get().getId());
        cartItem.setQuantity(new BigDecimal(String.valueOf(cartItem1.get().getQuantity().add(BigDecimal.valueOf(1)))));
        cartItem.setGrandTotal(new BigDecimal(String.valueOf(cartItem.getQuantity().multiply(cartItem.getPrice()))));
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem saveInDetail(CartItem cartItem) {
        Optional<CartItemsDTO> cartItem1 = cartItemRepository.getCartItemDTOByCode(cartItem.getUserName(), cartItem.getProduct().getCode());
        cartItem.setId(cartItem1.get().getId());
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Optional<CartItemsDTO> getCartItemDTOById(Long id) {
        return cartItemRepository.getCartItemDTOById(id);
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
    public Optional<CartItemsDTO> getCartItemDTOByCode(String userName , String code) {
        return cartItemRepository.getCartItemDTOByCode(userName,code);
    }

    @Override
    public CartItem SaveReduce(CartItem cartItem) {
        if(new BigDecimal(1).equals(cartItem.getQuantity())){
            throw new DataInputException("Số lượng không nhỏ hơn 1 Sản Phẩm");
        }
        cartItem.setQuantity(cartItem.getQuantity().subtract(BigDecimal.valueOf(1)));
        cartItem.setGrandTotal(cartItem.getGrandTotal().subtract(cartItem.getPrice()));
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem SaveIncreasing(CartItem cartItem) {
        if(new BigDecimal(5).equals(cartItem.getQuantity())){
            throw new DataInputException("Số lượng không lớn hơn 5 Sản phẩm!");
        }
        cartItem.setQuantity(cartItem.getQuantity().add(BigDecimal.valueOf(1)));
        cartItem.setGrandTotal(cartItem.getGrandTotal().add(cartItem.getPrice()));
        return cartItemRepository.save(cartItem);
    }
}
