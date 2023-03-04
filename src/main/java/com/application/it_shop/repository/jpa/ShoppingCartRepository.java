package com.application.it_shop.repository.jpa;

import com.application.it_shop.model.ShoppingCart;
import com.application.it_shop.model.User;
import com.application.it_shop.model.enums.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndCartStatus(User user, ShoppingCartStatus shoppingCartStatus);
}
