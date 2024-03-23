package com.example.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private int id;
    private String name;
    private String email;
    private boolean isAdmin;
    private String jwtToken;
}
