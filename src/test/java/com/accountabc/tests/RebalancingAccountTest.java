package com.accountabc.tests;

import com.accountabc.Base;
import com.accountabc.model.Security;
import com.accountabc.utils.CsvUtils;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.accountabc.CalculationService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RebalancingAccountTest extends Base {

    static List<Security> securityData() {
        return CsvUtils.readCsv("securities.csv");
    }

    private static String zeroVarianceActionsMessage = "";
    private static double newAccountAbcTotalAsset = 0;

    @ParameterizedTest
    @Description("Calculate number of shares to buy and sell")
    @MethodSource("securityData")
    void validateNumberOfSharesToBuySell(Security security) {
        if (security.getTargetVariance()!= 0) {
            double numberOfSharesToBuySell =  Math.abs(calculateNumberOfSharesToBuySell(security));
            String action = ((security.getTargetVariance() < 0)? "buy " : "sell ");
            zeroVarianceActionsMessage += "\n- " + action + String.format("%.4f", numberOfSharesToBuySell) + " shares of " + security.getName()  + " security ";
            logInfo("Number of shares to " + action + "for " + security.getName() + " security is " + String.format("%.4f", numberOfSharesToBuySell));
        } else {
            logInfo("No deviation for " + security.getName() + " security, no buy or sell action needed");
        }

        double newTotalSharesValueForSecurity = calculateNewTotalSharesValue(security);
        double targetTotalSharesValueForSecurity = (double) security.getTarget() / 100 * initialTotalAsset;
        assertEquals(newTotalSharesValueForSecurity, targetTotalSharesValueForSecurity, 0.001, "New total shares value is different than target for " + security.getName() + "\nTarget total shares value is: " + targetTotalSharesValueForSecurity + "\nNew total shares value is: " + newTotalSharesValueForSecurity);
        newAccountAbcTotalAsset += newTotalSharesValueForSecurity;
    }

    @AfterAll
    @Description("Verify if total asset is still $100,000 after buying and selling shares")
    static void verifyNewAccountAbcTotalAsset() {
        System.out.println("\nTo get to zero target variance, I have to:" + zeroVarianceActionsMessage);
        assertEquals(newAccountAbcTotalAsset, initialTotalAsset, 0.001, "Account ABC total assets after buying and selling shares is different than initial total asset!\nInitial total asset was: " + initialTotalAsset + "\nNew total asset is:" + newAccountAbcTotalAsset);
        System.out.println("\nZero target variance achieved.\nAccount ABC total assets after buying and selling shares: $" + newAccountAbcTotalAsset);
    }
}
