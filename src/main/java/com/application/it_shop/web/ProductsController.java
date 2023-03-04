package com.application.it_shop.web;


import com.application.it_shop.model.Product;
import com.application.it_shop.service.BrandService;
import com.application.it_shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final BrandService brandService;


    public ProductsController(ProductService productService, BrandService brandService) {
        this.productService = productService;
        this.brandService = brandService;
    }

    @GetMapping
    public String getProductsPage(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String productBrand,
            @RequestParam(required = false) Double productPrice,
            Model model) {
        List<Product> productList;

        if(productName == null && productBrand == null && productPrice == null) {
            productList = this.productService.listAll();
        } else {
            productList = this.productService.filter(productName, productBrand, productPrice);
        }


        model.addAttribute("productsList", productList);
        model.addAttribute("bodyContent", "products");
        return "master-template";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("brands", this.brandService.listAll());
        model.addAttribute("bodyContent", "addProduct");
        return "master-template";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(
            @PathVariable Long id,
            Model model
    ) {
        Product p = this.productService.findById(id);
        model.addAttribute("product", p);
//        System.out.println("Editing an image: "+p.getImage());

        model.addAttribute("brands",this.brandService.listAll());
        model.addAttribute("bodyContent","addProduct");

        return "master-template";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("myfile") MultipartFile file,
                             @RequestParam("price") Double price,
                             @RequestParam("brandId") Long brandId) {
        this.productService.add(name,file,price,brandId);
        return "redirect:/products?SuccessfullyAdded";
    }

    @PostMapping("/add/{id}")
    public String update(@PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("myfile") MultipartFile file,
            @RequestParam("price") Double price,
            @RequestParam("brandId") Long brandId) {

        this.productService.edit(id,name, file,price,brandId);

        return "redirect:/products";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        this.productService.deleteById(id);
        return "redirect:/products";
    }
}
