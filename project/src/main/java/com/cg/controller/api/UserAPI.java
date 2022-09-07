package com.cg.controller.api;

import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    @Autowired
    private IUserService userService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName){
        Optional<UserDTO> user = userService.findUserDTOByUsername(userName);
        if (user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        throw new RuntimeException("khong co user");
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<?> getAllElementUserByUserName(@PathVariable String userName){
        Optional<User> user = userService.findByUsername(userName);
        if (user.isPresent()){
            return new ResponseEntity<>(user.get().toUserDTO(), HttpStatus.OK);
        }
        throw new RuntimeException("khong co user");
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassWord (@RequestBody UserDTO userDTO,BindingResult bindingResult ){
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        userService.save(userDTO.toUser());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
