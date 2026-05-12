# Technical assessment exercise

### GitHub repository
https://github.com/blazeivanovski/AccountABC.git

### Tech Stack & Dependencies

The automated test framework was built using the following tools and libraries:

- **Java** — version 21.0.10
- **Apache Maven** — version 3.9.14
- **JUnit 5** — version 5.10.2
- **OpenCSV** — version 5.9
- **Lombok** — version 1.18.34
- **Allure Report** — version 2.24.0

### Test case
Calculate number of shares to buy and sell and verify if total asset is still $100,000 after buying and selling shares.

### Test steps
Repeat these test steps for each security from [securities.csv](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/resources/securities.csv)
1. Calculate current total price of shares per security<br>
  [calculateCurrentTotalValue](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/service/Calculation.java#L11)
2. Calculate current number of shares per security<br>
  [calculateCurrentNumberOfShares](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/service/Calculation.java#L15)
3. Calculate number of shares to buy and sell per security<br>
  [calculateNumberOfSharesToTrade](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/service/Calculation.java#L19)
4. Calculate actual total number of shares per security<br>
  [calculateActualNumberOfShares](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/service/Calculation.java#L23)
5. Calculate actual total price of shares per security<br>
  [calculateActualTotalValue](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/service/Calculation.java#L27)
6. Validate if actual % of total assets for security (after buying/selling shares) equals target % of total assets for that security<br>
  [assertEquals(0, security.getTarget().compareTo(actualPercentOfTotalAssetsForSecurity)](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/tests/RebalancingAccountTest.java#L43)
7. Calculate Account ABC's actual total asset (after buying and selling shares)<br>
  [actualAccountAbcTotalAsset](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/tests/RebalancingAccountTest.java#L45)<br><br>
After iterating through all securities:<br>
8. Verify if account ABC is still with $100K in total asset<br>
  [verifyActualAccountAbcTotalAsset](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/tests/RebalancingAccountTest.java#L50)

### Command to run test 
`mvn clean test`

### Command to run report 
`mvn allure:serve`

Input: [securities.csv](https://github.com/blazeivanovski/AccountABC/blob/master/src/main/resources/securities.csv)

Expected output:
```
Number of shares to buy for IBM security is 66.6667
No deviation for MSFT security, no buy or sell action needed
Number of shares to sell for ORCL security is 45.4545
No deviation for AAPL security, no buy or sell action needed
No deviation for HD security, no buy or sell action needed

To get to zero target variance, I have to:
- buy 66.6667 shares of IBM security 
- sell 45.4545 shares of ORCL security 

Zero target variance achieved.
Account ABC total assets after buying and selling shares: $100000
```
