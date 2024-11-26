package PageObjects;

import ProjectBase.TestBaseClass;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultPage extends TestBaseClass {

    public SearchResultPage() {
        PageFactory.initElements(getDriver(), this);
    }

    @FindBy(xpath = "//a[@class='product-name']")
    private List<WebElement> Search_Result_Product_Names;

    @FindBy(xpath = "//h1[@id='CatName']")
    private WebElement search_result_title;

    @FindBy(xpath = "//a[@class='product-name']")
    private WebElement product_displayed_description;

    public void Click_the_searched_product(String productName) {
        for (int i = 0; i < Search_Result_Product_Names.size(); i++) {
            WebElement product = Search_Result_Product_Names.get(i);
            if (product.getText().equals(productName)) {
                product.click();
            }

        }
    }

    public boolean Verify_the_searched_product_IsDisplayed(String productName) {
        for (int i = 0; i < Search_Result_Product_Names.size(); i++) {
            WebElement product = Search_Result_Product_Names.get(i);
            if (product.getText().equals(productName)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Validates whether the search result page is displayed or not
     *
     * @return If search result page is displayed then true else false
     * Author Balaji N
     */
    public boolean Verify_Search_Result_Page_IsDisplayed() {
        HighlightElement(search_result_title);
        return search_result_title.isDisplayed();
    }

    /**
     * Clicks the product displayed on search result page
     * <p>
     * This function will click the product displayed on search result page
     * Author Balaji N
     */
    public void Click_Product_Displayed_On_Search_Result_Page() {
        jsClick(product_displayed_description);
    }


}






