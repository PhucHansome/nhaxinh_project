package com.cg.model;

import com.cg.model.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
    public UserDTO toUserDTO() {
        return new UserDTO()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setRole(role.toRoleDTO())
                .setStatus(status)
                ;

    }

}
