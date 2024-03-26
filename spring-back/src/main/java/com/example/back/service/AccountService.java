package com.example.back.service;

import com.example.back.dto.AccountCreateDTO;
import com.example.back.dto.AccountDepositDTO;
import com.example.back.entity.Account;
import com.example.back.entity.User;
import com.example.back.repository.AccountRepository;
import com.example.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    public Account findById(String id){
        return accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account not found"));
    }

    public List<Account> findAll(){
        return (List<Account>) accountRepository.findAll();
    }

    public Boolean deposit(AccountDepositDTO accountDepositDTO){
        if (accountDepositDTO.getSum() < 0){
            return false;
        }
        Account a = accountRepository.findById(accountDepositDTO.getNumber()).orElseThrow(() -> new RuntimeException("Account not found"));

        a.setSum(a.getSum() + accountDepositDTO.getSum());
        accountRepository.save(a);
        return true;
    }

    public void addAccount(String email, AccountCreateDTO accountCreateDTO){
        User u = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Account a = new Account();
        a.setNumber(accountCreateDTO.getNumber());
        a.setSum(accountCreateDTO.getSum());
        a.setOwner(u);
        a.setIsLocked(false);

        accountRepository.save(a);
    }

    public void blockAccount(String accountNumber, boolean islocked){
        Account a = accountRepository.findById(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        a.setIsLocked(islocked);
        accountRepository.save(a);
    }
}
