package com.proyecto01.product.domain;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public enum TypeAccountPassive {

    AHORRO("AHORRO"),

    CORRIENTE("CORRIENTE"),

    PLAZOFIJO("PLAZOFIJO");

    private String Code;

}
