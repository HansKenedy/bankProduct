package com.proyecto01.product.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto01.product.domain.TypeAccountPassive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPassiveModel {
    @JsonIgnore
    private String id;

    @NotBlank(message="Identity Number cannot be null or empty")
    private String identityNumber;

    @NotBlank(message="accountNumber Number cannot be null or empty")
    private String accountNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate creationDate ;

    @PositiveOrZero
    private double balance;

    private boolean active = true;

    @PositiveOrZero
    private int maintenanceFee;

    @PositiveOrZero
    private int limitMovements;

    private TypeAccountPassive typeAccountsPassive;

}
