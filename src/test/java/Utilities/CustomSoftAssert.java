package Utilities;

import ProjectBase.TestBaseClass;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import static ProjectBase.TestBaseClass.getDriver;


/**
 * This class is used to capture screenshot on soft assert failure and add it to reports
 *
 * @author Balaji N
 * @Description: This Class overide the onAssertFailure method of Assert class to customize take screenshot on the assert failure
 */
public class CustomSoftAssert extends SoftAssert {
    TestBaseClass base = new TestBaseClass();

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        byte[] screenshot = ((TakesScreenshot)
                getDriver()).getScreenshotAs(OutputType.BYTES);
        Allure.getLifecycle().addAttachment("Screenshot on Failure", "image/png", "png", screenshot);
    }
}


