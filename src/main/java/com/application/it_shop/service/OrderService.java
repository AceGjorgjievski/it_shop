package com.application.it_shop.service;

import com.application.it_shop.model.Order;
import com.application.it_shop.model.Product;

import java.util.List;

public interface OrderService {

    Order placeOrder(String address, String city, Integer phoneNumber, List<Product> productList);
    List<Order> findAll();
}
