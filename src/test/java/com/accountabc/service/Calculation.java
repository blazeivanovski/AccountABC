package com.accountabc.service;

import com.accountabc.model.Security;

import java.math.BigDecimal;

import static com.accountabc.utils.Constants.*;

public class Calculation {

    public static BigDecimal calculateCurrentTotalValue(Security security) {
        return security.getCurrent()
                .divide(BigDecimal.valueOf(100), SCALE, ROUNDING)
                .multiply(TOTAL_ASSET);
    }

    public static BigDecimal calculateCurrentNumberOfShares(Security security) {
        return calculateCurrentTotalValue(security)
                .divide(security.getUnitPrice(), SCALE, ROUNDING);
    }

    public static BigDecimal calculateNumberOfSharesToTrade(Security security) {
        return calculateCurrentNumberOfShares(security)
                .multiply(security.getTargetVariance()).negate()
                .divide(security.getCurrent(), SCALE, ROUNDING);
    }

    public static BigDecimal calculateActualNumberOfShares(Security security) {
        return calculateCurrentNumberOfShares(security)
                .add(calculateNumberOfSharesToTrade(security));
    }

    public static BigDecimal calculateActualTotalValue(Security security) {
        return calculateActualNumberOfShares(security)
                .multiply(security.getUnitPrice());
    }
}
