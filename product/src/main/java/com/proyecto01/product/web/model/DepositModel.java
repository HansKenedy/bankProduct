package com.proyecto01.product.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositModel {
    @NotBlank(message = "Target account no is mandatory")
    private String accountNumber;
    @Positive(message = "Transfer amount must be positive")
    private double amount;
}
