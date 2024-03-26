package com.example.back.controller;

import com.example.back.dto.PaymentDTO;
import com.example.back.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/")
    public void makePayment(@RequestBody PaymentDTO paymentDTO){
        try {
            paymentService.makePayment(paymentDTO);
        }catch (RuntimeException e){
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
