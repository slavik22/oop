package com.example.back.controller;

import com.example.back.dto.AccountCreateDTO;
import com.example.back.dto.AccountDepositDTO;
import com.example.back.dto.PaymentDTO;
import com.example.back.dto.UserAccountResponse;
import com.example.back.entity.Account;
import com.example.back.service.AccountService;
import com.example.back.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/get/{id}")
    public ResponseEntity<UserAccountResponse> deposit(@PathVariable String id){

        return ResponseEntity.ok( new UserAccountResponse(accountService.findById(id)));
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody AccountDepositDTO accountDepositDTO){
        accountService.deposit(accountDepositDTO);
        return ResponseEntity.ok("Payment created");
    }
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody AccountCreateDTO accountCreateDTO, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        accountService.addAccount(((UserDetails) authentication.getPrincipal()).getUsername(), accountCreateDTO);
        return ResponseEntity.ok("Payment created");
    }
    @PostMapping("/{number}/block")
    public ResponseEntity<String> block(@PathVariable String number){
        accountService.blockAccount(number, true);
        return ResponseEntity.ok("Account blocked");
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserAccountResponse>> get() {
        List<Account> accounts = accountService.findAll();
        return ResponseEntity.ok(accounts.stream().map(UserAccountResponse::new).toList());
    }
    @PostMapping("/{number}/unblock")
    public ResponseEntity<String> unblock(@PathVariable String number){
        accountService.blockAccount(number, false);
        return ResponseEntity.ok("Account unblocked");
    }
}
