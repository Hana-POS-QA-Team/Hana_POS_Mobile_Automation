Feature: Validate e-commerce mobile application functionality
  This feature verifies the homepage and search functionality of the e-commerce web application on a mobile browser.

  Scenario Outline: Search for a product on the e-commerce website
    # Step 1: Launching the URL in the browser
    Given Launching the browser from the mobile app and searching for Webapp
    When User launches the URL in the Chrome browser in mobile as "<hanaEcomURL>"
    Then User should land on the e-commerce homepage, and the title should be displayed as "<hanawebsiteTitle>"

    # Step 2: User searches for a product on the website
    When User enters "<productName>" in the search box
    Then The user clicks on the search icon
    And The user should navigate to the "<SearchResultPage>"
    And The user should be able to see the respective searched "<productName>"

    # Step 3: Add the product to the cart
    When the user clicks the respective searched product it should navigated to product details page
    When the user enters or selects all mandatory fields and clicks on the order button
    Then the addons page should be displayed

    # Step 4: Select an addon and proceed to checkout
    When the user selects an addon and clicks on the "Continue to Checkout" button
    Then the order preview page should be displayed

    # Step 6: Verify sub-total on the order preview page
    Then the sub total of the product "<ecommerce_product>" with variant price "<product_variant_medium_price>" should be displayed properly

    # Step 7: Proceed to checkout
    When the user clicks on the "Proceed to Checkout" button
    Then the checkout page should be displayed

    # Step 8: Fill out Cart information
    When the user selects the occasion "<cart_occasion>" under the Cart tab
    Then the respective occasion value should be displayed in the respective field

    When the user enters the email "<cart_email>" in the Email field
    Then the respective value should be displayed in the respective field

    When the user enters the card message "<cart_card_message>" in the Card Message field
    Then the respective value should be displayed in the respective field

    # Step 9: Continue to Delivery / Pickup section
    When the user clicks on the "Continue to Delivery / Pickup" button
    Then the Delivery / Pickup section should be displayed

    # Step 10: Enter recipient information
    When the user enters the recipient phone number "<ecommerce_phone_number>" in the Recipient Phone Number field
    Then the respective value should be displayed in the respective field

    # Step 11: Address Lookup and Delivery Date
    When the user enters the address "<ecommerce_full_address>" and selects an address from the suggestion dropdown
    Then the respective address "<ecommerce_full_address>" should be displayed in the respective field

    When the user selects a delivery date in the Delivery Date field
    Then the respective date should be displayed in the respective field

    # Step 12: Proceed to payment
    When the user clicks on the "Continue to Payment" button
    Then the payment section should be displayed

    # Step 13: Select payment method and enter payment details
    When the user selects "<ecommerce_payment_type>" as the payment method
    Then the respective value "<ecommerce_payment_type>" should be displayed in the Payment Method field

    When the user enters the name "<ecommerce_name_on_card>" in the Name On Card field
    Then the respective value "<ecommerce_name_on_card>" should be displayed in the respective field

    When the user enters the card number "<ecommerce_credit_card_number>" in the Card Number field
    Then the respective value "<ecommerce_credit_card_number>" should be displayed in the respective field

    When the user enters the expiry date "<ecommerce_expiry_date>" in the Expiry Date field
    Then the respective value "<ecommerce_expiry_date>" should be displayed in the respective field

    When the user enters the CVV "<ecommerce_cvv>" in the CVV/CVC field
    Then the respective value "<ecommerce_cvv>" should be displayed in the respective field

    # Step 14: Billing Information
    When the user enters "<billing_first_name>" in the Billing First Name field
    Then the respective value "<billing_first_name>" should be displayed in the respective field

    When the user enters "<billing_last_name>" in the Billing Last Name field
    Then the respective value "<billing_last_name>" should be displayed in the respective field

    When the user enters "<billing_phone_number>" in the Billing Phone Number field
    Then the respective value "<billing_phone_number>" should be displayed in the respective field

    When the user enters "<billing_street_address>" in the Billing Street Address field
    Then the respective value "<billing_street_address>" should be displayed in the respective field

    When the user enters "<billing_country>" in the Billing Country field
    Then the respective value "<billing_country>" should be displayed in the respective field

    When the user enters "<billing_state>" in the Billing State field
    Then the respective value "<billing_state>" should be displayed in the respective field

    When the user enters "<billing_city>" in the Billing City field
    Then the respective value "<billing_city>" should be displayed in the respective field

    When the user enters "<billing_zip_code>" in the Billing Zip Code field
    Then the respective value "<billing_zip_code>" should be displayed in the respective field

    # Step 15: Continue to Review and verify entered information
    When the user clicks on the "Continue to Review" button
    Then the review section should be displayed

    When the user verifies if all the entered information is displaying properly
    Then the respective data should be displayed properly

    # Step 16: Agree with substitution policy
    When the user checks the "I agree with your substitution policy" checkbox
    Then the respective checkbox should be checked

    # Step 17: Place the order and verify the order confirmation page
    When the user clicks on the "Place Order" button
    Then a success toaster message should be displayed
    And the user should be redirected to the Order Confirmation Page

    # Step 18: Verify order details on the confirmation page
    Then the order number should be displayed
    And all the details entered in the checkout page should be displayed
    And the respective product with all the details should be displayed
    And the order total should be displayed properly

    Examples:
      | hanaEcomURL | hanawebsiteTitle | productName   | SearchResultPage | product_variant_medium_price | zipcode | cart_occasion | cart_email              | cart_card_message                                 | ecommerce_phone_number | ecommerce_business_name | ecommerce_full_address              | ecommerce_country | ecommerce_state | ecommerce_city | ecommerce_recipient_zipcode | ecommerce_payment_type | ecommerce_name_on_card | ecommerce_credit_card_number | ecommerce_expiry_date | ecommerce_cvv | billing_first_name | billing_last_name | billing_phone_number | billing_street_address | billing_country | billing_state | billing_city | billing_zip_code |  |
      | <url>       | <pageTitle>      | <productName> | <searchPage>     | CA$500.00                    | 77021   | Birthday      | hanaposqateam@gmail.com | Happy Birthday to the one and only wonderful you! | 561-555-7689           | Churchill Downs         | Central Avenue, Louisville, KY, USA | United States     | Kentucky        | Louisville     | 40208                       | Credit Card            | John Doe               | 4111111111111111             | 1030                  | 123           | John               | Doe               | 2015550123           | 123 Main St            | United States   | Kentucky      | Louisville   | 77022            |  |

