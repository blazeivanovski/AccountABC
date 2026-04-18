package com.accountabc.utils;

import com.accountabc.Base;
import com.accountabc.model.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculationService extends Base {

    // Step 1. Calculate current total price of shares for security
    public static double calculateCurrentTotalPriceOfShares(Security security) {
        return (double) security.getCurrent() / 100 * initialTotalAsset;
    }

    // Step 2. Calculate current number of shares for security
    public static double calculateCurrentNumberOfShares(Security security) {
        return calculateCurrentTotalPriceOfShares(security) / security.getUnitPrice();
    }

    // Step 3. Calculate number of shares to buy and sell for security
    public static double calculateNumberOfSharesToBuySell(Security security) {
        return calculateCurrentNumberOfShares(security)  / security.getCurrent() * security.getTargetVariance() * -1;
    }

    // Step 4. Calculate new total number of shares for security
    public static double calculateNewNumberOfShares(Security security) {
        return calculateCurrentNumberOfShares(security) + calculateNumberOfSharesToBuySell(security);
    }

    // Step 5. Calculate new total price of shares for security
    public static double calculateNewTotalSharePrice(Security security) {
        return calculateNewNumberOfShares(security)  * security.getUnitPrice();
    }

    // Step 6. Validate if new total price of shares for security (after buying/selling shares) equals expected (target) total price of shares for that security
    public static void validateNewTotalSharePrice(Security security, double newTotalSharePriceForSecurity) {
        double targetTotalSharePriceForSecurity = (double) security.getTarget() / 100 * initialTotalAsset;
        assertEquals(newTotalSharePriceForSecurity, targetTotalSharePriceForSecurity, 0.001, "New Total Price Of Shares is different than target for " + security.getName() + "\nTarget total share price is: " + targetTotalSharePriceForSecurity + "\nNew total share price is: " + newTotalSharePriceForSecurity);
    }
}
