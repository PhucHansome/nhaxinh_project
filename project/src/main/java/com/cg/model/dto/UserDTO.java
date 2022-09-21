package com.cg.model.dto;

import com.cg.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDTO  {

    private Long id;

    @NotBlank(message = "Email không được để trống")
    //@Email(message = "Email không đúng định dạng")
    @Email(message = "{model.userdto.email.invalid}")
    @Size(max = 50, message = "Tên đăng nhập tối đa 50 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max = 30, message = "Mật khẩu tối đa 30 ký tự")
    @Size(min = 6, message = "Mật khẩu tối thiểu 6 ký tự")
    private String password;

    @Valid
    private RoleDTO role;

    private String status;

    private Date createdAt;

    private Date updatedAt;



    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDTO(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
//        this.password = password;
        this.role = role.toRoleDTO();
    }

    public UserDTO(Long id, String username,String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role.toRoleDTO();

    }

    public UserDTO(Long id, String username,String password, Role role,String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role.toRoleDTO();
        this.status = status;
    }

    public UserDTO(Long id, String username, Role role,String status) {
        this.id = id;
        this.username = username;
        this.role = role.toRoleDTO();
        this.status = status;
    }
    public User toUser() {
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRole())
                .setStatus(status)
                ;

    }

}
