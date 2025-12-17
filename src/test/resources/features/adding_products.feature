Feature: Product catalogue
Background:
  Given the catalogue does not contain our products

  Scenario: Adding a product to an existing catalogue
    When a product named "Apple MacBook Pro 16" is added to the catalogue
    Then the catalogue listing for that product should contain the following:
      | name                 |
      | Apple MacBook Pro 16 |

  Scenario: Attempting to access a product that is not in the catalogue
    When the catalogue is asked for a product identified by "does-not-exist"
    Then the catalogue indicates the product is not found

  Scenario: Adding a malformed product to the catalogue
    When a product is added with malformed data
    Then the catalogue should reject it
