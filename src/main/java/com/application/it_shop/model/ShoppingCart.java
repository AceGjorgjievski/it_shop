package com.application.it_shop.model;


import com.application.it_shop.model.enums.ShoppingCartStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus cartStatus;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public ShoppingCart(User user) {
        this.user = user;
        this.products = new ArrayList<>();
        this.dateCreated = LocalDateTime.now();
        this.cartStatus = ShoppingCartStatus.CREATED;
    }
}
