package com.accountabc.tests;

import com.accountabc.model.Security;
import com.accountabc.utils.CsvUtils;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;

import static com.accountabc.service.Calculation.*;
import static com.accountabc.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RebalancingAccountTest {

    static List<Security> securityData() {
        return CsvUtils.readCsv("securities.csv");
    }

    private static String zeroVarianceActionsMessage = "";
    private static BigDecimal actualAccountAbcTotalAsset = BigDecimal.ZERO;

    @ParameterizedTest
    @Description("Verify the rebalancing trade and resulting asset allocation for a security")
    @MethodSource("securityData")
    void verifyRebalancingTrade(Security security) {
        if (security.getTargetVariance().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal tradeQuantity = calculateNumberOfSharesToTrade(security).abs();
            String action = ((security.getTargetVariance().compareTo(BigDecimal.ZERO) < 0) ? "buy " : "sell ");
            zeroVarianceActionsMessage += "\n- " + action + tradeQuantity + " shares of " + security.getName()  + " security ";
            System.out.println("Number of shares to " + action + "for " + security.getName() + " security is " + tradeQuantity);
        } else {
            System.out.println("No deviation for " + security.getName() + " security, no buy or sell action needed");

        }

        BigDecimal actualTotalValueForSecurity = calculateActualTotalValue(security);
        BigDecimal actualPercentOfTotalAssetsForSecurity = actualTotalValueForSecurity.divide(TOTAL_ASSET, SCALE, ROUNDING).multiply(BigDecimal.valueOf(100));
        assertEquals(0, security.getTarget().compareTo(actualPercentOfTotalAssetsForSecurity),
                "Actual % of total assets is different than target % of total assets for " + security.getName() + "\nTarget % of total assets is " + security.getTarget() + "\nActual % of total assets is " + actualPercentOfTotalAssetsForSecurity.stripTrailingZeros().toPlainString());
        actualAccountAbcTotalAsset = actualAccountAbcTotalAsset.add(actualTotalValueForSecurity);
    }

    @AfterAll
    @Description("Verify if total asset is still $100,000 after buying and selling shares")
    static void verifyActualAccountAbcTotalAsset() {
        System.out.println("\nTo get to zero target variance, I have to:" + zeroVarianceActionsMessage);
        actualAccountAbcTotalAsset = actualAccountAbcTotalAsset.setScale(2, ROUNDING);
        BigDecimal totalAssetDiff = TOTAL_ASSET.subtract(actualAccountAbcTotalAsset).abs();

        assertTrue(totalAssetDiff.compareTo(BigDecimal.valueOf(0.01)) <= 0,
                "Initial and actual Account ABC total assets differ by more than 0.01!\nInitial total asset: " + TOTAL_ASSET + "\nActual total asset (after trading): " + actualAccountAbcTotalAsset + "\nDifference: " + totalAssetDiff);
        System.out.println("\nZero target variance achieved.\nAccount ABC total assets after buying and selling shares: $" + actualAccountAbcTotalAsset.setScale(0, ROUNDING));
    }
}
