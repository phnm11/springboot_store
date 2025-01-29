package com.nam.storespring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nam.searchitem.SearchServiceGrpc;
import com.nam.searchitem.SearchServiceGrpc.SearchServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {
    @Bean
    ManagedChannel grpChannel() {
        return ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();
    }

    @Bean
    SearchServiceBlockingStub searchServiceStub(ManagedChannel channel) {
        return SearchServiceGrpc.newBlockingStub(channel);
    }
}
