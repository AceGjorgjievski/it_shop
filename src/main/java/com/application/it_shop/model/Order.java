package com.application.it_shop.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String city;

    private Integer phoneNumber;

    private Double totalPrice;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> productList;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderCreated;

    public Order() {
        this.productList = new ArrayList<>();
        this.orderCreated = LocalDateTime.now();
    }

    public Order(String address, String city, Integer phoneNumber, List<Product> productList) {
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.productList = new ArrayList<>(productList);
        this.orderCreated = LocalDateTime.now();
        this.totalPrice = productList.stream().mapToDouble(i -> i.getPrice()).sum();
    }
}
