package com.nam.storespring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.nam.storespring.dto.request.ProductCreationRequest;
import com.nam.storespring.dto.response.ApiResponse;
import com.nam.storespring.dto.response.ProductResponse;
import com.nam.storespring.dto.response.ProductSearchResponse;
import com.nam.storespring.service.ProductService;
import com.nam.storespring.service.SearchService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    private final SearchService searchService;

    public ProductController(ProductService productService, SearchService searchService) {
        this.productService = productService;
        this.searchService = searchService;
    }

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreationRequest request) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.createProduct(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<ProductResponse>> getProducts() {
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.getProducts());
        return apiResponse;
    }

    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getProduct(@PathVariable String productId) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.getProduct(productId));
        return apiResponse;
    }

    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable String productId,
            @RequestBody ProductCreationRequest request) {

        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.updateProduct(productId, request));

        return apiResponse;
    }

    @DeleteMapping("/{productId}")
    ApiResponse<String> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult("Product with id " + productId + " has been deleted");
        return apiResponse;
    }

    @GetMapping("/search")
    ApiResponse<List<ProductSearchResponse>> searchProducts(@RequestParam String name) {

        ApiResponse<List<ProductSearchResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(searchService.searchProduct(name));
        return apiResponse;
    }
}
