package com.wadlab.academy_bank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    
    private String address;
    
    @Column(name = "state_of_origin")  // Fixed the typo here
    private String stateOfOrigin;
    
    @Column(unique = true) // Ensures the account number is unique
    private String accountNumber;
    
    @Column(precision = 10, scale = 2) // Ensures precision for account balance
    private BigDecimal accountBalance;
    
    @Column(unique = true) // Ensure unique email addresses
    private String email;
    
    private String phoneNumber;
    private String alternativePhoneNumber;
    
    private String status;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
