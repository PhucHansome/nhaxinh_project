package com.cg.service.order;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            orderNew.get().setAddress(order1.getCustomerInfo().getLocationRegion().getAddress());
            orderNew.get().setUserName(order1.getCustomerInfo().getUserName());
            orderNew.get().setPhone(order1.getCustomerInfo().getPhone());
            orderNew.get().setDistrictName(order1.getCustomerInfo().getLocationRegion().getDistrictName());
            orderNew.get().setProvinceName(order1.getCustomerInfo().getLocationRegion().getProvinceName());

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
        String htmlContent = "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "\n" +
                "    <title>Document</title>\n" +
                "  </head>\n" +
                "\n" +
                "  <body>\n" +
                "    <div style=\"border: 90px solid red;\">\n" +
                "      <div style=\" text-align: center\">\n" +
                "        <h1>You have an Order by : " + order.getCustomerInfo().getFullName() + " - " + order.getCustomerInfo().getPhone() + "</h1>\n" +
                "        <br>\n" +
                "        <p style=\"text-align: left; padding-left: 60px ;\">Dear Manager!</p>\n" +
                "        <p style=\"text-align: left; padding-left: 60px ;\">You need <a href=\"http://localhost:8092/home-dashboard\">Click Here</a> to go to the admin page to process!!</p>\n" +
                "        </div>\n" +
                "        <br />\n" +
                "        <br />\n" +
                "        <br />\n" +
                "        <div style=\"padding-left: 60px;padding-bottom: 19px;font-weight: bold;\">\n" +
                "          <p></p>\n" +
                "          <p>---</p>  \n" +
                "        <p>Nhà Xinh</p>\n" +
                "        <p>Phone number: (84+) 0349108527 </p>\n" +
                "        <p>Email: Nhaxinhprj@gmail.com <span style=\"float: right;\"><img src=\"https://nhaxinh.com/wp-content/uploads/2022/04/logo-nha-xinh-moi-200422.png\" alt=\"\"></span></p>\n" +
                "        <p>Facebook: facebook.com/somitrang09 </p>\n" +
                "      </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n" +
                "";

        message.setContent(htmlContent, "text/html");
        Transport.send(message);

        final String toEmail2 = order.getCustomerInfo().getUserName();
        final String subjectt = "[New]You have successfully placed your order!!";
        Properties propss = new Properties();
        propss.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        propss.put("mail.smtp.port", "587"); //TLS Port
        propss.put("mail.smtp.auth", "true"); //enable authentication
        propss.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        Authenticator authn = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session sessionn = Session.getInstance(propss, authn);
        MimeMessage messages = new MimeMessage(sessionn);
        messages.setFrom(new InternetAddress(fromEmail));
        messages.addHeader("Content-type", "text/HTML; charset=UTF-8");
        messages.addHeader("format", "flowed");
        messages.addHeader("Content-Transfer-Encoding", "8bit");
        messages.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail2, false));
        messages.setSubject(subjectt);
        String htmlContent1 =
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "\n" +
                "    <title>Document</title>\n" +
                "  </head>\n" +
                "\n" +
                "  <body>\n" +
                "    <div style=\"border: 90px solid red;\">\n" +
                "      <div style=\" text-align: center\">\n" +
                "        <h1>Thank you : " + order.getCustomerInfo().getFullName() + " Ordered at our store</h1>\n" +
                "        <br>\n" +
                "        <p style=\"text-align: left; padding-left: 60px ;\">Dear " + order.getCustomerInfo().getFullName() + "!</p>\n" +
                "        <p style=\"text-align: left; padding-left: 60px ;\">Our store is very grateful " + order.getCustomerInfo().getFullName() + " trusted our store!!</p>\n" +
                "        <p style=\"text-align: left; padding-left: 60px ;\">I wish you a good day!!         <img src=\"https://i.pinimg.com/originals/ca/ee/2a/caee2a8625993767e40c6a8c68e69dc8.gif\" width=\"50px\" style=\" position: relative; bottom: -13px;left: -4px;\"  alt=\"\"></p>\n" +
                "        </div>\n" +
                "        <br />\n" +
                "        <br />\n" +
                "        <br />\n" +
                "        <div style=\"padding-left: 60px;padding-bottom: 19px;font-weight: bold;\">\n" +
                "          <p></p>\n" +
                "          <p>---</p>  \n" +
                "        <p>Nhà Xinh</p>\n" +
                "        <p>Phone number: (84+) 0349108527 </p>\n" +
                "        <p>Email: Nhaxinhprj@gmail.com <span style=\"float: right;\"><img src=\"https://nhaxinh.com/wp-content/uploads/2022/04/logo-nha-xinh-moi-200422.png\" alt=\"\"></span></p>\n" +
                "        <p>Facebook: facebook.com/somitrang09 </p>\n" +
                "      </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n" +
                "";

        messages.setContent(htmlContent1, "text/html");
        Transport.send(messages);
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
    public List<OrderDTO> findOderByCreateBetween(Date createAt1, Date createAt2) {


        return orderRepository.findOderByCreateBetween(createAt1,createAt2);
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
