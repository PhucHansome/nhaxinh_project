package com.cg.service.user;

import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    User getByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<UserDTO> findUserDTOByUsername(String username);

    List<UserDTO> findAllUserDTOByDeletedIsFailse();

    boolean existsById(Long id);

    User saveNoPassword(User user);

    Optional<UserDTO> findUserDTOById(Long id);

    Optional<UserDTO> findUserDTOByUserNameByStatus(String userName);

    User saveAndMail(User user ) throws MessagingException, UnsupportedEncodingException;

    User Block(User user);

    User Active(User user);

    boolean existsByEmailAndIdIsNot(String email,Long id);

}
