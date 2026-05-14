package com.accountabc.tests;

import com.accountabc.model.Security;
import com.accountabc.utils.CsvUtils;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.accountabc.service.Calculation.*;
import static com.accountabc.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RebalancingAccountTest {

    private static final List<RebalancingResult> allRebalancingResults = Collections.synchronizedList(new ArrayList<>());

    static List<Security> securityData() {
        return CsvUtils.readCsv("securities.csv");
    }

    @ParameterizedTest
    @Description("Verify the rebalancing trade and resulting asset allocation for a security")
    @MethodSource("securityData")
    void verifyRebalancingTrade(Security security) {
        BigDecimal tradeQuantity = BigDecimal.ZERO;
        String action = "no trade";

        if (security.getTargetVariance().compareTo(BigDecimal.ZERO) != 0) {
            tradeQuantity = calculateNumberOfSharesToTrade(security).abs();
            action = ((security.getTargetVariance().compareTo(BigDecimal.ZERO) < 0) ? "buy " : "sell ");
            System.out.println("Number of shares to " + action + "for " + security.getName() + " security is " + tradeQuantity);
        } else {
            System.out.println("No deviation for " + security.getName() + " security, no buy or sell action needed");

        }

        BigDecimal actualTotalValueForSecurity = calculateActualTotalValue(security); // $20.000
        BigDecimal actualPercentOfTotalAssetsForSecurity = actualTotalValueForSecurity.divide(TOTAL_ASSET, SCALE, ROUNDING).multiply(BigDecimal.valueOf(100)); // 20%
        assertEquals(0, security.getTarget().compareTo(actualPercentOfTotalAssetsForSecurity),
                "Actual % of total assets is different than target % of total assets for " + security.getName() + " security.\nTarget % of total assets is " + security.getTarget() + "\nActual % of total assets is " + actualPercentOfTotalAssetsForSecurity.stripTrailingZeros().toPlainString());

        allRebalancingResults.add(new RebalancingResult(
                security.getName(),
                tradeQuantity,
                action,
                actualTotalValueForSecurity
        ));
    }

    @AfterAll
    @Description("Verify if total asset is still $100,000 after buying and selling shares")
    static void verifyActualAccountAbcTotalAsset() {
        BigDecimal aggregatedTotalAsset = BigDecimal.ZERO;
        StringBuilder zeroVarianceActionsMessage = new StringBuilder();

        for (RebalancingResult result : allRebalancingResults) {
            if (!result.action().equals("no trade")) {
                zeroVarianceActionsMessage
                        .append("\n- ")
                        .append(result.action())
                        .append(result.tradeQuantity())
                        .append(" shares of ")
                        .append(result.securityName())
                        .append(" security ");
            }
            aggregatedTotalAsset = aggregatedTotalAsset.add(result.actualTotalValueForSecurity());
        }

        System.out.println("\nTo get to zero target variance, I have to:" + zeroVarianceActionsMessage);
        aggregatedTotalAsset = aggregatedTotalAsset.setScale(2, ROUNDING);
        BigDecimal totalAssetDiff = TOTAL_ASSET.subtract(aggregatedTotalAsset).abs();

        assertTrue(totalAssetDiff.compareTo(BigDecimal.valueOf(ASSERT_DELTA)) <= 0,
                "Initial and actual Account ABC total assets differ by more than $" + ASSERT_DELTA + "\nInitial total asset: $" + TOTAL_ASSET + "\nActual total asset (after trading): $" + aggregatedTotalAsset + "\nDifference: $" + totalAssetDiff);
        System.out.println("\nZero target variance achieved.\nAccount ABC total assets after buying and selling shares: $" + aggregatedTotalAsset.setScale(0, ROUNDING));
    }

    private record RebalancingResult(String securityName, BigDecimal tradeQuantity, String action,
                                         BigDecimal actualTotalValueForSecurity) {
    }
}
