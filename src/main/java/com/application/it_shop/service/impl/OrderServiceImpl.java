package com.application.it_shop.service.impl;

import com.application.it_shop.model.Order;
import com.application.it_shop.model.Product;
import com.application.it_shop.repository.jpa.OrderRepository;
import com.application.it_shop.service.OrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order placeOrder(String address, String city, Integer phoneNumber, List<Product> productList) {

        Order order = new Order(address, city, phoneNumber, productList);

        this.orderRepository.save(order);

        return order;
    }

    @Override
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }
}
