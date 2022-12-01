package com.proyecto01.product.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "accountPassive")
public class AccountPassive {
    @Id
    private String id;

    @NotNull
    private String identityNumber;

    @NotNull
    @Indexed(unique = true)
    private String accountNumber;

    @NotNull
    private LocalDate creationDate ;

    @NotNull
    private double balance;

    @NotNull
    private boolean active = true;

    @NotNull
    private int maintenanceFee;

    @NotNull
    private int limitMovements;

    @NotNull
    private TypeAccountPassive typeAccountsPassive;

}
