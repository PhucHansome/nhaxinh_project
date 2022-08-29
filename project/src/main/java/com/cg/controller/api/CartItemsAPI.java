package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.CartItem;
import com.cg.model.dto.CartItemsDTO;
import com.cg.service.cartItem.CartItemService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemsAPI {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping()
    public ResponseEntity<?> doCreate(@RequestBody CartItemsDTO cartItemsDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            try {
                Optional<CartItemsDTO> cartItemDTO = cartItemService.getCartItemDTOById(cartItemsDTO.getUserName(), cartItemsDTO.getProduct().getCode());
                if (cartItemDTO.isPresent()) {
                    if (cartItemDTO.get().getProduct().getFiles().contains(cartItemsDTO.getProduct())) {
                        // cho nó nhảy vào catch để xử lý thêm product vào cartItem;
                    }
                }
                CartItem cartItemsDTO1 = cartItemService.save(cartItemsDTO.toCartItem());
                return new ResponseEntity<>(cartItemsDTO1.toCartItemDTO(), HttpStatus.CREATED);
            } catch (Exception e) {
                CartItem cartItemsDTO1 = cartItemService.saveOp(cartItemsDTO.toCartItem());
                return new ResponseEntity<>(cartItemsDTO1.toCartItemDTO(), HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            throw new DataInputException("Không thêm vào giỏ hàng thành công");
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getCartByUserName(@PathVariable String userName) {
        try {
            List<CartItemsDTO> cartItemsDTO = cartItemService.findCartItemDTOById(userName);
            return new ResponseEntity<>(cartItemsDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Out");
        }
    }
}
