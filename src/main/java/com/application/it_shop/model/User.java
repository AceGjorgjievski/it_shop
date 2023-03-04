package com.application.it_shop.model;


import lombok.Data;
import com.application.it_shop.model.enums.Role;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "shop_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    private String password;

    private String username;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user")
    private List<ShoppingCart> carts;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(String name,
                String surname,
                LocalDate birthday,
                String username,
                String email,
                String password,
                Role role) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = birthday;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
