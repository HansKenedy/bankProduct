package com.proyecto01.product.domain;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"identityNumber"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "accountActive")
public class AccountActive {
    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String identityNumber;

    @NotNull
    @Indexed(unique = true)
    private String accountNumber;

    @NotNull
    private TypeAccountActive typeAccountActive;

    @NotNull
    private LocalDate registerDate;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private int dues;

    @NotNull
    private LocalDate highDate;

    @NotNull
    private boolean active = true;


}
