package com.application.it_shop.repository.jpa;

import com.application.it_shop.model.Brand;
import com.application.it_shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByNameContaining(String name);
}
