package com.accountabc.utils;

import com.accountabc.Base;
import com.accountabc.model.Security;
import io.qameta.allure.Step;

public class CalculationService extends Base {

    @Step("1. Calculate current total price of shares for {security.name}")
    public static double calculateCurrentTotalPriceOfShares(Security security) {
        return (double) security.getCurrent() / 100 * initialTotalAsset;
    }

    @Step("2. Calculate current number of shares for {security.name}")
    public static double calculateCurrentNumberOfShares(Security security) {
        return calculateCurrentTotalPriceOfShares(security) / security.getUnitPrice();
    }

    @Step("3. Calculate number of shares to buy and sell for {security.name}")
    public static double calculateNumberOfSharesToBuySell(Security security) {
        return calculateCurrentNumberOfShares(security)  / security.getCurrent() * security.getTargetVariance() * -1;
    }

    @Step("4. Calculate new number of shares for {security.name}")
    public static double calculateNewNumberOfShares(Security security) {
        return calculateCurrentNumberOfShares(security) + calculateNumberOfSharesToBuySell(security);
    }

    @Step("5. Calculate new total shares price for {security.name}")
    public static double calculateNewTotalSharePrice(Security security) {
        return calculateNewNumberOfShares(security)  * security.getUnitPrice();
    }
}
