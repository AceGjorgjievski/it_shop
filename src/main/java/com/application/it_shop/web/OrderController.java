package com.application.it_shop.web;


import com.application.it_shop.model.Order;
import com.application.it_shop.model.Product;
import com.application.it_shop.model.ShoppingCart;
import com.application.it_shop.service.OrderService;
import com.application.it_shop.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.GeneratedValue;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;

    public OrderController(ShoppingCartService shoppingCartService,
                           OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrders(Model model) {
        List<Order> orders = this.orderService.findAll();

        model.addAttribute("orders",orders);
        model.addAttribute("bodyContent","orders");

        return "master-template";
    }

    @PostMapping("/prepareOrder")
    public String addNewOrder(HttpServletRequest request, Model model) {

        String username = request.getRemoteUser();
        ShoppingCart cart = this.shoppingCartService.getActiveCart(username);
        List<Product> productList = this.shoppingCartService.listAllProductsInShoppingCart(cart.getId());

        model.addAttribute("productsForOrder", productList);
        model.addAttribute("bodyContent","order-details");


        return "master-template";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(@RequestParam String address,
                            @RequestParam String city,
                            @RequestParam Integer phone,
                            HttpServletRequest request, Model model) {

        String username = request.getRemoteUser();
        ShoppingCart cart = this.shoppingCartService.getActiveCart(username);
        List<Product> productList = this.shoppingCartService.listAllProductsInShoppingCart(cart.getId());

        this.orderService.placeOrder(address,city,phone,productList);


//        List<Order> orders = this.orderService.findAll();
//
//        model.addAttribute("products", productList);
//        model.addAttribute("address",address);
//        model.addAttribute("city",city);
//        model.addAttribute("phone", phone);
//        model.addAttribute("bodyContent","orders");

        return "redirect:/order";
    }
}
