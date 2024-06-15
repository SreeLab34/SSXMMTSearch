package demo;

import java.lang.Thread;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestCases {
    // private static RemoteWebDriver driver;
    ChromeDriver driver;
    public TestCases() throws MalformedURLException {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");
        driver = new ChromeDriver(options);
        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void testCase01() {
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.makemytrip.com/");

        // Get the current URL
        String currentURL = driver.getCurrentUrl();

        // Verify that the URL contains "makemytrip"
        if (currentURL.contains("makemytrip")) {
            System.out.println("Test case passed: URL contains 'makemytrip'");
        } else {
            System.out.println("Test case failed: URL does not contain 'makemytrip'");
        }

        System.out.println("End Test case: testCase01");
    }


    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
        driver.get("https://www.makemytrip.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#fromCity")));
        try {
            WebElement notificationFrame = driver
                    .findElement(By.id("webklipper-publisher-widget-container-notification-frame"));
            driver.switchTo().frame(notificationFrame);
            System.out.println("Entered the notification frame");
            WebElement closeNotificationButton = driver.findElement(By
                    .cssSelector("#webklipper-publisher-widget-container-notification-close-div"));
            closeNotificationButton.click();
            driver.switchTo().defaultContent(); // Switch back to the main content
            System.out.println("Exited the notification frame");
        } catch (Exception e) {
            System.out.println("No notification frame found or couldn't close it.");
        }

        try {
            WebElement notificationFrame = driver.findElement(By.xpath(
                    "//div[@class='imageSliderModal modal displayBlock modalLogin dynHeight personal']"));
            System.out.println("Entered the second notification frame");
            notificationFrame.click();
            System.out.println("Closed the second notification frame");
        } catch (Exception e) {
            System.out.println("No second notification frame found or couldn't close it.");
        }

        // Select the departure location (BLR)
        WebElement fromCity = driver.findElement(By.xpath("//*[@id='fromCity']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fromCity);
        fromCity.sendKeys("blr");
        WebElement fromCityInput = driver.findElement(By.xpath("//input[@placeholder='From']"));
        fromCityInput.sendKeys("blr");
        Thread.sleep(1000);
        fromCityInput.sendKeys(Keys.DOWN);
        fromCityInput.sendKeys(Keys.ENTER);
        System.out.println("Selected Bangalore");
        WebElement from = driver.findElement(By.xpath("(//span[@class='truncate airPortName '])[1]"));
        if (from.getText().contains("BLR")) {
            System.out.println("blr found successfully");
        }
        // Select the arrival location (DEL)
        WebElement toCity = driver.findElement(By.xpath("//*[@id='toCity']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toCity);
        toCity.sendKeys("del");
        WebElement toCityInput = driver.findElement(By.xpath("//input[@placeholder='To']"));
        toCityInput.sendKeys("del");
        Thread.sleep(1000);
        toCityInput.sendKeys(Keys.DOWN);
        toCityInput.sendKeys(Keys.ENTER);
        System.out.println("Selected del");
        Thread.sleep(3000);
        // Select the correct date (29th of next month)
        WebElement dateInput = driver.findElement(By.xpath("//span[text()='Departure']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateInput);
        Thread.sleep(3000);

        // WebElement nextMonth = driver.findElement(By.xpath("//span[@aria-label='Next Month']"));
        // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextMonth);
        // Thread.sleep(3000);

        WebElement selectDate = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[@class='DayPicker-Day']//p[text()='29'])[2]")));
        if (selectDate.getText().contains("29")) {
            System.out.println("date found successfully");
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectDate);
        Thread.sleep(3000);

        System.out.println("Selected date");

        // Click on the search button
        WebElement searchButton =
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.primaryBtn")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
        System.out.println("Clicked Search button");
        try {
            Thread.sleep(20000); // wait for captcha if necessary
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File screensho = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destinatinFile = new File("kaaaa.png");
        try {
            FileHandler.copy(screensho, destinatinFile);
            System.out.println("Screenshot saved at: " + destinatinFile.getAbsolutePath());
        } catch (IOException e) {

            e.printStackTrace();
        }

        try {
            WebElement notificationFrame = driver
                    .findElement(By.xpath("//*[@id='root']/div/div[2]/div[2]/div[2]/div/span"));
            System.out.println("Entered the second notification frame");
            notificationFrame.click();
            System.out.println("Closed the second notification frame");
        } catch (Exception e) {
            System.out.println("No second notification frame found or couldn't close it.");
        }
        Thread.sleep(5000);


        try {
            System.out.println("*********************");
            List<WebElement> priceElements = driver.findElements(By.xpath(
                    "//div[contains(@class, 'clusterViewPrice')]//span[contains(@class, 'fontSize18')]"));
            Thread.sleep(5000);
            if (priceElements.size() > 0) {
                System.out.println("Extracted prices: " + priceElements.get(0).getText());
            }
            List<WebElement> adultElements = driver
                    .findElements(By.xpath("//p[@class='fontSize12 darkText lightFont lh14']"));
            Thread.sleep(5000);
            if (adultElements.get(0).getText().contains("adult")) {
                System.out.println("adult found");
            }
        } catch (Exception e) {
            System.out.println("Could not find the flight price element.");
            // e.printStackTrace();
        }
        System.out.println("End Test case: testCase02");
    }



    public void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        driver.get("https://www.makemytrip.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Click on the Train tab using JavascriptExecutor
        WebElement trainTab = driver.findElement(By.xpath(
                "//a[@href='https://www.makemytrip.com/railways/' and contains(@class, 'headerIcons')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", trainTab);



        // Select the departure location (YPR)
        WebElement fromCity = driver.findElement(By.xpath("//*[@id='fromCity']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fromCity);
        // WebElement fromCityInput = driver.findElement(By.xpath("//span[@class='appendBottom2' and
        // text()='From']"));
        fromCity.sendKeys("ypr");
        WebElement fromCityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[@id='react-autowhatever-1-section-0-item-0']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fromCityOption);
        System.out.println("Selected YPR");


        // Select the arrival location (NDLS)
        WebElement toCity = driver.findElement(By.xpath("//*[@id='toCity']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toCity);
        // WebElement toCityInput = driver.findElement(By.xpath("//span[@class='appendBottom2' and
        // text()='To']"));
        toCity.sendKeys("ndls");
        WebElement toCityOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[@id='react-autowhatever-1-section-0-item-0']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toCityOption);
        System.out.println("Selected NDLS");


        // Select the correct date (29th of next month)
        WebElement dateInput = driver.findElement(By.xpath("//span[text()='Travel Date']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateInput);
        // dateInput.click();
        WebElement nextMonth = driver.findElement(By.cssSelector("#travelDate"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextMonth);
        WebElement selectDate =
                driver.findElement(By.xpath("(//div[contains(@aria-label, '29')])[1]"));
        if (selectDate.getText().contains("29")) {
            System.out.println("date found successfully");
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectDate);
        // selectDate.click();
        System.out.println("Selected Date");


        // Select the class as 3AC
        WebElement select =
                driver.findElement(By.xpath("//span[@class='appendBottom5 downArrow']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", select);
        WebElement thirdAC =
                driver.findElement(By.xpath("//li[@data-cy='3A' and text()='Third AC']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", thirdAC);


        // Click on the search button for the train using JavascriptExecutor
        WebElement searchButton = driver.findElement(By.xpath(
                "//a[@data-cy='submit' and contains(@class, 'primaryBtn') and contains(@class, 'font24') and contains(@class, 'latoBold') and contains(@class, 'widgetSearchBtn')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
        System.out.println("Clicked Search");

        // Wait for the search results to load
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.train-list")));
        try {
            // Store the train price for 3AC
            WebElement trainPrice = driver.findElement(By.xpath(
                    "(//div[@id='train_options_26-07-2024_0']/div/div[@class= 'ticket-price justify-flex-end']/text()[2])[1]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String trainPriceText =
                    (String) js.executeScript("return arguments[0].textContent;", trainPrice);
            System.out.println("Train Price: " + trainPriceText);
        } catch (Exception e) {
            System.out.println("Could not find the flight price element.");
        }
        System.out.println("End Test case: testCase03");
    }



    public void testCase04() throws InterruptedException {
        System.out.println("Start Test case: testCase04");
        driver.get("https://www.makemytrip.com/bus-tickets/");
        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            WebElement notificationFrame = driver
                    .findElement(By.id("webklipper-publisher-widget-container-notification-frame"));
            driver.switchTo().frame(notificationFrame);
            System.out.println("Entered the notification frame");
            WebElement closeNotificationButton = driver.findElement(By
                    .cssSelector("#webklipper-publisher-widget-container-notification-close-div"));
            closeNotificationButton.click();
            driver.switchTo().defaultContent(); // Switch back to the main content
            System.out.println("Exited the notification frame");
        } catch (Exception e) {
            System.out.println("No notification frame found or couldn't close it.");
        }

        try {
            WebElement notificationFrame = driver.findElement(By.xpath(
                    "//span[@data-cy='closeModal' and contains(@class, 'commonModal__close')]"));
            System.out.println("Entered the second notification frame");
            notificationFrame.click();
            System.out.println("Closed the second notification frame");
        } catch (Exception e) {
            System.out.println("No second notification frame found or couldn't close it.");
        }

        try {
            // Select Bangalore as the departure location for buses
            WebElement fromCity = driver.findElement(By.xpath("//*[@id='fromCity']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fromCity);
            WebElement fromCityInput = driver.findElement(By.xpath("//input[@placeholder='From']"));
            fromCityInput.sendKeys("bangl");
            Thread.sleep(1000);
            fromCityInput.sendKeys(Keys.DOWN);
            fromCityInput.sendKeys(Keys.ENTER);
            System.out.println("Selected Bangalore");


            // Select Kathmandu as the arrival location for buses
            WebElement toCity = driver.findElement(By.xpath("//*[@id='toCity']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toCity);
            WebElement toCityInput = driver.findElement(By.xpath("//input[@placeholder='To']"));
            toCityInput.sendKeys("kathma");
            Thread.sleep(1000);
            toCityInput.sendKeys(Keys.DOWN);
            toCityInput.sendKeys(Keys.ENTER);
            System.out.println("Selected katmandu");
            Thread.sleep(3000);

            try {
                // Select the travel date
                WebElement dateInput = driver.findElement(By.xpath("//span[text()='Travel Date']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateInput);
                System.out.println("Clicked travel date");
                Thread.sleep(6000);
                WebElement selectDate = driver.findElement(By.xpath(
                        "//*[@id='top-banner']/div[2]/div/div/div[2]/div/div[3]/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div[3]/div[5]/div[2]"));
                Thread.sleep(6000);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectDate);
                Thread.sleep(4000);
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
                // Check if the date is the 29th and click it
                if (selectDate.getText().contains("29")) {
                    System.out.println("Date found successfully: " + selectDate.getText());
                    // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
                } else {
                    System.out.println("Desired date not found.");
                }
            } catch (Exception w) {
                System.out.println("Desired date not found.");

            }


            // Click on the search button
            WebElement searchButton =
                    driver.findElement(By.xpath("//button[contains(text(),'Search')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchButton);
            System.out.println("Clicked on search");

            // Wait for the search results to load
            WebElement noBus = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//span[@class='error-title']")));
            if (noBus.getText().contains("No buses found")) {
                System.out.println("No buses found for 29th of next month");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("End Test case: testCase04");
    }
}

// File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
// File destinationFile = new File("kaaaa.png");
// try {
// FileHandler.copy(screenshot, destinationFile);
// System.out.println("Screenshot saved at: " + destinationFile.getAbsolutePath());
// } catch (IOException e) {

// e.printStackTrace();
// }
