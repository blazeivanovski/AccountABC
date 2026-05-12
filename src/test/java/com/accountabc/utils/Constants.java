package com.accountabc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Constants {

    private Constants() {}

    public static final BigDecimal TOTAL_ASSET = BigDecimal.valueOf(100000);
    public static final int SCALE = 4;
    public static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
}
