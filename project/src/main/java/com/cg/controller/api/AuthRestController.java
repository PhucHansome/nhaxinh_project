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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    private com.cg.util.AppUtil appUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        userDTO.setImage("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAhFBMVEUBAQH///8AAAD8/PwFBQX5+fkJCQn09PTq6upTU1Pt7e3FxcXx8fGcnJzm5ubDw8Opqana2tqvr68sLCzOzs44ODjX19cmJia2trZ5eXkdHR2FhYVqamoyMjKPj49bW1uZmZlBQUFFRUUVFRVkZGRMTEx8fHwfHx8XFxerq6tfX19ycnJphd7gAAAIu0lEQVR4nO2di3biOAyGsUwIEEK5X0pLC72X93+/kRxaLgMFEpn84eTbPXt29syZzV/JsizbcqVSUlJSUlJSUlJSUlJSUnJNyP299atbg6qVSrVCjFNXvUGJrIk23I7CHVm1YUNYDj+3/qP8jry/MgPON1lFf9UZNa3Z0BpFndlybc9aYSU6byRqdLqhMdZuFPK/yl+m1e1NH39MWUCoyvrGX22WFIimLY1OYfJL250v5EdRxPjK372YtMwpWOvovXhmTCJLx7L1TioUW9bj+2KJpBo76Dw4ab9t4heqFSHmyCc6a4y75qT5dmlNi2BF+pnYp+GFAsVXow/8VIBqIu++0bvUgE6kaX5DGzGZ1xZx1LaJSS5WyGHpGdhR3ew+q18ubJcerKNKgvndPmN2OMUENS9nC07TOec+X5AKJUNbKahzPGMORVoqeKjDmilguOFPaqvIS+jDOapEUUWBZlADs6JMFPXLstATdMACKn/NSlMfh+QFlkL20sho2jAwA4JaERN9as0UvzxADUSiJ22BpgsVa4hidYWmwVlE3sJ+kWGoje1JoSBvZT/oTveJQGuRJgwaa2Tc+8yRFC719RkzQlL4kKZocYrwAyea6ialv6yAFMY+bGh6QAonPiJNEAKNw8iHQmMfYYxIXS825NwUxYrU9qHQmhhHYVOpRLPHBGUJVdVP2hJgFolETT8KmygrKKLASyw1YQ1EYYX8CDRBH0Mhu5InhSATohT0S4VFV+jJSy3M+smPQv4juw3COJxB4qVeqhiEckqTqKUvsA113I0y794fVJi3rC2o7cWGecvagtr6a4sulsKuvg0HYApVt0cdUanwqtBA30vvsBRO1AXK0ai8ZW1BPX2FnZtXOENTqD4QwRQ+6yt8x1I41VcIdhbjXV9hA0uh2sHLDUMshQ3VE1ECSInmBxqqKwzu0RRqu2mIsmWR4EHhCKeCUZFi21h9bTEAKbMJrmCkvvkU4dShiBbTeF7X9tLm12yIsQNM9Oqnos8TBkZqSi/1IAhVbpLsyrM2CCFmDJorS9sQ8BoRwE8p8uWkBmPK8FLQX2NN6yVvhVSt+NmU+dGY+y63KPRyZg9Gobvu5NGGLYhxOPCmD+RGAnW82dCaCcRs8eBJX3IPEUFh32OkeYRQ6OvUHtuwjTAMWaGHPYs1GFsX5DExxbjxTNTwJdBilEypVtPfHU0IIYahZDWR9aNxgBBJXTMTD9syDpBz7PwVw8CDDTlTegMptyn3GtjQkmOdeatzkI8dYHZ8nDukRG9erpM0MEJpRRTWPBzcQzrXxrGmoy3PoqRsDlb4qb25xtF5gaSw5iH7hlj8/iBbKJ1mUy3c2DCoo7X8cl3a1JoOWDmHgSVQ2iQqhhtrhzCT/S/SymyqpdCVusEECjRUUzhC1CdmDLViDdaxxA08ZShJBOo0sAPNlRRarONQG2ipZMI6ZJypyEDs6ii8Q0pntlFqpWTNDFehUjusBaY+l9i0FWKN7SJ1T9qB48OXRjSNUQONpN8q5e8nVIHOiIPsK+E2qo8KpLFdGoNUSQ9CMiVmHYkLrKOz+8ih/UwSLdaFrn1co9aMJvzGW/xuw26aLTm1EVz9Yh/KVswI3sD1icKX9H4qB2iAA2kCD8Wn9JttzTHyZJggcSL90YX3IryMRJSh6LZCDzOOLL3LMY6unyLLQhjjAM0piO5SK/wqiMLUl/MDrE4Rx8ig0IIWu/cgGqW2IVY/k2Nw8p3ahkB7939AL6cfkzumMHwphMKPMK1CE3wUQuEi9RrY4pZKt5CUJr3CAiQ1vDZIf1Qx4KSmAGuLDF1ALPCTZL9IzTSDwgH+4oLX6KknCyNtdQtgw/ssZW+wTgqHIPrOIBB602INcSjNAv4KMettPfz1E2UKNMY28RU+ZdyaaWArrFYyDkN5LKCC88rTf0ixNH2RJiGC35kJMnUytRbkstMxSOGRQIibsccgjZvdA+D9NR5AGkfb3nDP01SzFIM3cKzJW8oRSM5fZj9tEtg5qJ9W9Z55bBDS04drpD/8U/oi2y7hE6CjsgUbejfYQO44byFBZq5lQSF4hgqoEhiGkdFUaM1giBNvEn363D3yn5zzeKSk395yEhq1uxY/WGuCyYLyfQKCRwqPv2Ev8HFb3Ur/KTt5lIkjtxEpN9b6PY/NW4Svx5xiztpB49Bnqy8jzRqCr0/nqFdW6S4c9tWv/x6hM+b/2xWPEq3NN+601FvrHoSdJHQarxVzJL5U6SMOOb54HoNrhfKPVnx/tZgj+j7j1nXUbdF8/qSad1ddu0pcN/rtPE/AA8Jdf/brqmw+jjBzT885nkF97tFV1z8+WnWNp4Y7ZxCY9oo8GdL96IhmctsgN4H8ow1Md+bsqG5I6XJJDW+t2S6i3VB/kC1xi9cosPk56C/yEdG3sqeKgy589ri8nBHbsaZTzHEpIb3dXX16+BP+lujNfVlmO8r8To93bjWTt6wdbLJEzh5xJOWN8x98hwnjWraV1XoCrOsv4HVgr6qvsoQc56ArpRvo3ug+ULpLGsmPph9dPcG+FGsG/VR2dBloHFxrhZQeWT3GaWYOdtBXjbvn18Ca9uuFdUc30XSsyS8DvQh52L3jvvlsgeygTz6azPmk/n1+KcetccHm99NYG58bbvgn8YiVg56H2+o4ZzBykrDMdHArR1rLM1IcWSWFBYmh+wQmfDjpqOyiGnvxeWHN/O9wI0P12eg/z3w1+NP/bEcoiejUBgDr+LTIzPj+R7iRe9hFtqDAX/96PNwQ3dfRFroXE9jm8adb1Jp15cyxW4zSLLfoBnQEx1qduycAbkEhL/gOd9dQbLGaOwffZZcOSLdgQcFyPP1fY+1GwkxC9/9+7pxvF30q3EJeHdhTyL+OsKraWWAh0f7hDfL6qFEO/PdgctYLWXjsXxHL0OoBE7t/RYz6YXFXFIewwXhHYpVmBV4zHSK0O31eSbGPMwh77+6xwvZtCZQ6+FrhP8ViiI3FgssIAAAAAElFTkSuQmCC");

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<UserDTO> optUser = userService.findUserDTOByUsername(userDTO.getUsername());

        if (optUser.isPresent()) {
            throw new EmailExistsException("Email này đã tồn tại");
        }

        Optional<Role> optRole = roleService.findById(userDTO.getRole().getId());

        Boolean existRole = roleService.existById(userDTO.getRole().getId());

        if (!existRole) {
            throw new DataInputException("Invalid account role");
        }


        try {

            userDTO.setStatus("Active");

//            User newUser =  userService.save(userDTO.toUser());

//            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            return new ResponseEntity<>( HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @PostMapping("/registerdefaulimage")
    public ResponseEntity<?> registerDefautlimage(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        userDTO.setImage("data:image/png;base64,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");

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

            userDTO.setStatus("Active");

            User newUser =  userService.save(userDTO.toUser());

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);

//            return new ResponseEntity<>( HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO user) {
        Optional<UserDTO> userDTO= userService.findUserDTOByUsername(user.getUsername());

        if(userDTO.get().getStatus().equals("Block")){
           throw new EmailExistsException("Tài khoản này đã bị block!");
        }

        if(userDTO.get().getStatus().equals("Active")){
            Authentication authentication  =  authenticationManager.authenticate(
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
                    .maxAge(60 * 1000)
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

        throw new EmailExistsException("Tài khoản này không tồn tại");

    }
}
