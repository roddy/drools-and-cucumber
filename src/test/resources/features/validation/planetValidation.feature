@validation
Feature: Planet Validation

  Scenario: A planet without a name is invalid
    When I have a small planet
    And my planet is located at (0,0,0)
    And my planet belongs to the Humans
    Then my planet is not valid
    And the error message is: "Errors: planet needs a name; "

  Scenario: A planet without a species is valid
    When I have a medium planet named "Earth"
    And my planet is located at (0,0,0)
    Then my planet is valid
    And there is no error message

  Scenario: A planet with a species and a name is valid
    When I have a medium planet named "Mars"
    And my planet is located at (0,0,0)
    And my planet belongs to the Aliens
    Then my planet is valid
    And there is no error message

  Scenario Outline: Uninhabited planets cannot have commerce
    When I have a <size> planet named "Seti Alpha 6"
    And my planet's commerce is primarily <type>
    And my planet is located at (0,0,0)
    Then my planet is not valid
    And the error message contains: "uninhabited planet cannot have commerce"
    Scenarios:
      | size     | type         |
      | gigantic | agricultural |
      | gigantic | industrial   |
      | gigantic | commercial   |
      | gigantic | military     |
      | large    | agricultural |
      | large    | industrial   |
      | large    | commercial   |
      | large    | military     |
      | medium   | agricultural |
      | medium   | industrial   |
      | medium   | commercial   |
      | medium   | military     |
      | small    | agricultural |
      | small    | industrial   |
      | small    | commercial   |
      | small    | military     |
      | tiny     | agricultural |
      | tiny     | industrial   |
      | tiny     | commercial   |
      | tiny     | military     |

  Scenario Outline: Valid planets have no errors
    When I have a <size> planet named "Mars"
    And my planet belongs to the <species>s
    And my planet is located at (0,0,0)
    Then my planet is valid
    And there is no error message
    Scenarios:
      | size     | species |
      | tiny     | Human   |
      | small    | Human   |
      | medium   | Human   |
      | large    | Human   |
      | gigantic | Human   |
      | tiny     | Alien   |
      | small    | Alien   |
      | medium   | Alien   |
      | large    | Alien   |
      | gigantic | Alien   |

  Scenario: Planets without coordinates are invalid
    When I have a small planet named "Earth"
    And my planet belongs to the Humans
    Then my planet is not valid
    And the error message is: "Errors: planets must have coordinates; "

