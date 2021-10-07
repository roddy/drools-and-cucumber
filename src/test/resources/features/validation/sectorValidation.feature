@validation
Feature: Sector Validation

  Scenario: A sector without a designation is invalid
    When I have a sector
    Then my sector is not valid
    And the error message contains: "sector must have a designation"

  Scenario: A sector without planets is invalid
    When I have a sector with designation "ABC"
    Then my sector is not valid
    And the error message contains: "sector must have planets"

  Scenario: A valid sector will not have error messages
    When I have a sector with designation "ABC"
    And my sector contains the following planets:
      | name     | species | size | commerce   | location |
      | Planet X | Alien   | Tiny | Industrial | 0,0,0    |
    Then my sector is valid
    And there is no error message

  Scenario: A sector cannot have two planets with the same coordinates
    When I have a sector with designation "ABC"
    And my sector contains the following planets:
      | name     | species | size | commerce     | location |
      | Planet X | Alien   | Tiny | Industrial   | 0,0,0    |
      | Planet Y | Human   | Tiny | Agricultural | 0,0,0    |
    Then my sector is not valid
    And the error message contains: "sector planets must be distinct locations"

  Scenario Outline: A sector can have multiple planets
    When I have a sector with designation "ABC"
    And my sector contains the following planets:
      | name     | species | size | commerce     | location |
      | Planet X | Alien   | Tiny | Industrial   | 0,0,0    |
      | Planet Y | Human   | Tiny | Agricultural | <coords> |
    Then my sector is valid
    And there is no error message
    Scenarios:
      | coords |
      | 1,0,0  |
      | 0,1,0  |
      | 0,0,1  |
      | 1, 1,0 |
      | 1,0,1  |
      | 0,1,1  |
      | 1,1,1  |

  Scenario: A sector with multiple planets can't have any occupying the same coordinates
    When I have a sector with designation "ABC"
    And my sector contains the following planets:
      | name     | species | size | commerce   | location |
      | Planet X | Alien   | Tiny | Industrial | 0,0,0    |
      | Planet Y | Alien   | Tiny | Industrial | 1,0,0    |
      | Planet Z | Alien   | Tiny | Industrial | 0,1,0    |
      | Planet A | Alien   | Tiny | Industrial | 0,0,1    |
      | Planet B | Alien   | Tiny | Industrial | 1,1,0    |
      | Planet C | Alien   | Tiny | Industrial | 1,0,1    |
      | Planet D | Alien   | Tiny | Industrial | 0,1,1    |
      | Planet E | Alien   | Tiny | Industrial | 1,1,1    |
      | Planet F | Alien   | Tiny | Industrial | 0,0,0    |
    Then my sector is not valid
    And the error message contains: "sector planets must be distinct locations"
