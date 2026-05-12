package com.accountabc.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Security {
    private String name;
    private BigDecimal target;
    private BigDecimal current;
    private BigDecimal targetVariance;
    private BigDecimal unitPrice;
}