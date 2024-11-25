package StepDefinition;

import PageObjects.HomePage;
import PageObjects.SearchResultPage;
import ProjectBase.TestBaseClass;
import Utilities.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Properties;

import static ProjectBase.TestBaseClass.getDriver;
import static Utilities.ConfigReader.getProperty;

public class Hana_T1049_Web_Checkout_Page_Functionality {
    public static TestBaseClass testBaseClass = new TestBaseClass();
    public static HomePage homePage = new HomePage();
    public ConfigReader configReader = new ConfigReader();
    public static SearchResultPage searchResultPage = new SearchResultPage();

    @Given("Launching the browser from the mobile app and searching for Webapp")
    public void launching_the_browser_from_the_mobile_app_and_searching_for_webapp() {
        System.out.println("Mobile Browser Launched");
    }
    @When("User launches the URL in the Chrome browser in mobile as {string}")
    public void user_launches_the_url_in_the_chrome_browser_in_mobile_as(String url) {
        url = configReader.getProperty("webURL");
        getDriver().get(url);
    }
    @Then("User should land on the e-commerce homepage, and the title should be displayed as {string}")
    public void user_should_land_on_the_e_commerce_homepage_and_the_title_should_be_displayed_as(String WebTitle) {
        WebTitle = getDriver().getTitle();
        System.out.println("Page title: " + getDriver().getTitle());
        Assert.assertEquals(getDriver().getTitle(),WebTitle,"Ecommerce Page title is not matching");
    }

    @When("User enters {string} in the search box")
    public void user_enters_in_the_search_box(String webSearchProduct) {

        webSearchProduct = getProperty("webSearchProduct");
        homePage.Search_and_sort_product(webSearchProduct);
    }
    @Then("The user clicks on the search icon")
    public void the_user_clicks_on_the_search_icon() {
        homePage.Click_Search_Icon();
        System.out.println("User clicked on the Search icon");
    }
    @Then("The user should navigate to the {string}")
    public void the_user_should_navigate_to_the(String WebTitle) {
        WebTitle = getDriver().getTitle();
        System.out.println("Page title: " + getDriver().getTitle());
        Assert.assertEquals(getDriver().getTitle(),WebTitle,"Ecommerce Page title is not matching");
    }
    @Then("The user should be able to see the respective searched {string}")
    public void the_user_should_be_able_to_see_the_respective_searched(String productName) {
        productName = getProperty("webSearchProduct");
        Assert.assertTrue(searchResultPage.Verify_the_searched_product_IsDisplayed(productName),"Searched product is not displaying");

    }

}
