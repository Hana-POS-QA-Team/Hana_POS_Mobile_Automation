package PageObjects;

import ProjectBase.TestBaseClass;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultPage extends TestBaseClass {

    public SearchResultPage() {
        PageFactory.initElements(getDriver(),this);
    }

    @FindBy(xpath = "//a[@class='product-name']")
    private List <WebElement> Search_Result_Product_Names;



    public void Click_the_searched_product(String productName){
        for(int i = 0; i<Search_Result_Product_Names.size(); i++){
            WebElement product = Search_Result_Product_Names.get(i);
            if(product.getText().equals(productName)){
                product.click();
            }

        }
    }

    public boolean Verify_the_searched_product_IsDisplayed(String productName){
        for(int i = 0; i<Search_Result_Product_Names.size(); i++){
            WebElement product = Search_Result_Product_Names.get(i);
            if(product.getText().equals(productName)){
                return true;
            }

        }
        return false;
    }

}






