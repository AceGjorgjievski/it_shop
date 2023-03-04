package com.application.it_shop.service;

import com.application.it_shop.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    List<Brand> listAll();
    Brand findById(Long id);

    void deleteById(Long id);
    Brand create(String name, String description);
    Brand edit(Long brandId, String name, String description);

}
