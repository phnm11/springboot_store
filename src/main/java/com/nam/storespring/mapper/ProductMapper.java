package com.nam.storespring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.nam.storespring.dto.request.ProductCreationRequest;
import com.nam.storespring.dto.response.ProductResponse;
import com.nam.storespring.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductCreationRequest request);

    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductCreationRequest request);
}
