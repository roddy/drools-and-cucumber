@validation
Feature: Person Validation

  Scenario: A person without a name is invalid
    When my person is a female Alien
    Then my person is not valid
    And the error message is: "Errors: person is missing a name; "

  Scenario: A person without a gender is invalid
    When my person is named "Spock"
    And my person is an Alien
    Then my person is not valid
    And the error message is: "Errors: person is missing gender; "

  Scenario: A person without a species is invalid
    When my person is named "Bob"
    And my person is male
    Then my person is not valid
    And the error message is "Errors: person is missing species; "

  Scenario Outline: A person missing multiple data has multiple messages
    When my person is named "<name>"
    And my person is <gender>
    And my person is a <species>
    Then my person is not valid
    And there are error messages: "<messages>"
    Scenarios:
      | name | gender | species | messages                                                                         |
      | Bill |        |         | person is missing species; person is missing gender                          |
      |      | male   |         | person is missing a name; person is missing species                            |
      |      |        | Human   | person is missing a name; person is missing gender                             |
      |      |        |         | person is missing a name; person is missing species; person is missing gender |

  Scenario: Valid persons have no error messages
    When my person is a male Human named "John Q. Public"
    Then my person is valid
    And there is no error message