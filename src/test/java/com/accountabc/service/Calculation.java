package com.accountabc.service;

import com.accountabc.model.Security;

import static com.accountabc.utils.Constants.TOTAL_ASSET;

public class Calculation {

    public static double calculateCurrentTotalSharesValue(Security security) {
        return (double) security.getCurrent() / 100 * TOTAL_ASSET;
    }

    public static double calculateCurrentNumberOfShares(Security security) {
        return calculateCurrentTotalSharesValue(security) / security.getUnitPrice();
    }

    public static double calculateNumberOfSharesToBuySell(Security security) {
        return calculateCurrentNumberOfShares(security)  / security.getCurrent() * security.getTargetVariance() * -1;
    }

    public static double calculateNewNumberOfShares(Security security) {
        return calculateCurrentNumberOfShares(security) + calculateNumberOfSharesToBuySell(security);
    }

    public static double calculateNewTotalSharesValue(Security security) {
        return calculateNewNumberOfShares(security)  * security.getUnitPrice();
    }
}
