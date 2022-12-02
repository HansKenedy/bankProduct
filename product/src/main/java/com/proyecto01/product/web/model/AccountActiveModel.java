package com.proyecto01.product.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto01.product.domain.TypeAccountActive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountActiveModel {
    @JsonIgnore
    private String id;

    @NotBlank(message="Identity Number cannot be null or empty")
    private String identityNumber;

    @NotBlank(message="accountNumber Number cannot be null or empty")
    private String accountNumber;


    private TypeAccountActive typeAccountActive;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate registerDate;

    @PositiveOrZero
    private double amount;

    @PositiveOrZero
    private int dues;

    @PositiveOrZero
    private double amountPaid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate highDate;

    private boolean active = true;

}
