package com.cg.controller;


import com.cg.model.CustomerInfo;
import com.cg.model.OrderDetail;
import com.cg.model.dto.*;
import com.cg.service.Tag.TagService;
import com.cg.service.category.CategoryService;
import com.cg.service.customerInfo.ICustomerInfoService;
import com.cg.service.order.OrderService;
import com.cg.service.orderdetail.OrderDetailService;
import com.cg.service.page.PageProductService;
import com.cg.service.product.ProductService;
import com.cg.service.productColor.ProductColorService;
import com.cg.service.productmedia.ProductMediaService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.function.ServerResponse.status;

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

    @Autowired
    private ProductMediaService productMediaService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductColorService productColorService;

    @Autowired
    private PageProductService pageProductService;

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

    @GetMapping("/login")
    public String getLogin() {
        String email = getPrincipal();
        Optional<UserDTO> userDTOOptional = userService.findUserDTOByUsername(email);
        if (userDTOOptional.isPresent()) {
            return "redirect:/";
        }
        return "/customerView/dangnhap_dangky/dangnhap_dangky";
    }

    @GetMapping("/search/{query}")
    public ModelAndView getSearchByTitle(@PathVariable String query) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/search/Search");
        String email = getPrincipal();
        if (email == "anonymousUser") {
            email = "Đăng nhập";
            modelAndView.addObject("userDTO", email);
        }
        modelAndView.addObject("userDTO", email);

        modelAndView.addObject("query", query);
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
    public ModelAndView goDetailProduct(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/customerView/detail/detail");
        Optional<ProductDTO> productDTOOptional = productService.findProductDTOById(id);
        Optional<TagDTO> tagDTO = tagService.findTagDTOByProductId(id);
        List<ProductMediaDTO> productMediaDTOList = productMediaService.findAllByProductIdOrderByTsAsc(id);
        modelAndView.addObject("product", productDTOOptional.get());
        modelAndView.addObject("tag", tagDTO.get());
        modelAndView.addObject("image", productMediaDTOList);
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

        List<OrderDTO> orderDTOS = orderService.findOrderDTOByUserName(email);
        modelAndView.addObject("orderList", orderDTOS);
        BigDecimal sum = BigDecimal.valueOf(0);
        for (OrderDTO orderDTO : orderDTOS) {
            sum = orderDTO.getGrandTotal().add(sum);
        }
        modelAndView.addObject("Total", sum);
        return modelAndView;
    }

    //==dashBoard===//


    @GetMapping("/home-dashboard")
    public ModelAndView getDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/home/home");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        List<OrderDetail> orderDetails = orderDetailService.findAll();
        modelAndView.addObject("order", orderDetails.size());
        List<CustomerInfoDTO> customerInfoDTOS = customerInfoService.findAllCustomerInfoDTOByDeletedIsFailse();
        modelAndView.addObject("customer", customerInfoDTOS.size());
        BigDecimal sum = BigDecimal.valueOf(0);
        List<OrderDetailDTO> orderDetails1 = orderDetailService.findAllOrderDetailByStatusWait("Đơn hàng đã duyệt");
        for (OrderDetailDTO orderDetail : orderDetails1) {
            sum = orderDetail.getGrandTotal().add(sum);
        }
        List<ProductDTO> productDTOList = productService.findAllProductDTONoImage();
        modelAndView.addObject("totalProductSize", productDTOList.size());
        modelAndView.addObject("totalOrder", sum);


        return modelAndView;
    }

    @GetMapping("/product-dashboard")
//    @GetMapping("/product-dashboard/page/{pageNo}")
//    public ModelAndView getProductDashboard(@PathVariable int pageNo,Pageable pageable) {
    public ModelAndView getProductDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/productDashboard/product");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        List<ProductDTO> productDTOList = productService.findAllProductDTONoImage();
        modelAndView.addObject("productList", productDTOList);
//        Page<ProductDTO> productDTOPage = pageProductService.findAllProductDTONoImage(PageRequest.of((pageNo -1), 2));
//        modelAndView.addObject("productList", productDTOPage);
//        modelAndView.addObject("totalPage",productDTOPage.getTotalPages());
//        modelAndView.addObject("totalItem",productDTOPage.getTotalElements());
//        modelAndView.addObject( "currentPage",pageNo);

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

    @GetMapping("/category-product-dashboard")
    public ModelAndView getCategoryProductDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/categoryDashboard/category-product");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        List<CategoryDTO> categoryDTOS = categoryService.findAllCategoryDTO();
        modelAndView.addObject("category", categoryDTOS);
        return modelAndView;
    }

    @GetMapping("/color-product-dashboard")
    public ModelAndView getColorProductDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/colorDashboard/color-product");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        List<ProductColorDTO> productColorDTOS = productColorService.findAllProductColorDTO();
        modelAndView.addObject("color", productColorDTOS);
        return modelAndView;
    }

    @GetMapping("/detail-product-dashboard/{productId}")
    public ModelAndView getDetailProductDashboard(@PathVariable String productId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/productDashboard/detail-product");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        Optional<ProductDTO> productDTOList = productService.findProductDTOById(productId);
        modelAndView.addObject("product", productDTOList.get().toProduct());
        Optional<TagDTO> tagDTO = tagService.findTagDTOByProductId(productId);
        modelAndView.addObject("tag", tagDTO.get().toTag());
        List<ProductMediaDTO> productMediaDTO = productMediaService.findAllByProductIdOrderByTsAsc(productId);
        modelAndView.addObject("productMedia", productMediaDTO);
        return modelAndView;
    }

    @GetMapping("/user-dashboard")
    public ModelAndView getUserDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/userDashboard/user");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        List<CustomerInfoDTO> customerInfoDTOS = customerInfoService.findAllCustomerInfoDTOByDeletedIsFailse();
        modelAndView.addObject("customerList", customerInfoDTOS);
        return modelAndView;

    }

    @RequestMapping("/")
    public String detailCustomerinfo() {
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


    @GetMapping("/order-dashboard")
    public ModelAndView getOrderDashboard() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/orderDashboard/order");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        List<OrderDetail> orderDetailDTOS = orderDetailService.findAll();
        modelAndView.addObject("orderDetail", orderDetailDTOS);
        return modelAndView;
    }

    @GetMapping("/order-dashboard/detail/{id}")
    public ModelAndView getOrderDetailDashboard(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/dashboard/orderDashboard/detail-order");
        String email = getPrincipal();
        modelAndView.addObject("userDTO", email);
        modelAndView.addObject("idOrder", id);
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
