package com.application.it_shop.repository.jpa;


import com.application.it_shop.model.Brand;
import com.application.it_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductsByNameContaining(String productName);
    List<Product> findProductsByBrand(Brand brand);
    List<Product> findProductsByPrice(Double productPrice);

    List<Product> findProductsByNameContainingAndBrand(String productName, Brand brand);
    List<Product> findProductsByNameContainingAndPrice(String productName, Double productPrice);
    List<Product> findProductsByPriceAndBrand(Double productPrice, Brand brand);

    List<Product> findProductsByNameContainingAndBrandAndPrice(String productName,
                                                                                     Brand brand,
                                                                                     Double productPrice);

}
