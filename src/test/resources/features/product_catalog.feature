Feature: Product catalogue
Background:
  Given the catalogue does not contain our products

  Scenario: Adding a product to an existing catalogue
    When a product named "Apple MacBook Pro 16" is added to the catalogue
    Then the catalogue listing for that product should contain the following:
      | name                 |
      | Apple MacBook Pro 16 |

  Scenario: Listing multiple products in the catalogue
    Given the product "Samsung Galaxy S21" was added to the catalogue
    And the product "Google Pixel 6" was added to the catalogue
    When a catalogue request to list both products is made
    Then the catalogue listing should contain the following:
      | name                 |
      | Samsung Galaxy S21   |
      | Google Pixel 6       |

  Scenario: Removing a product from the catalogue
    Given the product "Dell XPS 13" was added to the catalogue
    When the product "Dell XPS 13" is removed from the catalogue
    Then the catalogue listing should not contain the product "Dell XPS 13"

  Scenario: Attempting to access a product that is not in the catalogue
    When the catalogue is asked for a product identified by "does-not-exist"
    Then the catalogue indicates the product is not found

  Scenario: Adding a malformed product to the catalogue
    When a product is added with malformed data
    Then the catalogue should reject it
