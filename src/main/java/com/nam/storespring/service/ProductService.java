package com.nam.storespring.service;

import java.util.List;

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
    private ProductRepository productRepository;

    private ProductMapper productMapper;

    private final KafkaProducerService kafkaProducerService;

    public ProductService(RedisTemplate<String, Object> redisTemplate, ProductRepository productRepository,
            KafkaProducerService kafkaProducerService, ProductMapper productMapper) {
        super(redisTemplate);
        this.productRepository = productRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.productMapper = productMapper;
    }

    // Cache 1 product
    public void cacheProduct(String id, Product product) {
        String cacheKey = "product:" + id;
        setObject(cacheKey, product);
        setTimeToLive(cacheKey, 1);
    }

    public ProductResponse createProduct(ProductCreationRequest request) {
        Product product = productMapper.toProduct(request);
        Product savedProduct = productRepository.save(product);
        kafkaProducerService.sendLog("product-logs", "ADD", savedProduct.getId(), savedProduct);
        return productMapper.toProductResponse(savedProduct);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
    }

    public ProductResponse getProduct(String id) {
        String cacheKey = "product:" + id;

        Product cachedProduct = (Product) get(cacheKey);

        if (cachedProduct != null) {
            kafkaProducerService.sendMessage("product_views", id);
            return productMapper.toProductResponse(cachedProduct);
        }

        Product productFromDB = productRepository.findByIdWithReviews(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        cacheProduct(id, productFromDB);

        kafkaProducerService.sendMessage("product_views", id);

        return productMapper.toProductResponse(productFromDB);
    }

    public ProductResponse updateProduct(String id, ProductCreationRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));

        productMapper.updateProduct(product, request);

        Product updatedProduct = productRepository.save(product);

        kafkaProducerService.sendLog("product-logs", "UPDATE", id, updatedProduct);

        return productMapper.toProductResponse(updatedProduct);
    }

    public void deleteProduct(String id) {
        String cacheKey = "product:" + id;
        Product productDeleted = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
        productRepository.deleteById(id);
        delete(cacheKey);
        kafkaProducerService.sendLog("product-logs", "DELETE", id, productDeleted);
    }
}
