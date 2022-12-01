package com.proyecto01.product.web.model;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawModel {
    String accountNumber;
    @Positive(message = "Transfer amount must be positive")
    private double amount;
}
