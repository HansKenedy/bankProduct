package com.proyecto01.product.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public enum TypeAccountActive {
    STAFF("STAFF"),

    BUSINESS("BUSINESS"),

    CARD("CARD");

    private String Code;
}
