//
//import PageObjects.HomePage;

import PageObjects.HomePage;
import PageObjects.SearchResultPage;
import ProjectBase.TestBaseClass;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class SampleTest extends TestBaseClass {
    private HomePage homePage;
    private SearchResultPage searchResultPage;

    @Test
    public void testGoogle() {
        getDriver().get("https://bestfloristhana.azurewebsites.net/");
        System.out.println("Page title: " + getDriver().getTitle());
        homePage = new HomePage();
        homePage.Search_and_sort_product("Balaji_Test_Product");
        homePage.Click_Search_Icon();

        searchResultPage = new SearchResultPage(driver);
        Assert.assertTrue(searchResultPage.Verify_the_searched_product_IsDisplayed("Balaji_Test_Product"),"Searched Product is not displaying");
        searchResultPage.Click_the_searched_product("Balaji_Test_Product");


    }
}
