package com.cg.controller;


import com.cg.model.dto.*;
import com.cg.service.Tag.TagService;
import com.cg.service.customerInfo.ICustomerInfoService;
import com.cg.service.product.ProductService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private IUserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ICustomerInfoService customerInfoService;

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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/homepage/homepage");
        String email = getPrincipal();
        if (email == "anonymousUser") {
            email = "Đăng nhập";
            modelAndView.addObject("userDTO", email);
        }
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/detail")
    public ModelAndView getDetail() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/detail/detail");
        String email = getPrincipal();
        if (email == "anonymousUser") {
            email = "Đăng nhập";
            modelAndView.addObject("userDTO", email);
        }
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/login")
    public String getLogin() {
        String email = getPrincipal();
        Optional<UserDTO> userDTOOptional = userService.findUserDTOByUsername(email);
        if (userDTOOptional.isPresent()) {
            return "redirect:/";
        }
        return "/customerView/dangnhap_dangky/dangnhap_dangky";
    }

    @GetMapping("/search")
    public ModelAndView getSearch() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/search/Search");
        String email = getPrincipal();
        if (email == "anonymousUser") {
            email = "Đăng nhập";
            modelAndView.addObject("userDTO", email);
        }
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/cart_details")
    public ModelAndView getCartDetails() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/chitiet_giohang/chitiet_giohang");
        String email = getPrincipal();
        if (email == "anonymousUser") {
            email = "Đăng nhập";
            modelAndView.addObject("userDTO", email);
        }
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/cart")
    public ModelAndView getCart() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/giohang/cart");
        String email = getPrincipal();
        if (email == "anonymousUser") {
            email = "Đăng nhập";
            modelAndView.addObject("userDTO", email);
        }
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView goDetailProduct(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/detail/detail");
        Optional<ProductDTO> productDTOOptional = productService.findProductDTOById(id);
        Optional<TagDTO> tagDTO = tagService.findTagDTOByProductId(id);
        modelAndView.addObject("product", productDTOOptional.get());
        modelAndView.addObject("tag", tagDTO.get());
        String email = getPrincipal();
        if (email == "anonymousUser") {
            email = "Đăng nhập";
            modelAndView.addObject("userDTO", email);
        }
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/account")
    public ModelAndView getAccount() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/myaccount/account");
        String email = getPrincipal();
        if (email == "anonymousUser") {
            email = "Đăng nhập";
            modelAndView.addObject("userDTO", email);
        }
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    //==dashBoard===//



    @GetMapping("/home-dashboard")
    public ModelAndView getDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/home/home");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/product-dashboard")
    public ModelAndView getProductDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/productDashboard/product");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/create-product-dashboard")
    public ModelAndView getCreateProductDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/productDashboard/create-product");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/edit-product-dashboard")
    public ModelAndView getEditProductDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/productDashboard/edit-product");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/detail-product-dashboard")
    public ModelAndView getDetailProductDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/productDashboard/detail-product");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/user-dashboard")
    public ModelAndView getUserDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/userDashboard/user");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @RequestMapping("/")
    public String detailCustomerinfo(){
        return "/dashboard/userDashboard/user";
    }

    @RequestMapping("/redirect")
    public RedirectView redirect(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
        System.out.println(name);
        redirectAttributes.addAttribute("name", name);
        return new RedirectView("/dashboard/userDashboard/detail-user");
    }

    @RequestMapping("/detail-user-dashboard")
    public String page2(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name.toUpperCase());
        return "/detail-user-dashboard";
    }

    @GetMapping("/create-user-dashboard")
    public ModelAndView getCreateUserDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/userDashboard/create-user");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }


    @GetMapping("/edit-user-dashboard")
    public ModelAndView getEditUserDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/userDashboard/edit-user");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/detail-user-dashboard")
    public ModelAndView getDetailUserDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/userDashboard/detail-user");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);

        return modelAndView;
    }

    @GetMapping("/order-dashboard")
    public ModelAndView getOrderDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/orderDashboard/order");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

    @GetMapping("/login_admin")
    public String getLoginAdmin() {
        String email = getPrincipal();
        Optional<UserDTO> userDTOOptional = userService.findUserDTOByUsername(email);
        if (userDTOOptional.isPresent()) {
            return "redirect:/home-dashboard";
        }
        return "/dashboard/loginDashboard/login";
    }

    @GetMapping("/api/customerInfo/edit/{id}")
    public ModelAndView showCustomerInfoDetail(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/userDashboard/detail-user");
        Optional<CustomerInfoDTO> customerInfo = customerInfoService.findUserDTOById(id);
        modelAndView.addObject("locationRegion", customerInfo.get().getLocationRegion());
        modelAndView.addObject("customerInfo", customerInfo.get().toCustomerInfo());
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        return modelAndView;
    }

}
