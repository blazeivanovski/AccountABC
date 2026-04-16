package com.accountabc.model;

import lombok.Data;

@Data
public class Security {
    private String name;
    private int target;
    private int current;
    private int targetVariance;
    private double unitPrice;
}