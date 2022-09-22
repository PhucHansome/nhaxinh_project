package com.cg.service.user;

import com.cg.exception.DataInputException;
import com.cg.model.User;
import com.cg.model.UserPrinciple;
import com.cg.model.dto.UserDTO;
import com.cg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean existsByEmailAndIdIsNot(String email, Long id) {
        return userRepository.existsByEmailAndIdIsNot(email,id);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserDTO> findUserDTOByUsername(String username) {
        return userRepository.findUserDTOByUsername(username);
    }

    @Override
    public List<UserDTO> findAllUserDTOByDeletedIsFailse() {
        return userRepository.findAllUserDTOByDeletedIsFailse();
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
//        return null;
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void softDelete(User user) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(userOptional.get());
//        return (UserDetails) userOptional.get();
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User saveNoPassword(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserDTO> findUserDTOById(Long id) {
        return userRepository.findUserDTOById(id);
    }

    @Override
    public Optional<UserDTO> findUserDTOByUserNameByStatus(String userName) {
        return userRepository.findUserDTOByUserNameByStatus(userName);
    }

    @Override
    public User saveAndMail(User user) throws MessagingException, UnsupportedEncodingException {
        user.setPassword(randomAlphanumeric(10));

        final String fromEmail = "nhaxinhprj@gmail.com";
        final String password = "cqpubpedlamghzfc";
        final String toEmail = user.getUsername();
        final String subject = "[New]You have a NewPassWord!!";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        prop.put("mail.smtp.port", "587"); //TLS Port
        prop.put("mail.smtp.auth", "true"); //enable authentication
        prop.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(prop, auth);
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
                "        <h2>Account: " + user.getUsername() + " password has been changed </h2>\n" +
                "        <br>\n" +
                "        <p style=\"text-align: left; padding-left: 60px ;\">Dear " + user.getUsername() + "!</p>\n" +
                "        <p style=\"text-align: left; padding-left: 60px ;\">Your password has been changed to:<b> " + user.getPassword() + "</b> </p>\n" +
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return null;
    }

    @Override
    public User Block(User user) {
        user.setStatus("Block");
        return userRepository.save(user);
    }

    @Override
    public User Active(User user) {
        user.setStatus("Active");
        return userRepository.save(user);
    }

}
