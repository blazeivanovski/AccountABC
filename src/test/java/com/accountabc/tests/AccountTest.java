package com.accountabc.tests;

import com.accountabc.Base;
import com.accountabc.model.Security;
import com.accountabc.utils.CsvUtils;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.accountabc.utils.CalculationService.*;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest extends Base {

    static List<Security> securityData() {
        return CsvUtils.readCsv("securities.csv");
    }

    private static double newAccountAbcTotalAsset = 0;

    @ParameterizedTest
    @Description("Calculate number of shares to buy and sell. Validate if target variance is zero. Verify if total asset is still $100,000 after buying and selling shares.")
    @MethodSource("securityData")
    void validateSharesPerSecurity(Security security) {
        double numberOfSharesToBuySell;
        if (security.getTargetVariance()!= 0) {
            numberOfSharesToBuySell =  calculateNumberOfSharesToBuySell(security);
            logInfo("Number of shares to " + ((security.getTargetVariance() < 0)? "buy" : "sell") + " for " + security.getName() + " is " + Math.abs(numberOfSharesToBuySell));
        } else {
            logInfo("No deviation for " + security.getName() + ", no need to buy or sell shares.");
        }

        double newTotalSharePriceForSecurity = calculateNewTotalSharePrice(security);
        double targetTotalSharePriceForSecurity = (double) security.getTarget() / 100 * initialTotalAsset;
        // 6. Validate if new total price of shares for security (after buying/selling shares) equals expected (target) total price of shares for that security
        assertEquals(newTotalSharePriceForSecurity, targetTotalSharePriceForSecurity, 0.001, "New Total Price Of Shares is different than target for " + security.getName() + "\nTarget total share price is: " + targetTotalSharePriceForSecurity + "\nNew total share price is: " + newTotalSharePriceForSecurity);
        newAccountAbcTotalAsset += newTotalSharePriceForSecurity;
    }

    @AfterAll
    static void verifyNewAccountAbcTotalAsset() {
        System.out.println("New AccountABC total asset (after shares buying and selling): " + newAccountAbcTotalAsset);
        assertEquals(newAccountAbcTotalAsset, initialTotalAsset, 0.001, "New AccountABC Total Asset (after buying and selling shares) is different than initial total asset!\nInitial total asset was: " + initialTotalAsset + "\nNew total asset is:" + newAccountAbcTotalAsset);
    }
}
