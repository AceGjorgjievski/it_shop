package com.application.it_shop.service;

import com.application.it_shop.model.Brand;
import com.application.it_shop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product add(String name, MultipartFile multipartFile, Double price, Long brandId);
    List<Product> listAll();

    Product findById(Long id);

    void deleteById(Long id);

    Product edit(Long productId, String name,
                 MultipartFile multipartFile, Double price, Long brandId);

    List<Product> filter(String productName, String productBrand, Double productPrice);
}
