package com.cg.controller.api;


import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.CartDTO;
import com.cg.model.dto.CartItemsDTO;
import com.cg.service.cart.CartService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartAPI {
    @Autowired
    private CartService cartService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@RequestBody CartDTO cartDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Cart createCart = cartService.save(cartDTO.toCart());
        return new ResponseEntity<> (createCart.toCartDTO(), HttpStatus.CREATED);
    }

}
