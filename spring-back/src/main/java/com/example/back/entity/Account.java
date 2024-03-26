package com.example.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String number;
    private Double sum;

    private Boolean isLocked;

    @ManyToOne
    @JoinColumn(name="owner", nullable=false)
    private User owner;

    @OneToMany(mappedBy="from_account")
    private List<Payment> senders;

    @OneToMany(mappedBy="to_account")
    private List<Payment> receivers;
}