package com.example.back.service;

import com.example.back.dto.PaymentDTO;
import com.example.back.dto.UserAuthResponse;
import com.example.back.dto.UserLoginDTO;
import com.example.back.dto.UserRegisterDTO;
import com.example.back.entity.Account;
import com.example.back.entity.Payment;
import com.example.back.entity.User;
import com.example.back.repository.AccountRepository;
import com.example.back.repository.PaymentRepository;
import com.example.back.repository.UserRepository;
import com.example.back.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void makePayment(PaymentDTO paymentDTO){
        Account from = accountRepository.findById(paymentDTO.getFrom()).orElseThrow(()->new RuntimeException("Sender account is incorrect"));
        Account to = accountRepository.findById(paymentDTO.getTo()).orElseThrow(()->new RuntimeException("Receiver account is incorrect"));

        if (from.getIsLocked()){
            throw new RuntimeException("Account is locked");
        }

        if (from.getSum() < paymentDTO.getSum()){
            throw new RuntimeException("Not enough money");
        }

        from.setSum(from.getSum() - paymentDTO.getSum());
        to.setSum(to.getSum() + paymentDTO.getSum());

        Payment p = new Payment();

        p.setFrom_account(from);
        p.setTo_account(to);
        p.setSum(paymentDTO.getSum());
        p.setTime(new Date());

        paymentRepository.save(p);
    }
}
