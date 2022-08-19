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
        }
        else {
            username = principal.toString();
        }

        return username;
    }

    @GetMapping("/")
    public ModelAndView getHome(){
        return new ModelAndView("/customerView/homepage");
    }

    @GetMapping("/detail")
    public ModelAndView getDetail(){
        return new ModelAndView("/customerView/detail");
    }

    @GetMapping("/login")
    public ModelAndView getLogin(){
        return new ModelAndView("/customerView/dangnhap_dangky");
    }

    @GetMapping("/search")
    public ModelAndView getSearch(){
        return new ModelAndView("/customerView/Search");
    }

    @GetMapping("/HomeDashboard")
    public ModelAndView getDashboard(){
        return new ModelAndView("/dashboard/product/home");
    }
}
