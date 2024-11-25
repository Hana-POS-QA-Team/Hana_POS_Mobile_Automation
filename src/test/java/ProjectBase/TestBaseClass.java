package ProjectBase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static Utilities.ConfigReader.getProperty;

public class TestBaseClass {

    public static AppiumDriver driver;
    private static final String CHROMEDRIVER_PATH = System.getProperty("user.dir")+"\\src\\test\\resources\\Drivers\\chromedriver.exe";
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
    public static void launchAndroidApp(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:platformVersion", "15");
        caps.setCapability("appium:deviceName", "Saka");
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

    public static void Web_Clear_and_Type(WebElement element, String text){
        try {
            // Clear the existing text
            element.clear();

            // Type the new text
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("Failed to clear and type into the input box: " + e.getMessage());
        }
    }

    public static void clickElement(WebElement ele){
        ele.click();
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
                    .withAppiumJS(new File("C:\\Users\\Sakrateesh\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")); // Path to Appium

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

    public static void teardown(){
        stopServer();
        closeEmulator();
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
    public static void closeEmulator() {
        try {
            // Stop the emulator using adb command
            String command = "adb -s "+getProperty("avdName")+" emu kill"; // Replace "emulator-5554" with your emulator's ID
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor(); // Wait for the process to finish

            System.out.println("Emulator closed successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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
}