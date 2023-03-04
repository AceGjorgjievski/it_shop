package com.application.it_shop.service;

import com.application.it_shop.model.Product;
import com.application.it_shop.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<Product> listAllProductsInShoppingCart(Long cartId);
    ShoppingCart getActiveCart(String username);
    ShoppingCart addProductToShoppingCart(String username, Long id);
    void removeProductFromShoppingCart(ShoppingCart shoppingCart, Long id);
}
