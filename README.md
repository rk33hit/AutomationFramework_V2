# AutomationFramework_V2

A production-grade Selenium Test Automation Framework built with Java, 
TestNG, and Jenkins CI/CD Pipeline.

## Tech Stack
- Language: Java 17
- Test Framework: TestNG
- Automation: Selenium WebDriver 4.18.1
- Build Tool: Apache Maven
- Reporting: ExtentReports + JUnit XML
- Data Driven: Apache POI (Excel)
- Version Control: Git + GitHub
- CI/CD: Jenkins Pipeline

## Project Structure
src/test/java
├── com.banking.base       → BaseClass (WebDriver setup/teardown)
├── com.banking.pages      → Page Object Model (LoginPage)
├── com.banking.tests      → Test Classes (LoginTest)
└── com.banking.utils      → ExcelReader, DataProviders, TestListener
src/test/resources
└── testng.xml             → TestNG suite configuration

## Framework Features
- Page Object Model (POM) design pattern
- Data driven testing using Apache POI
- Valid and invalid login scenario coverage
- Screenshot capture on test failure
- ExtentReports HTML reporting
- Parallel test execution using TestNG
- Jenkins Pipeline with 5 stages
- GitHub webhook auto trigger on every push

## CI/CD Pipeline
Jenkins Pipeline stages:
1. Checkout  — pulls latest code from GitHub
2. Build     — compiles Java source using Maven
3. Test      — runs all 20 Selenium TestNG tests
4. Report    — publishes JUnit XML test results
5. Archive   — saves test output artifacts

GitHub webhook triggers Jenkins automatically on every push.

## Test Results
- Total Tests: 20
- Pass Rate: 100%
- Valid login: verified successful redirect
- Invalid logins: verified correct rejection
- Edge cases: SQL injection, XSS, special characters,
  numeric usernames, null values

## How to Run Locally
1. Clone the repo:
   git clone https://github.com/rk33hit/AutomationFramework_V2.git

2. Navigate to project:
   cd AutomationFramework_V2

3. Run tests:
   mvn clean test

## How to Run via Jenkins
1. Open Jenkins at http://localhost:8080
2. Go to AutomationFramework-Pipeline
3. Click Build Now
   OR just push any code change to GitHub
   — webhook will auto trigger the build

## Author
Rohit | QA Automation Engineer
GitHub: https://github.com/rk33hit