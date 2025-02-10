package ProjectBase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static Utilities.ConfigReader.getProperty;

public class TestBaseClass {

    public static AppiumDriver driver;
    private static final String CHROMEDRIVER_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\Drivers\\chromedriver.exe";
    private static AppiumDriverLocalService service;

    public void SuiteBeforeMethods() {
        startServer();
        System.out.println(CHROMEDRIVER_PATH);
//        launchEmulator();
//        try {
//            Thread.sleep(5000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        launchAndroidApp();
    }

    /**
     * This method is used to launch the Android mobile app with opening chrome applicaiton
     */
    public static void launchAndroidApp() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:platformVersion", "15");
        caps.setCapability("appium:deviceName", "AutomationEmulator");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("appium:chromedriverExecutable", CHROMEDRIVER_PATH);
        caps.setCapability("appium:ensureWebviewsHavePages", true);
        caps.setCapability("appium:nativeWebScreenshot", true);
        caps.setCapability("appium:newCommandTimeout", 3600);
        caps.setCapability("appium:connectHardwareKeyboard", true);

        try {
            // Initialize Appium Driver
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);

            // Perform actions here if needed

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void fluentWait(WebElement ele) {
        Wait<WebDriver> wait = null;
        try {
            wait = new FluentWait<WebDriver>((WebDriver) getDriver())
                    .withTimeout(Duration.ofSeconds(60))
                    .pollingEvery(Duration.ofSeconds(3))
                    .ignoring(Exception.class);
            wait.until(ExpectedConditions.visibilityOf(ele));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void explicitWait(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(ele));
    }

    public void clickAndType(WebElement ele, String data) {
        try {
            fluentWait(ele);
            ele.clear();
            HighlightElement(ele);
            ele.click();
            ele.sendKeys(data);
        } catch (StaleElementReferenceException e) {
            throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static void Web_Clear_and_Type(WebElement element, String text) {
        try {
            // Clear the existing text
            element.clear();

            // Type the new text
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("Failed to clear and type into the input box: " + e.getMessage());
        }
    }

    public static void clickElement(WebElement ele) {
        ele.click();
    }

    /**
     * This function will highlight the element displayed with scroll action
     *
     * @param ele Element to be interact
     * @Author Balaji N
     */
    public static void HighlightElement(WebElement ele) {
        fluentWait(ele);
        JavascriptExecutor JS = (JavascriptExecutor) getDriver();
        JS.executeScript("arguments[0].scrollIntoView();", ele);
        JS.executeScript("arguments[0].style.border='3px solid red'", ele);
    }

    public void dropDown(WebElement ele, String value, String usingmethod) {
        Select select = new Select(ele);

        try {
            //	HighlightElement(ele);
            switch (usingmethod) {
                case "index":
                    select.selectByIndex(Integer.parseInt(value));
                    break;
                case "value":
                    select.selectByValue(value);
                    break;
                case "VisibleText":
                    jsClick(ele);
                    select.selectByVisibleText(value);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    public void jsScrollClick(WebElement ele) {
        try {
            fluentWait(ele);
            HighlightElement(ele);
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", ele);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jsClick(WebElement ele) {
        try {
            HighlightElement(ele);
            fluentWait(ele);
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", ele);
        } catch (Exception e) {
            Assert.fail("Unable to click the element using js click method");
            throw new RuntimeException();
        }
    }

    public static void delayWithGivenTime(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static String CurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedCurrentDate = currentDate.format(formatter);
        return (formattedCurrentDate);
    }

    public static void click(WebElement ele) {
        try {
            HighlightElement(ele);
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.click();
        } catch (Exception e) {
        }
    }

    public void jsDatePicker(WebElement ele, String dateval) {
        try {
            HighlightElement(ele);
            JavascriptExecutor JS = (JavascriptExecutor) getDriver();
            JS.executeScript("arguments[0].setAttribute('value','" + dateval + "');", ele);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDriver(AppiumDriver appiumDriver) {
        driver = appiumDriver;
    }

    /**
     * Gets the current AppiumDriver instance.
     *
     * @return The AppiumDriver instance.
     */
    public static AppiumDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized. Call setDriver() first.");
        }
        return driver;
    }

    public static void startServer() {
        if (service == null) {
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .withIPAddress("127.0.0.1")  // Localhost
                    .usingPort(4723)             // Default Appium port
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)  // Override existing sessions
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "info") // Set log level
                    .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe")) // Path to Node.js
                    .withAppiumJS(new File(System.getProperty("user.home")
                            + "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")); // Path to Appium

            // Initialize the Appium service
            service = AppiumDriverLocalService.buildService(builder);
        }

        // Start the server
        if (!service.isRunning()) {
            service.start();
            System.out.println("Appium Server started!");
        }
    }

    /**
     * Stops the Appium server.
     */
    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            System.out.println("Appium Server stopped!");
        }
    }

    public static void teardown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
        stopServer();
        //closeEmulator();

    }

    public void launchEmulator() {
        try {
            // Replace with the name of your Android Emulator AVD
            String avdName = getProperty("avdName");

            // Start the emulator via the command line
            String command = "emulator -avd " + avdName;

            // Execute the command to launch the emulator
            Process process = Runtime.getRuntime().exec(command);

            // Wait for a few seconds to give the emulator time to start

            Thread.sleep(2000); // Adjust as needed
            waitForEmulatorToBoot();
            System.out.println("Emulator launched successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Function to close the emulator
    public static void closeEmulator() {
        try {
            // Get the emulator ID dynamically from adb devices
            String emulatorId = getEmulatorId();
            if (emulatorId == null || emulatorId.isEmpty()) {
                System.out.println("No emulator is currently running.");
                return;
            }

            // Form the command to close the emulator
            String command = "adb -s " + emulatorId + " emu kill";
            System.out.println("Executing command: " + command);

            // Execute the command
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor(); // Wait for the process to complete

            System.out.println("Emulator closed successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Helper function to get the emulator ID
    private static String getEmulatorId() {
        try {
            // Run the adb devices command to list devices
            Process process = Runtime.getRuntime().exec("adb devices");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("emulator-")) {
                    // Return the first emulator ID found
                    return line.split("\\s+")[0];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // No emulator found
    }


    private static void waitForEmulatorToBoot() {
        try {
            // Wait for emulator to be ready (checking if it is responding to ADB commands)
            boolean isEmulatorReady = false;
            while (!isEmulatorReady) {
                // Use adb to check if the emulator is responding
                String adbCommand = "adb shell getprop sys.boot_completed";
                Process process = Runtime.getRuntime().exec(adbCommand);
                process.waitFor();

                // Check if the emulator has booted successfully
                String output = new String(process.getInputStream().readAllBytes()).trim();
                if ("1".equals(output)) {
                    isEmulatorReady = true;  // Emulator is ready
                } else {
                    System.out.println("Waiting for emulator to finish booting...");
                    Thread.sleep(2000);  // Wait for a short time before retrying
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to launch the emulator
    public static void startEmulator() {
        try {
            String avdName = getProperty("avdName");
            System.out.println("Starting emulator: " + avdName);


            // Command to launch the emulator
            String command = "emulator -avd " + avdName;

            // Execute the command
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Monitor the output to ensure it's starting
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("boot completed")) {
                    System.out.println("Emulator started successfully.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to start the emulator. Ensure AVD name is correct.");
        }
    }

    // Method to start the Appium server
    public static void startAppiumServer() {
        try {
            System.out.println("Starting Appium server...");

            // Command to start the Appium server
            String command = "appium";

            // Execute the command
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Monitor the output to ensure it's running
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("Appium REST http interface listener started")) {
                    System.out.println("Appium server started successfully.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to start the Appium server. Ensure Appium is installed correctly.");
        }
    }

}