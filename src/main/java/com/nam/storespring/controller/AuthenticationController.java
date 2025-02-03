package com.nam.storespring.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nam.storespring.dto.request.AuthenticationRequest;
import com.nam.storespring.dto.request.IntrospectRequest;
import com.nam.storespring.dto.response.AuthenticationResponse;
import com.nam.storespring.dto.response.IntrospectResponse;
import com.nam.storespring.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);

        return AuthenticationResponse.builder()
                .token(result.getToken())
                .authenticated(result.isAuthenticated())
                .build();
    }

    @PostMapping("/introspect")
    public IntrospectResponse introspect(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        return authenticationService.introspect(request);
    }
}
