package com.application.it_shop.service.impl;

import com.application.it_shop.model.Brand;
import com.application.it_shop.model.Product;
import com.application.it_shop.model.exceptions.BrandNotFoundException;
import com.application.it_shop.model.exceptions.ProductNotFoundException;
import com.application.it_shop.repository.jpa.BrandRepository;
import com.application.it_shop.repository.jpa.ProductRepository;
import com.application.it_shop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Product> listAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product add(String name, MultipartFile multipartFile, Double price, Long brandId) {

        String image = this.setProductImage(multipartFile);
//        System.out.println("Adding an image: "+image);

        Brand b = this.brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));


        Product p = new Product(name, image, price, b);
        return this.productRepository.save(p);
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public Product edit(Long productId, String name, MultipartFile file, Double price, Long brandId) {
        Product p = this.findById(productId);

        p.setName(name);

        String image = this.setProductImage(file);

        p.setImage(image);
        p.setPrice(price);

        Brand b = this.brandRepository
                .findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));
        p.setBrand(b);

        return this.productRepository.save(p);
    }

    @Override
    public List<Product> filter(String productName, String productBrand, Double productPrice) {

        Brand brand = productBrand != null && !productBrand.isEmpty() ?
                this.brandRepository.findByNameContaining(productBrand) : null;


        if (productName != null && !productName.isEmpty() && brand != null && productPrice != null) {
            return this.productRepository
                    .findProductsByNameContainingAndBrandAndPrice(productName, brand, productPrice);
        } else if (!productName.isEmpty() && brand != null && productPrice == null) {
            return this.productRepository
                    .findProductsByNameContainingAndBrand(productName, brand);
        } else if (!productName.isEmpty() && productPrice != null && brand == null) {
            return this.productRepository
                    .findProductsByNameContainingAndPrice(productName, productPrice);
        } else if (brand != null && productPrice != null && productName.isEmpty()) {
            return this.productRepository
                    .findProductsByPriceAndBrand(productPrice, brand);
        } else if (!productName.isEmpty() && brand == null && productPrice == null) {
            return this.productRepository
                    .findProductsByNameContaining(productName);
        } else if (productPrice != null && productName.isEmpty() && brand == null) {
            return this.productRepository
                    .findProductsByPrice(productPrice);
        } else if (brand != null && productName.isEmpty() && productPrice == null) {
            return this.productRepository
                    .findProductsByBrand(brand);
        } else {
            return this.productRepository
                    .findAll();
        }
    }

    private String setProductImage(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String imageName = null;
        if (fileName.contains("..")) {
            System.out.println("not a a valid file");
        }
        try {
            imageName = Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageName;
    }


}
