package com.example.back.dto;

import com.example.back.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountResponse {
    private String number;
    private Double sum;
    private Boolean isLocked;

    public UserAccountResponse(Account a){
        this.number = a.getNumber();
        this.sum = a.getSum();
        this.isLocked = a.getIsLocked();
    }
}
