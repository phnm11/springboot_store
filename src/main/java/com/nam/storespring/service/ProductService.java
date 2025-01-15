package com.nam.storespring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.nam.storespring.dto.request.ProductCreationRequest;
import com.nam.storespring.dto.response.ProductResponse;
import com.nam.storespring.entity.Product;
import com.nam.storespring.mapper.ProductMapper;
import com.nam.storespring.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService extends BaseRedisServiceImpl {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductService(RedisTemplate<String, Object> redisTemplate, ProductRepository productRepository) {
        super(redisTemplate);
        this.productRepository = productRepository;
    }

    // Cache 1 product
    public void cacheProduct(String id, Product product) {
        String cacheKey = "product:" + id;
        setObject(cacheKey, product);
        setTimeToLive(cacheKey, 1);
    }

    public Product createProduct(ProductCreationRequest request) {
        Product product = productMapper.toProduct(request);

        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ProductResponse getProduct(String id) {
        String cacheKey = "product:" + id;

        Product cachedProduct = (Product) get(cacheKey);

        if (cachedProduct != null) {
            return productMapper.toProductResponse(cachedProduct);
        }

        Product productFromDB = productRepository.findByIdWithReviews(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        cacheProduct(id, productFromDB);

        return productMapper.toProductResponse(productFromDB);
        // return
        // productMapper.toProductResponse(productRepository.findByIdWithReviews(id)
        // .orElseThrow(() -> new RuntimeException("Product not found!")));
    }

    public ProductResponse updateProduct(String id, ProductCreationRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        productMapper.updateProduct(product, request);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    public void deleteProduct(String id) {
        String cacheKey = "product:" + id;
        productRepository.deleteById(id);
        delete(cacheKey);
    }
}
