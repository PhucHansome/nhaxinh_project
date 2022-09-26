package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.JwtResponse;
import com.cg.model.Role;
import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.jwt.JwtService;
import com.cg.service.role.IRoleService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private AppUtil appUtils;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/register")
    public ResponseEntity<?> registerRegister(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        userDTO.setStatus("Active");

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<UserDTO> optUser = userService.findUserDTOByUsername(userDTO.getUsername());

        if (optUser.isPresent()) {
            throw new EmailExistsException("Email này đã tồn tại");
        }
//
        Optional<Role> optRole = roleService.findById(userDTO.getRole().getId());

        Boolean existRole = roleService.existById(userDTO.getRole().getId());

        if (!existRole) {
            throw new DataInputException("Invalid account role");
        }

        try {


            User newUser = null;
            try {
                newUser = userService.save(userDTO.toUser());
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);

//            return new ResponseEntity<>( HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName, BindingResult bindingResult){
        Optional<User> user = userService.findByUsername(userName);
        if (user.isPresent()){
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
        throw new RuntimeException("khong co user");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO user,  BindingResult bindingResult) {
        System.out.println("Email không hợp lệ");
        //System.out.println(messageSource.getMessage("model.userdto.email.invalid",null, new Locale("vi")));
        System.out.println(messageSource.getMessage("model.userdto.email.invalid",null, new Locale("vi")));
        try {
            Optional<UserDTO> userDTO = userService.findUserDTOByUserNameByStatus(user.getUsername());
            if (userDTO.get().getStatus().contains("Block")){
                throw new DataInputException("Tài khoản của bạn đã bị khóa");
            }

            if(!userDTO.isPresent()){
                throw  new DataInputException("Mật khẩu hoặc tài khoản không đúng vui lòng nhập lại");
            }
        }catch (Exception e){
            throw  new DataInputException("Email không tồn tại");
        }


        if (bindingResult.hasErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.getByUsername(user.getUsername());

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                currentUser.getId(),
                userDetails.getUsername(),
                currentUser.getUsername(),
                userDetails.getAuthorities()
        );


        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(100 * 1000)
                .domain("localhost")
//                .domain("ajax-bank-location-jwt.herokuapp.com")
//                .domain("bank-transaction.azurewebsites.net")
                .build();

        System.out.println(jwtResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);
    }
}
