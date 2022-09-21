package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    @Autowired
    private IUserService userService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping()
    public ResponseEntity<?> getAllUser(){
            return new ResponseEntity<>(userService.findAllUserDTOByDeletedIsFailse(), HttpStatus.OK);
    }

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

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<UserDTO> user = userService.findUserDTOById(id);
        if (user.isPresent()){
            return new ResponseEntity<>(user.get().toUser(), HttpStatus.OK);
        }
        throw new RuntimeException("khong co user");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassWord (@RequestBody UserDTO userDTO,BindingResult bindingResult ) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        userService.save(userDTO.toUser());

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/forgot-a-password")
    public ResponseEntity<?> forgotAPassword(@RequestBody UserDTO userDTO, BindingResult bindingResult) throws MessagingException, UnsupportedEncodingException {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        userService.saveAndMail(userDTO.toUser());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/block")
    public ResponseEntity<?> Block(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        return new ResponseEntity<>(userService.Block(userDTO.toUser()).toUserDTO(),HttpStatus.ACCEPTED);

    }

    @PutMapping("/active")
    public ResponseEntity<?> Active(@RequestBody UserDTO userDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        return new ResponseEntity<>(userService.Active(userDTO.toUser()).toUserDTO(),HttpStatus.ACCEPTED);

    }
}
