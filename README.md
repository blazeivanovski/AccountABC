Technical assessment exercise

Tools and dependencies used for writing automated test:
- Java (java version 21.0.10)
- Maven build and dependency management tool (Apache Maven 3.9.14)
- JUnit testing framework (5.10.2)
- OpenCSV 5.9
- Lombok 1.18.34
- Allure reporting (2.24.0)
- SLF4J and Logback logging

Test case: Calculate number of shares to buy and sell and verify if total asset is still $100,000 after buying and selling shares.

Test steps:
Repeat these test steps for each security from [securities.csv](https://github.com/blazeivanovski/AccountABC/blob/master/src/main/resources/securities.csv)
1. Calculate current total price of shares per security
  https://github.com/blazeivanovski/AccountABC/blob/a32e22d3728526fb92ad3aaf3d2b61c4890d4952/src/main/java/com/accountabc/utils/CalculationService.java#L10
2. Calculate current number of shares per security
  https://github.com/blazeivanovski/AccountABC/blob/a32e22d3728526fb92ad3aaf3d2b61c4890d4952/src/main/java/com/accountabc/utils/CalculationService.java#L15
3. Calculate number of shares to buy and sell per security
  https://github.com/blazeivanovski/AccountABC/blob/a32e22d3728526fb92ad3aaf3d2b61c4890d4952/src/main/java/com/accountabc/utils/CalculationService.java#L20
4. Calculate new total number of shares per security
  https://github.com/blazeivanovski/AccountABC/blob/a32e22d3728526fb92ad3aaf3d2b61c4890d4952/src/main/java/com/accountabc/utils/CalculationService.java#L25
5. Calculate new total price of shares per security
  https://github.com/blazeivanovski/AccountABC/blob/a32e22d3728526fb92ad3aaf3d2b61c4890d4952/src/main/java/com/accountabc/utils/CalculationService.java#L30
6. Validate if new total price of shares for security (after buying/selling shares) equals expected (target) total price of shares for that security
  https://github.com/blazeivanovski/AccountABC/blob/a32e22d3728526fb92ad3aaf3d2b61c4890d4952/src/test/java/com/accountabc/tests/AccountTest.java#L39
7. Calculate new AccountABC total asset (after buying and selling shares)
  https://github.com/blazeivanovski/AccountABC/blob/a32e22d3728526fb92ad3aaf3d2b61c4890d4952/src/test/java/com/accountabc/tests/AccountTest.java#L40

After iterating through all securities:
8. Verify if account ABC is still with $100K in total asset
  https://github.com/blazeivanovski/AccountABC/blob/a32e22d3728526fb92ad3aaf3d2b61c4890d4952/src/test/java/com/accountabc/tests/AccountTest.java#L44
