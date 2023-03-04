package com.application.it_shop.service.impl;

import com.application.it_shop.model.Product;
import com.application.it_shop.model.ShoppingCart;
import com.application.it_shop.model.User;
import com.application.it_shop.model.enums.ShoppingCartStatus;
import com.application.it_shop.model.exceptions.*;
import com.application.it_shop.repository.jpa.ProductRepository;
import com.application.it_shop.repository.jpa.ShoppingCartRepository;
import com.application.it_shop.repository.jpa.UserRepository;
import com.application.it_shop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);

        return this.shoppingCartRepository
                .findById(cartId)
                .get()
                .getProducts();
    }

    @Override
    public ShoppingCart getActiveCart(String username) {
        User u = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFoundException(username));


        return this.shoppingCartRepository
                .findByUserAndCartStatus(u, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart(u);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveCart(username);
        Product p = this.productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        int sizeOfShoppingCartList = shoppingCart
                .getProducts()
                .stream()
                .filter(i -> i.getId().equals(p.getId()))
                .collect(Collectors.toList())
                .size();
        if(sizeOfShoppingCartList > 0) { // the product 'p' already is in the shoppingCart
            throw new ProductAlreadyInShoppingCartException(productId, username);
//            p.setQuantity(p.getQuantity()+1);
//            p.setPrice(p.getPrice()*p.getQuantity());
        }
//        else {
            shoppingCart.getProducts().add(p);
//        }

        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void removeProductFromShoppingCart(ShoppingCart shoppingCart, Long id) {
        List<Product> products = shoppingCart.getProducts();

        for(Product p : products) {
            p.setQuantity(1);
        }


        Product p = this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        p.setQuantity(1);
        products.removeIf(i -> i.getId().equals(p.getId()));

        shoppingCart.setProducts(products);

        this.shoppingCartRepository.save(shoppingCart);
    }
}
