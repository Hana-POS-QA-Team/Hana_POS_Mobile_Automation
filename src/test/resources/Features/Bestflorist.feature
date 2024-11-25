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

    Examples:
      | hanaEcomURL  | hanawebsiteTitle | productName  | SearchResultPage |
      | <url>        | <pageTitle>      | <productName>| <searchPage>     |
