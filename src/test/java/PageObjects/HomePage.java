package PageObjects;

import ProjectBase.TestBaseClass;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class HomePage extends TestBaseClass {
    public HomePage() {
        PageFactory.initElements(getDriver(),this);
    }
    @FindBy(id = "txtSearch")
    private WebElement Search_TextBox;

    @FindBy(id = "BtnSearch")
    private WebElement Search_Icon;

    public void Search_and_sort_product(String productName){
        Web_Clear_and_Type(Search_TextBox,productName);
    }

    public void Click_Search_Icon(){
        clickElement(Search_Icon);
    }
}
