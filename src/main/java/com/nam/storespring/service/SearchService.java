package com.nam.storespring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nam.searchitem.SearchProductRequest;
import com.nam.searchitem.SearchProductResponse;
import com.nam.searchitem.SearchServiceGrpc.SearchServiceBlockingStub;
import com.nam.searchitem.SearchUserRequest;
import com.nam.searchitem.SearchUserResponse;
import com.nam.storespring.dto.response.ProductSearchResponse;
import com.nam.storespring.dto.response.UserSearchResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchService {
    private final SearchServiceBlockingStub searchServiceStub;

    public SearchService(SearchServiceBlockingStub searchServiceStub) {
        this.searchServiceStub = searchServiceStub;
    }

    public List<ProductSearchResponse> searchProduct(String keyword) {
        SearchProductRequest request = SearchProductRequest.newBuilder()
                .setQuery(keyword)
                .build();

        SearchProductResponse response = searchServiceStub.searchProduct(request);

        return response.getProductsList().stream()
                .map(product -> ProductSearchResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .categoryId(product.getCategoryId())
                        .imageUrl(product.getImageUrl())
                        .views(product.getViews())
                        .build())
                .collect(Collectors.toList());
    }

    public List<UserSearchResponse> searchUser(String firstName, String lastName, String userName, String email,
            Integer phone, String address, String role) {

        int phoneValue = (phone != null) ? phone : 0;
        SearchUserRequest request = SearchUserRequest.newBuilder()
                .setFirstName(Optional.ofNullable(firstName).orElse(""))
                .setLastName(Optional.ofNullable(lastName).orElse(""))
                .setUserName(Optional.ofNullable(userName).orElse(""))
                .setEmail(Optional.ofNullable(email).orElse(""))
                .setPhone(phoneValue)
                .setAddress(Optional.ofNullable(address).orElse(""))
                .setRole(Optional.ofNullable(role).orElse(""))
                .build();

        log.info("Search params: firstName={}, lastName={}, userName={}, email={}, phone={}, address={}, role={}",
                firstName, lastName, userName, email, phone, address, role);

        try {
            SearchUserResponse response = searchServiceStub.searchUser(request);

            if (response.getUsersList().isEmpty()) {
                log.info("No user found with given search parameters.");
            }

            return response.getUsersList().stream()
                    .map(user -> UserSearchResponse.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .userName(user.getUserName())
                            .email(user.getEmail())
                            .phone(user.getPhone())
                            .address(user.getAddress())
                            .role(user.getRole())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to search users: ", e);
            throw new RuntimeException("Unable to search users at the moment. Please try again later.");
        }
    }
}
