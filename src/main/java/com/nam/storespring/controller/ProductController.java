package com.nam.storespring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.nam.storespring.dto.request.ProductCreationRequest;
import com.nam.storespring.dto.response.ProductResponse;
import com.nam.storespring.entity.Product;
import com.nam.storespring.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    ProductResponse createProduct(@RequestBody ProductCreationRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping
    List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    ProductResponse getProduct(@PathVariable String productId) {
        return productService.getProduct(productId);
    }

    @PutMapping("/{productId}")
    ProductResponse updateProduct(@PathVariable String productId, @RequestBody ProductCreationRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    String deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return "Product with id " + productId + " has been deleted";
    }
}
