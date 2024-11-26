package StepDefinition;

import PageObjects.*;
import ProjectBase.TestBaseClass;
import Utilities.ConfigReader;
import Utilities.CustomSoftAssert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Properties;

import static ProjectBase.TestBaseClass.*;
import static Utilities.ConfigReader.getProperty;

public class Hana_T1049_Web_Checkout_Page_Functionality {
    public static TestBaseClass testBaseClass = new TestBaseClass();
    public static HomePage homePage = new HomePage();
    public ConfigReader configReader = new ConfigReader();
    public static SearchResultPage searchResultPage = new SearchResultPage();
    public static Add_On_Page addOnPage = new Add_On_Page();
    public static Order_Preview_Page orderPreviewPage = new Order_Preview_Page();
    public static Checkout_Page checkoutPage = new Checkout_Page();
    public static Order_Confirmation_Page order_confirmationPage = new Order_Confirmation_Page();
    public static ProductDetails_Page productDetailsPage = new ProductDetails_Page();
    CustomSoftAssert softassert = new CustomSoftAssert();

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
        softassert.assertEquals(getDriver().getTitle(), WebTitle, "Ecommerce Page title is not matching");
    }

    @When("User enters {string} in the search box")
    public void user_enters_in_the_search_box(String webSearchProduct) {

        webSearchProduct = getProperty("webSearchProduct");
        homePage.Search_and_sort_product(webSearchProduct);
    }

    @Then("The user clicks on the search icon")
    public void the_user_clicks_on_the_search_icon() {
        homePage.Click_Search_Icon();
    }

    @Then("The user should navigate to the {string}")
    public void the_user_should_navigate_to_the(String WebTitle) {
        WebTitle = getDriver().getTitle();
        softassert.assertEquals(getDriver().getTitle(), WebTitle, "Ecommerce Page title is not matching");
    }

    @Then("The user should be able to see the respective searched {string}")
    public void the_user_should_be_able_to_see_the_respective_searched(String productName) {
        productName = getProperty("webSearchProduct");
        softassert.assertTrue(searchResultPage.Verify_the_searched_product_IsDisplayed(productName), "Searched product is not displaying");
    }

    @When("the user clicks the respective searched product it should navigated to product details page")
    public void the_user_clicks_the_respective_searched_product_it_should_navigated_to_product_details_page() {
        searchResultPage.Click_Product_Displayed_On_Search_Result_Page();
        // delayWithGivenTime(2000);
        softassert.assertEquals(productDetailsPage.Verify_Product_Displayed_Description(), getProperty("ecommerce_product"), "Test Step - 4: Product description is not matching with expected product description");
    }

    @When("the user enters or selects all mandatory fields and clicks on the order button")
    public void the_user_enters_selects_all_mandatory_fields_and_clicks_on_the_order_button() {
        //   delayWithGivenTime(2000);
        productDetailsPage.Click_Product_Variant_As_Medium();
        // delayWithGivenTime(1000);
        softassert.assertEquals(productDetailsPage.Verify_Displayed_Product_Price(), getProperty("product_variant_medium_price"), "Test Step - 4: Product variant is not matching with expected product variant");
        // delayWithGivenTime(2000);
        productDetailsPage.Click_Delivery_Tab();
        // delayWithGivenTime(2000);
        productDetailsPage.Click_Delivery_Date_As_Current_Date();
        // delayWithGivenTime(3000);
        //  softassert.assertEquals(productDetailsPage.Validate_Delivery_Date_As_Current_Date_Displayed_In_Green_Colour(), "rgba(0, 128, 0, 1)", "Test Step - 4: Selected Delivery date is not highlighted in green colour");
        productDetailsPage.Enter_Zip_Code(getProperty("ecommerce_zipcode"));
        //   delayWithGivenTime(2000);
        productDetailsPage.Click_Check_Button();
        //  delayWithGivenTime(2000);
        softassert.assertEquals(productDetailsPage.Verify_Zip_Code_Validation_Message(), "Delivery Available 77021", "Test Step - 4: Validation message is not matching with expected validation message");
        //   delayWithGivenTime(2000);
        productDetailsPage.Click_Create_Order_Button();
    }

    @Then("the addons page should be displayed")
    public void the_addons_page_should_be_displayed() {
        //  delayWithGivenTime(2000);
        softassert.assertTrue(addOnPage.verify_add_on_page_title(), "Test Step - 5: Add - On page is not displayed");
        //  delayWithGivenTime(2000);
    }

    @When("the user selects an addon and clicks on the {string} button")
    public void the_user_selects_an_addon_and_clicks_on_the_button(String string) {
        addOnPage.Click_add_on_chocolates_medium_checkbox();
        //   delayWithGivenTime(2000);
        addOnPage.Click_continueToCheckoutBtn();
    }

    @Then("the order preview page should be displayed")
    public void the_order_preview_page_should_be_displayed() {
        //  delayWithGivenTime(3000);
        softassert.assertTrue(orderPreviewPage.Verify_OrderPreviewPage(), "Test Step - 5: Order preview page is not displayed");
    }

    @Then("the sub total of the product {string} with variant price {string} should be displayed properly")
    public void the_sub_total_of_the_product_with_variant_price_should_be_displayed_properly(String string, String string2) {
        //  delayWithGivenTime(2000);
        softassert.assertEquals(orderPreviewPage.get_product_item_and_variant(), "Balaji_Test_Product-Medium", "Test Step - 6: Product item and variant is not matching with expected product item and variant");
        softassert.assertEquals(orderPreviewPage.get_product_qty_and_price(), "1 × $500.00", "Test Step - 6: Product item and variant is not matching with expected product item and variant");
        softassert.assertEquals(orderPreviewPage.get_addon_product_item_and_variant_price(), "Chocolates - Medium Box add-on for Test_Automation", "Test Step - 6: Product item and variant is not matching with expected product item and variant");
        softassert.assertEquals(orderPreviewPage.get_addon_product_qty_and_price(), "1 × $19.99", "Test Step - 6: Product item and variant is not matching with expected product item and variant");
        softassert.assertEquals(orderPreviewPage.get_subtotal_on_order_preview_page(), "$" + orderPreviewPage.calculated_subtotal(), "Test Step - 6: Sub-Total price is not matching with expected total price");
        //  delayWithGivenTime(2000);
    }

    @When("the user clicks on the {string} button")
    public void the_user_clicks_on_the_button(String string) {
        orderPreviewPage.Click_proceed_to_checkout_button();
        //  delayWithGivenTime(3000);
    }

    @Then("the checkout page should be displayed")
    public void the_checkout_page_should_be_displayed() {
        softassert.assertTrue(checkoutPage.Verify_CheckoutPage(), "Test Step - 7: Checkout page is not displayed");
        // delayWithGivenTime(3000);
    }

    @When("the user selects the occasion {string} under the Cart tab")
    public void the_user_selects_the_occasion_under_the_cart_tab(String string) {
        checkoutPage.Select_Occasion(getProperty("cart_occasion"));
        // delayWithGivenTime(1000);
    }

    @Then("the respective occasion value should be displayed in the respective field")
    public void the_respective_occasion_value_should_be_displayed_in_the_respective_field() {
        softassert.assertEquals(checkoutPage.get_selected_occasion_on_cartTab_checkoutPage(), getProperty("cart_occasion"), "Test Step - 7: Selected occasion is not matching with expected occasion");
        //  delayWithGivenTime(1000);
    }

    @When("the user enters the email {string} in the Email field")
    public void the_user_enters_the_email_in_the_email_field(String string) {
        if (checkoutPage.Verify_Message_Popup_isDisplay() == true) {
            checkoutPage.Click_on_outside_from_messagePopup();
        }

        checkoutPage.Enter_Email_on_cartTab_checkoutPage(getProperty("cart_email"));
        delayWithGivenTime(1000);
    }

    @Then("the respective value should be displayed in the respective field")
    public void the_respective_value_should_be_displayed_in_the_respective_field() {
        softassert.assertEquals(checkoutPage.get_entered_email_on_cartTab_checkoutPage(), getProperty("cart_email"), "Test Step - 7: Entered email is not matching with expected email");
        delayWithGivenTime(2000);
    }

    @When("the user enters the card message {string} in the Card Message field")
    public void the_user_enters_the_card_message_in_the_card_message_field(String string) {
        //  checkoutPage.Click_browse_our_suggestion_Label();
        delayWithGivenTime(1000);
        checkoutPage.Enter_card_message_on_cartTab_checkoutPage(getProperty("cart_card_message"));
        //  checkoutPage.Click_card_message_from_Suggestion(getProperty("cart_card_message"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_entered_card_message_on_cartTab_checkoutPage(), getProperty("cart_card_message"), "Test Step - 7: Entered card message is not matching with expected card message");
    }

    @Then("the Delivery \\/ Pickup section should be displayed")
    public void the_delivery_pickup_section_should_be_displayed() {
        delayWithGivenTime(1000);
        checkoutPage.Click_cart_tab_Continue_toDelivery_PickUp_button();
        delayWithGivenTime(2000);
        softassert.assertTrue(checkoutPage.verify_recipient_information_subtitle(), "Test Step - 7: Recipient information subtitle is not displayed");
    }

    @When("the user enters the recipient phone number {string} in the Recipient Phone Number field")
    public void the_user_enters_the_recipient_phone_number_in_the_recipient_phone_number_field(String string) {
        delayWithGivenTime(2000);
        checkoutPage.Enter_recipient_first_name(getProperty("recipient_firstName1"));
        delayWithGivenTime(2000);
        softassert.assertEquals(checkoutPage.get_entered_firstname_on_recipient_information_section(), getProperty("recipient_firstName1"), "Test Step - 7: Entered recipient first name is not matching with expected recipient first name");
    }

    @When("the user enters the address {string} and selects an address from the suggestion dropdown")
    public void the_user_enters_the_address_and_selects_an_address_from_the_suggestion_dropdown(String string) {
        delayWithGivenTime(1000);
        checkoutPage.Enter_recipient_last_name(getProperty("recipient_lastName1"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_entered_lastname_on_recipient_information_section(), getProperty("recipient_lastName1"), "Test Step - 7: Entered recipient first name is not matching with expected recipient last name");

        delayWithGivenTime(1000);
        checkoutPage.Enter_recipient_phone_number(getProperty("ecommerce_phone_number"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_entered_phone_number_on_recipient_information_section(), getProperty("ecommerce_phone_number"), "Test Step - 7: Entered recipient phone number is not matching with expected recipient phone number");

        delayWithGivenTime(1000);
        checkoutPage.Search_and_select_with_BusinessName(getProperty("ecommerce_business_name"), getProperty("ecommerce_full_address"));
        delayWithGivenTime(1000);
    }

    @Then("the respective address {string} should be displayed in the respective field")
    public void the_respective_address_should_be_displayed_in_the_respective_field(String string) {
        softassert.assertEquals(checkoutPage.get_selected_country(), getProperty("ecommerce_country"), "Test Step - 7: Selected country is not matching with expected country");
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_selected_state(), getProperty("ecommerce_state"), "Test Step - 7: Selected state is not matching with expected city");
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_displayed_city(), getProperty("ecommerce_city"), "Test Step - 7: Entered city is not matching with expected city");
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_displayed_zip_code(), getProperty("ecommerce_recipient_zipcode"), "Test Step - 7: Entered zip code is not matching with expected zip code");
    }

    @When("the user selects a delivery date in the Delivery Date field")
    public void the_user_selects_a_delivery_date_in_the_delivery_date_field() {
        delayWithGivenTime(2000);
        checkoutPage.Enter_delivery_date(CurrentDate());
        delayWithGivenTime(1000);
        checkoutPage.Click_cart_tab_Continue_toDelivery_PickUp_button();
        delayWithGivenTime(2000);
    }

    @Then("the respective date should be displayed in the respective field")
    public void the_respective_date_should_be_displayed_in_the_respective_field() {

    }

    @Then("the payment section should be displayed")
    public void the_payment_section_should_be_displayed() {
        softassert.assertTrue(checkoutPage.Verify_Payment_Information_Tab_isDisplayed(), "Test Step - 7: Payment information Tab is not displayed");
    }

    @When("the user selects {string} as the payment method")
    public void the_user_selects_as_the_payment_method(String string) {
        delayWithGivenTime(1000);
        checkoutPage.Select_Payment_Method(getProperty("ecommerce_payment_type"));
    }

    @Then("the respective value {string} should be displayed in the Payment Method field")
    public void the_respective_value_should_be_displayed_in_the_payment_method_field(String string) {
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_selected_payment_method(), getProperty("ecommerce_payment_type"), "Test Step - 7: Selected payment method is not matching with expected payment method");
    }

    @When("the user enters the name {string} in the Name On Card field")
    public void the_user_enters_the_name_in_the_name_on_card_field(String string) {
        delayWithGivenTime(1000);
        checkoutPage.Enter_Name_of_Card_on_Payment_Info(getProperty("ecommerce_name_on_card"));

    }

    @Then("the respective value {string} should be displayed in the respective field")
    public void the_respective_value_should_be_displayed_in_the_respective_field(String string) {
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_entered_name_of_card_on_Payment_Info(), getProperty("ecommerce_name_on_card"), "Test Step - 7: Entered name of card is not matching with expected name of card");

    }

    @When("the user enters the card number {string} in the Card Number field")
    public void the_user_enters_the_card_number_in_the_card_number_field(String string) {
        delayWithGivenTime(3000);
        checkoutPage.Enter_Card_Number_on_Payment_Info(getProperty("ecommerce_credit_card_number"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_entered_card_number_on_Payment_Info(), getProperty("ecommerce_credit_card_number"), "Test Step - 7: Entered card number is not matching with expected card number");

    }

    @When("the user enters the expiry date {string} in the Expiry Date field")
    public void the_user_enters_the_expiry_date_in_the_expiry_date_field(String string) {
        delayWithGivenTime(1000);
        checkoutPage.Enter_expiry_date_on_Payment_Info(getProperty("ecommerce_expiry_date"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_entered_expiry_date_on_Payment_Info(), getProperty("ecommerce_expiry_date"), "Test Step - 7: Entered expiry date is not matching with expected expiry date");

    }

    @When("the user enters the CVV {string} in the CVV\\/CVC field")
    public void the_user_enters_the_cvv_in_the_cvv_cvc_field(String string) {
        delayWithGivenTime(1000);
        checkoutPage.Enter_CVV_on_Payment_Info(getProperty("ecommerce_cvv"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.get_entered_CVV_on_Payment_Info(), getProperty("ecommerce_cvv"), "Test Step - 7: Entered CVV is not matching with expected CVV");

    }

    @When("the user enters {string} in the Billing First Name field")
    public void the_user_enters_in_the_billing_first_name_field(String string) {
        delayWithGivenTime(1000);
        checkoutPage.enterBillingFirstName(getProperty("billing_first_name"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.getBillingFirstName(), getProperty("billing_first_name"),
                "Test Step - 1: Entered billing first name is not matching with expected value");
    }

    @When("the user enters {string} in the Billing Last Name field")
    public void the_user_enters_in_the_billing_last_name_field(String string) {
        delayWithGivenTime(1000);
        checkoutPage.enterBillingLastName(getProperty("billing_last_name"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.getBillingLastName(), getProperty("billing_last_name"),
                "Test Step - 2: Entered billing last name is not matching with expected value");

    }

    @When("the user enters {string} in the Billing Phone Number field")
    public void the_user_enters_in_the_billing_phone_number_field(String string) {
        delayWithGivenTime(1000);
        checkoutPage.enterBillingPhoneNumber(getProperty("ecommerce_phone_number"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.getBillingPhoneNumber(), getProperty("ecommerce_phone_number"),
                "Test Step - 3: Entered billing phone number is not matching with expected value");

    }

    @When("the user enters {string} in the Billing Street Address field")
    public void the_user_enters_in_the_billing_street_address_field(String string) {
        delayWithGivenTime(1000);
        checkoutPage.enterBillingStreetAddress(getProperty("billing_street_address"));
        delayWithGivenTime(1000);
        softassert.assertEquals(checkoutPage.getBillingStreetAddress(), getProperty("billing_street_address"),
                "Test Step - 4: Entered billing street address is not matching with expected value");

    }

    @When("the user enters {string} in the Billing Country field")
    public void the_user_enters_in_the_billing_country_field(String string) {
        delayWithGivenTime(2000);
        checkoutPage.selectBillingCountry(getProperty("billing_country"));
        delayWithGivenTime(2000);
        softassert.assertEquals(checkoutPage.getBillingCountry(), getProperty("billing_country"),
                "Test Step - 5: Selected billing country is not matching with expected value");

    }

    @When("the user enters {string} in the Billing State field")
    public void the_user_enters_in_the_billing_state_field(String string) {
        delayWithGivenTime(2000);
        checkoutPage.selectBillingState(getProperty("billing_state"));
        delayWithGivenTime(2000);
        softassert.assertEquals(checkoutPage.getBillingState(), getProperty("billing_state"),
                "Test Step - 6: Selected billing state is not matching with expected value");

    }

    @When("the user enters {string} in the Billing City field")
    public void the_user_enters_in_the_billing_city_field(String string) {
        delayWithGivenTime(2000);
        checkoutPage.enterBillingCity(getProperty("billing_city"));
        delayWithGivenTime(2000);
        softassert.assertEquals(checkoutPage.getBillingCity(), getProperty("billing_city"),
                "Test Step - 7: Entered billing city is not matching with expected value");

    }

    @When("the user enters {string} in the Billing Zip Code field")
    public void the_user_enters_in_the_billing_zip_code_field(String string) {
        delayWithGivenTime(2000);
        checkoutPage.enterBillingZipCode(getProperty("billing_zip_code"));
        delayWithGivenTime(2000);
        softassert.assertEquals(checkoutPage.getBillingZipCode(), getProperty("billing_zip_code"),
                "Test Step - 8: Entered billing zip code is not matching with expected value");

    }

    @Then("the review section should be displayed")
    public void the_review_section_should_be_displayed() {
        delayWithGivenTime(1000);
        checkoutPage.Click_continue_to_review_button();
        delayWithGivenTime(1000);
        softassert.assertTrue(checkoutPage.Verify_Review_Information_Tab_isDisplayed(), "Test Step - 9: Review information Tab is not displayed");
    }

    @When("the user verifies if all the entered information is displaying properly")
    public void the_user_verifies_if_all_the_entered_information_is_displaying_properly() {
        delayWithGivenTime(1000);
        checkoutPage.Click_Agree_Substitution_Checkbox();
    }

    @Then("the respective data should be displayed properly")
    public void the_respective_data_should_be_displayed_properly() {
        delayWithGivenTime(1000);
        checkoutPage.Click_Place_Your_Order_Button();

    }

    @When("the user checks the {string} checkbox")
    public void the_user_checks_the_checkbox(String string) {

    }

    @Then("the respective checkbox should be checked")
    public void the_respective_checkbox_should_be_checked() {

    }

    @Then("a success toaster message should be displayed")
    public void a_success_toaster_message_should_be_displayed() {
        delayWithGivenTime(3000);
        softassert.assertTrue(checkoutPage.Verify_Order_Confirmation_Message(), "Test Step - 10: Order confirmation message is not displayed");
    }

    @Then("the user should be redirected to the Order Confirmation Page")
    public void the_user_should_be_redirected_to_the_order_confirmation_page() {

    }

    @Then("the order number should be displayed")
    public void the_order_number_should_be_displayed() {

    }

    @Then("all the details entered in the checkout page should be displayed")
    public void all_the_details_entered_in_the_checkout_page_should_be_displayed() {
        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getProductRow1Text(), "Balaji_Test_Product-Medium", "Test Step - 1: Product Row 1 text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getProductRow2Text(), "Chocolates - Medium Box add-on for Test_Automation", "Test Step - 2: Product Row 2 text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getQuantityRow1Text(), "1", "Test Step - 3: Quantity Row 1 text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getQuantityRow2Text(), "1", "Test Step - 4: Quantity Row 2 text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getUnitPriceRow1Text(), "$500.00", "Test Step - 5: Unit Price Row 1 text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getUnitPriceRow2Text(), "$19.99", "Test Step - 6: Unit Price Row 2 text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getTotalPriceRow1Text(), "$500.00", "Test Step - 7: Total Price Row 1 text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getTotalPriceRow2Text(), "$19.99", "Test Step - 8: Total Price Row 2 text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getProductCostText(), "$519.99", "Test Step - 9: Product Cost text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getDeliveryFeeText(), "$35.00", "Test Step - 10: Delivery Fee text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getTaxText(), "$60.00", "Test Step - 11: Tax text is not empty");


    }

    @Then("the respective product with all the details should be displayed")
    public void the_respective_product_with_all_the_details_should_be_displayed() {

    }

    @Then("the order total should be displayed properly")
    public void the_order_total_should_be_displayed_properly() {
        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getTotalProductCostText(), "$" + order_confirmationPage.Calculate_Total_Cost(), "Test Step - 12: Total Product Cost text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getCustomerInformationText(), "John Doe\n" +
                "123 Main St\n" +
                "Louisville, KY 77022 US\n" +
                "hanaposqateam@gmail.com\n" +
                "Phone: 1-561-555-7689", "Test Step - 13: Customer Information text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getDeliveryInformationText(), "Abish David\n" +
                "Churchill Downs\n" +
                "700 Central Ave\n" +
                "Louisville, KY 40208 US\n" +
                "Phone: 1-561-555-7689\n" +
                "Delivery Date: " + CurrentDate(), "Test Step - 14: Delivery Information text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getPaymentTypeText(), "CREDIT CARD", "Test Step - 15: Payment Type text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getPaymentCardNumberText(), "1111", "Test Step - 16: Payment Card Number text is not empty");

        delayWithGivenTime(1000);
        softassert.assertEquals(order_confirmationPage.getCommentsOrSpecialInstructionsText(), getProperty("cart_card_message"), "Test Step - 17: Comments or Special Instructions text is not empty");

    }


}
