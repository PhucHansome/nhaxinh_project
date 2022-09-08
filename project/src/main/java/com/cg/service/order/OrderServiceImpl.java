package com.cg.service.order;

import com.cg.model.Cart;
import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.dto.*;
import com.cg.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CartRepository cartRepoSitory;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Boolean existById(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.getById(id);
    }

    @Override
    public Order save(Order order) throws MessagingException, UnsupportedEncodingException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(0L);
        orderDetail.setStatusOrderDetail("abc");
        orderDetail.setCreatedAt(new Date());
        orderDetailRepository.save(orderDetail);
        BigDecimal sum = BigDecimal.valueOf(0);
        Optional<OrderDetailDTO> orderNew = orderDetailRepository.findOrderDetailNew("abc");
        List<CartItemsDTO> cartItemsDTOList = cartItemRepository.findCartItemDTOById(order.getCustomerInfo().getUserName());
        for (CartItemsDTO cartItemsDTO : cartItemsDTOList) {
            Optional<ProductDTO> productDTO = productRepository.findProductDTOById(cartItemsDTO.getProduct().getId());
            productDTO.get().setQuantity(productDTO.get().getQuantity().subtract(cartItemsDTO.getQuantity()));
            productRepository.save(productDTO.get().toProduct());
            order.setId(0L);
            order.setOrderDetail(orderNew.get().toOrderDetail());
            order.setQuantity(cartItemsDTO.getQuantity());
            order.setProductCode(cartItemsDTO.getProduct().getCode());
            order.setProductTitle(cartItemsDTO.getProduct().getTitle());
            order.setProductImage(cartItemsDTO.getProduct().getImage());
            order.setGrandTotal(cartItemsDTO.getGrandTotal());
            sum = order.getGrandTotal().add(sum);
            cartItemRepository.deleteById(cartItemsDTO.getId());
            orderRepository.save(order);
        }

        List<CartDTO> cartDTOList = cartRepoSitory.getCartItemDTOByIdCustomerInfo(order.getCustomerInfo().getId());
        for (CartDTO cartDTO : cartDTOList) {
            if (cartDTO.toCart().getCustomerInfo().getId().equals(order.getCustomerInfo().getId())) {
                cartRepoSitory.deleteById(cartDTO.getId());
            }
        }
        List<OrderDTO> orderDTOS = orderRepository.findOrderDTOByUserName(order.getCustomerInfo().getUserName());
        for (OrderDTO order1 : orderDTOS) {
            orderNew.get().setStatusOrderDetail(order1.getStatusOrder());
            orderNew.get().setFullName(order1.getCustomerInfo().getFullName());
        }
        orderNew.get().setGrandTotal(sum);
        orderDetailRepository.save(orderNew.get().toOrderDetail());
        final String fromEmail = "nhaxinhprj@gmail.com";
        final String password = "cqpubpedlamghzfc";
        final String toEmail = "huynhvanvinh080398@gmail.com";
        final String subject = "[New]You have an order!!";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        message.setSubject(subject);
        String htmlContent = "<h1>You have an Order by : " + order.getCustomerInfo().getFullName() + "</h1> <p>You need to go to the Admin page to view order details!!</p>";
        message.setContent(htmlContent, "text/html");
        Transport.send(message);
        System.out.println("Gui mail thanh cong");
        return null;
    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void softDelete(Order order) {

    }

    @Override
    public List<OrderDTO> findOrderDTOByUserName(String userName) {
        return orderRepository.findOrderDTOByUserName(userName);
    }

    @Override
    public List<OrderDTO> findOrderDTO() {
        return orderRepository.findOrderDTO();
    }

    @Override
    public List<OrderDTO> findOrderDTOByUserNameAndStatus(String userName, String status) {
        List<OrderDTO> order = orderRepository.findOrderDTOByUserName(userName);
//        for (OrderDTO orderDTO : order) {
////            List<OrderDetailDTO> orderDetailDTOS = orderDetailRepository.findAllOrderDetailDTOByOrderId(orderDTO.getId());
////            for (OrderDetailDTO orderDetailDTOSs : orderDetailDTOS) {
//
//            }
//        }

        return order;
    }

    @Override
    public List<OrderDTO> findAllOrderDTOByOrderDetailId(Long id) {
        return orderRepository.findAllOrderDTOByOrderDetailId(id);
    }
}
