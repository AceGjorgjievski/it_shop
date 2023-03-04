package com.application.it_shop.web;


import com.application.it_shop.model.Product;
import com.application.it_shop.model.ShoppingCart;
import com.application.it_shop.service.ProductService;
import com.application.it_shop.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {


    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    public CartController(ShoppingCartService shoppingCartService,
                          ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    @GetMapping
    public String getCartPage(Model model, HttpServletRequest request) {

        String username = request.getRemoteUser();
        ShoppingCart cart = null;
        List<Product> productList = null;
        try {
            cart = this.shoppingCartService.getActiveCart(username);
            productList = this.shoppingCartService.listAllProductsInShoppingCart(cart.getId());

            double totalPrice = cart.getProducts().stream()
                    .mapToDouble(i -> i.getPrice())
                    .sum();

            model.addAttribute("totalPrice", totalPrice);

            model.addAttribute("productsInCart", productList);
            model.addAttribute("bodyContent", "cart");

            request.getSession().setAttribute("user", username);

        } catch (Exception e) {
            return "redirect:/cart?error=" + e.getMessage();
        }


        return "master-template";
    }

    @DeleteMapping("/{id}/remove")
    public String removeFromCart(@PathVariable Long id,
                                 HttpServletRequest request,
                                 Model model) {
        String username = request.getRemoteUser();
        ShoppingCart shoppingCart = this.shoppingCartService.getActiveCart(username);

        this.shoppingCartService.removeProductFromShoppingCart(shoppingCart, id);

        double totalPrice = shoppingCart.getProducts().stream()
                .mapToDouble(i -> i.getPrice())
                .sum();

        model.addAttribute("totalPrice", totalPrice);


        return "redirect:/cart?SuccessfullyRemovedFromCart";
    }

    @GetMapping("/{id}/add")
    public String addToCart(@PathVariable Long id, HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        this.shoppingCartService.addProductToShoppingCart(username, id);
        model.addAttribute("addedToCart",true);

        return "redirect:/products?SuccessfullyAddedToCart";
    }

}
