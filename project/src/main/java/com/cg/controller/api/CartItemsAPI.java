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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-item")
public class CartItemsAPI {
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getCartByUserName(@PathVariable String userName) {
        try {
            List<CartItemsDTO> cartItemsDTO = cartItemService.findCartItemDTOById(userName);
            return new ResponseEntity<>(cartItemsDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Không lấy được danh sách đơn hàng");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> GetCartById(@PathVariable Long id) {
        try {
            Optional<CartItemsDTO> cartItemsDTO = cartItemService.getCartItemDTOById(id);
            return new ResponseEntity<>(cartItemsDTO.get(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Không tkấy được cart item", HttpStatus.NO_CONTENT);
        }


    }

    @PostMapping()
    public ResponseEntity<?> doCreate(@RequestBody CartItemsDTO cartItemsDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            try {
                Optional<CartItemsDTO> cartItemDTO = cartItemService.getCartItemDTOByCode(cartItemsDTO.getUserName(), cartItemsDTO.getProduct().getCode());
                if (cartItemDTO.isPresent()) {
                    if (cartItemDTO.get().getProduct().getFiles().contains(cartItemsDTO.getProduct())) {
                        // cho nó nhảy vào catch để xử lý thêm product vào cartItem;
                    }
                }
                return new ResponseEntity<>(cartItemService.save(cartItemsDTO.toCartItem()).toCartItemDTO(), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(cartItemService.saveOp(cartItemsDTO.toCartItem()).toCartItemDTO(), HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            throw new DataInputException("không đủ số lượng sản phẩm để thêm vào giỏ hàng!!");
        }
    }

    @PostMapping("/creat-cart-in-detail")
    public ResponseEntity<?> doCreateCartItem(@RequestBody CartItemsDTO cartItemsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            try {
                Optional<CartItemsDTO> cartItemDTO = cartItemService.getCartItemDTOByCode(cartItemsDTO.getUserName(), cartItemsDTO.getProduct().getCode());
                if (cartItemDTO.isPresent()) {
                    if (cartItemDTO.get().getProduct().getFiles().contains(cartItemsDTO.getProduct())) {
                        // cho nó nhảy vào catch để xử lý thêm product vào cartItem;
                    }
                }

                CartItem cartItemsDTO1 = cartItemService.save(cartItemsDTO.toCartItem());
                return new ResponseEntity<>(cartItemsDTO1.toCartItemDTO(), HttpStatus.CREATED);
            } catch (Exception e) {
                CartItem cartItemsDTO1 = cartItemService.saveInDetail(cartItemsDTO.toCartItem());
                return new ResponseEntity<>(cartItemsDTO1.toCartItemDTO(), HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            throw new DataInputException("Không thêm vào giỏ hàng thành công");
        }
    }



    @PutMapping("/input-change")
    public ResponseEntity<?> inputChange(@RequestBody CartItem cartItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(cartItemService.saveChangeInput(cartItem).toCartItemDTO(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new DataInputException("Sản phẩm không đủ để thêm nữa!");
        }
    }

    @PutMapping("/increasing")
    public ResponseEntity<?> increasingCart(@RequestBody CartItem cartItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(cartItemService.SaveIncreasing(cartItem).toCartItemDTO(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new DataInputException("Sản phẩm không đủ để thêm nữa!");
        }
    }

    @PutMapping("/reduce")
    public ResponseEntity<?> ReduceCart(@RequestBody CartItem cartItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        try {
            return new ResponseEntity<>(cartItemService.SaveReduce(cartItem).toCartItemDTO(), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Không thể Giảm số lượng sản phẩm", HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        cartItemService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/createCartAndCartItem")
    public ResponseEntity<?> doCreateCartAndCartItem(@RequestBody CartItemsDTO cartItemsDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        CartItem cartItemsDTO1 = cartItemService.saveCartItemAndCart(cartItemsDTO.toCartItem());
        return new ResponseEntity<>(cartItemsDTO1.toCartItemDTO(), HttpStatus.CREATED);
    }
}
