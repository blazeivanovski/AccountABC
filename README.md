# Technical assessment exercise

### Project URL
https://github.com/blazeivanovski/AccountABC.git

### Tools and dependencies used for writing automated test
- Java (java version 21.0.10)
- Maven build and dependency management tool (Apache Maven 3.9.14)
- JUnit testing framework (5.10.2)
- OpenCSV 5.9
- Lombok 1.18.34
- Allure reporting (2.24.0)
- SLF4J and Logback logging

### Test case
Calculate number of shares to buy and sell and verify if total asset is still $100,000 after buying and selling shares.

### Test steps
Repeat these test steps for each security from [securities.csv](https://github.com/blazeivanovski/AccountABC/blob/master/src/main/resources/securities.csv)
1. Calculate current total price of shares per security<br>
  [calculateCurrentTotalSharesValue](https://github.com/blazeivanovski/AccountABC/blob/master/src/main/java/com/accountabc/CalculationService.java#L7)
2. Calculate current number of shares per security<br>
  [calculateCurrentNumberOfShares](https://github.com/blazeivanovski/AccountABC/blob/master/src/main/java/com/accountabc/CalculationService.java#L11)
3. Calculate number of shares to buy and sell per security<br>
  [calculateNumberOfSharesToBuySell](https://github.com/blazeivanovski/AccountABC/blob/master/src/main/java/com/accountabc/CalculationService.java#L15)
4. Calculate new total number of shares per security<br>
  [calculateNewNumberOfShares](https://github.com/blazeivanovski/AccountABC/blob/master/src/main/java/com/accountabc/CalculationService.java#L19)
5. Calculate new total price of shares per security<br>
  [calculateNewTotalSharesValue](https://github.com/blazeivanovski/AccountABC/blob/master/src/main/java/com/accountabc/CalculationService.java#L23)
6. Validate if new % of total assets for security (after buying/selling shares) equals target % of total assets for that security<br>
  [assertEquals(newPercentOfTotalAssetsForSecurity, security.getTarget())](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/tests/RebalancingAccountTest.java#L40)
7. Calculate Account ABC's new total asset (after buying and selling shares)<br>
  [newAccountAbcTotalAsset](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/tests/RebalancingAccountTest.java#L41)<br><br>
After iterating through all securities:<br>
8. Verify if account ABC is still with $100K in total asset<br>
  [verifyNewAccountAbcTotalAsset](https://github.com/blazeivanovski/AccountABC/blob/master/src/test/java/com/accountabc/tests/RebalancingAccountTest.java#L46)

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
Account ABC total assets after buying and selling shares: $100000.0
```
