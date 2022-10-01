package com.cg.service.cartItem;

import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.*;
import com.cg.repository.*;
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

    @Autowired
    CustomerInfoRepository customerInfoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;


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
            throw new DataInputException("Đã hết hàng!");
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
        if(productDTO.get().getQuantity().compareTo(cartItem.getQuantity()) < 0){
            throw new DataInputException("Đã hết hàng!");
        }
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
        Optional<ProductDTO> productDTO = productRepository.findProductDTOById(cartItem.getProduct().getId());

        cartItem.setQuantity(cartItem.getQuantity().add(BigDecimal.valueOf(1)));
        cartItem.setGrandTotal(cartItem.getGrandTotal().add(cartItem.getPrice()));
        if(productDTO.get().getQuantity().compareTo(cartItem.getQuantity()) < 0){
            throw new DataInputException("Đã hết hàng!");
        }
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem saveCartItemAndCart(CartItem cartItem) {
        Optional<CartItemsDTO> cartItem1 = cartItemRepository.getCartItemDTOByCode(cartItem.getUserName(),cartItem.getProduct().getCode());
        if(cartItem1.isPresent()){
            cartItem1.get().setQuantity(cartItem1.get().getQuantity().add(BigDecimal.valueOf(1)));
            cartItem1.get().setGrandTotal(cartItem1.get().getPrice().multiply(cartItem1.get().getQuantity()));
            CartItem cartItem2 = cartItemRepository.save(cartItem1.get().toCartItem());
            Optional<CustomerInfoDTO>customerInfoDTO1 = customerInfoRepository.findUserDTOByUserName(cartItem2.getUserName());
            Optional<UserDTO> userDTOOptional = userRepository.findUserDTOByUserNameByStatus(cartItem2.getUserName());
            Optional<CartDTO> cartDTO = cartRepository.findCartItemDTOByIdCustomerInfo(customerInfoDTO1.get().getId());
            if(cartDTO.isPresent()){
                return cartItem2;
            }
            Cart cart= new Cart();
            cart.setCustomerInfo(customerInfoDTO1.get().toCustomerInfo());
            cart.setContent("null");
            cart.setId(0L);
            cart.setUser(userDTOOptional.get().toUser());
            cartRepository.save(cart);
            return cartItem2;
        }
        cartItem.setId(0L);
        CartItem cartItemNew = cartItemRepository.save(cartItem);
        Optional<CustomerInfoDTO>customerInfoDTO = customerInfoRepository.findUserDTOByUserName(cartItemNew.getUserName());
        Optional<UserDTO> userDTOOptional = userRepository.findUserDTOByUserNameByStatus(cartItemNew.getUserName());
        Optional<CartDTO> cartDTO = cartRepository.findCartItemDTOByIdCustomerInfo(customerInfoDTO.get().getId());
        if(cartDTO.isPresent()){
            return cartItemNew;
        }
        Cart cart= new Cart();
        cart.setCustomerInfo(customerInfoDTO.get().toCustomerInfo());
        cart.setContent("null");
        cart.setId(0L);
        cart.setUser(userDTOOptional.get().toUser());
        cartRepository.save(cart);
        return cartItemNew;
    }

    @Override
    public CartItem saveChangeInput(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
}
