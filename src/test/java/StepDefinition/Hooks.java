package StepDefinition;

import ProjectBase.TestBaseClass;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    TestBaseClass testBaseClass = new TestBaseClass();

    @Before()
    public void setup(){

        testBaseClass.SuiteBeforeMethods();
    }

    @After()
    public void teardown(){
        testBaseClass.teardown();
    }

    @AfterStep()
    public void addScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) testBaseClass.getDriver();
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }
}
