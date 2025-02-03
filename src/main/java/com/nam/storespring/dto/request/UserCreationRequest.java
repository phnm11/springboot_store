package com.nam.storespring.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    String firstName;
    String lastName;

    @Size(min = 5, message = "Username must be at least 5 characters!")
    String userName;

    @Size(min = 8, message = "Password must be at least 8 characters!")
    String password;
    String email;
    int phone;
    String address;
    String role;
}
