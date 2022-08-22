package com.cg.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class HomeController {
    private String getPrincipal() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    //==CustomerView===//

    @GetMapping("/")
    public ModelAndView getHome() {
        return new ModelAndView("/customerView/homepage/homepage");
    }

    @GetMapping("/detail")
    public ModelAndView getDetail() {
        return new ModelAndView("/customerView/detail/detail");
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        return new ModelAndView("/customerView/dangnhap_dangky/dangnhap_dangky");
    }

    @GetMapping("/search")
    public ModelAndView getSearch() { return new ModelAndView("/customerView/search/Search");}

    @GetMapping("/cart_details")
    public ModelAndView getCartDetails() { return new ModelAndView("/customerView/chitiet_giohang/chitiet_giohang");}

    @GetMapping("/cart")
    public ModelAndView getCart() { return new ModelAndView("/customerView/giohang/cart");}

    //==dashBoard===//

    @GetMapping("/home-dashboard")
    public ModelAndView getDashboard() {return new ModelAndView("/dashboard/home/home");}

    @GetMapping("/login_admin")
    public ModelAndView getLoginAdmin() {return new ModelAndView("/dashboard/loginDashboard/login");}
}
