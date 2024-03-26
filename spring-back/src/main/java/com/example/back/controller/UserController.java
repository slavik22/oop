package com.example.back.controller;

import com.example.back.dto.UserAccountResponse;
import com.example.back.dto.UserAuthResponse;
import com.example.back.dto.UserLoginDTO;
import com.example.back.dto.UserRegisterDTO;
import com.example.back.entity.Account;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<UserAuthResponse> login(@RequestBody UserLoginDTO loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserAuthResponse> registerUser(@RequestBody UserRegisterDTO registerDto) {
        return ResponseEntity.ok(userService.register(registerDto));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<UserAccountResponse>> getUserAccounts(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<Account> accounts = userService.getUserAccounts(userDetails.getUsername());
        return ResponseEntity.ok(accounts.stream().map(UserAccountResponse::new).toList());
    }

    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userService.isAdmin(userDetails.getUsername()));
    }
}
