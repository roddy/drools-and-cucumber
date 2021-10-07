@validation
Feature: Cargo Validation
  
  Scenario: Unnamed cargo is invalid
    When I have Luxury cargo
    And my cargo has length 10, width 15, and depth 2
    And my cargo weighs 7 pounds
    Then my cargo is not valid
    And there are error messages: "cargo must have a name"

  Scenario: Cargo with no type is invalid
    When I have cargo called "Cargo"
    And my cargo has length 10, width 15, and depth 2
    And my cargo weighs 7 pounds
    Then my cargo is not valid
    And there are error messages: "cargo must have a type"

  Scenario: Cargo with no dimensions is invalid
    When I have Luxury cargo called "Cargo"
    Then my cargo is not valid
    And there are error messages: "cargo must have dimensions"

  Scenario Outline: Cargo without complete dimensions is invalid
    When I have Luxury cargo called "Cargo"
    And my cargo has length <length>, width <width>, and depth <depth>
    And my cargo weighs <weight> pounds
    Then my cargo is not valid
    And there are error messages: "cargo dimensions are invalid"
    Scenarios:
      | length | width | depth | weight |
      | 0      | 1     | 1     | 1      |
      | 1      | 0     | 1     | 1      |
      | 1      | 1     | 0     | 1      |
      | 1      | 1     | 1     | 0      |
      | 0      | 0     | 1     | 1      |
      | 0      | 1     | 0     | 1      |
      | 0      | 1     | 1     | 0      |
      | 1      | 0     | 0     | 1      |
      | 1      | 0     | 1     | 0      |
      | 1      | 1     | 0     | 0      |
      | 0      | 0     | 0     | 1      |
      | 0      | 0     | 1     | 0      |
      | 0      | 1     | 0     | 0      |
      | 1      | 0     | 0     | 0      |
      | 0      | 0     | 0     | 0      |
      | -1     | 1     | 1     | 1      |
      | 1      | -1    | 1     | 1      |
      | 1      | 1     | -1    | 1      |
      | 1      | 1     | 1     | -1     |
      | -1     | -1    | 1     | 1      |
      | -1     | 1     | -1    | 1      |
      | -1     | 1     | 1     | -1     |
      | 1      | -1    | -1    | 1      |
      | 1      | -1    | 1     | -1     |
      | 1      | 1     | -1    | -1     |
      | -1     | -1    | -1    | 1      |
      | -1     | -1    | 1     | -1     |
      | -1     | 1     | -1    | -1     |
      | 1      | -1    | -1    | -1     |
      | -1     | -1    | -1    | -1     |

  Scenario Outline: Cargo of all types is valid
    When I have <type> cargo called "Cargo"
    And my cargo has length 10, width 15, and depth 2
    And my cargo weighs 7 pounds
    Then my cargo is valid
    And there is no error message
    Scenarios:
      | type           |
      | luxury         |
      | narcotic       |
      | military       |
      | raw material   |
      | finished goods |
      | machinery      |
      | perishable     |
      | medical        |
      | commercial     |
      | industrial     |

  Scenario Outline: Both legal and illegal cargo is valid
    When I have narcotic cargo called "Cargo"
    And my cargo is <legality>
    And my cargo has length 10, width 15, and depth 2
    And my cargo weighs 7 pounds
    Then my cargo is valid
    And there is no error message
    Scenarios:
      | legality |
      | legal    |
      | illegal  |

  Scenario: Cargo with a description is valid
    When I have industrial cargo called "Cargo"
    And my cargo can be described as "important things for factories"
    And my cargo has length 10, width 15, and depth 2
    And my cargo weighs 7 pounds
    Then my cargo is valid
    And there is no error message
