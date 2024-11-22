package ProjectBase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class TestBaseClass {

    public static AppiumDriver driver;
    private static final String CHROMEDRIVER_PATH = System.getProperty("user.dir")+"\\src\\test\\resources\\Drivers\\chromedriver.exe";

    @BeforeSuite(groups = {"Smoke", "Sanity", "Regression"})
    public void SuiteBeforeMethods(){
        System.out.println(CHROMEDRIVER_PATH);
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
}