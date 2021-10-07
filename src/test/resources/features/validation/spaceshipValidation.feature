@validation
Feature: Spaceship Validation

  Scenario Outline: Spaceships without a name are invalid
    When I have a <species> spaceship
    And my spaceship has no name
    Then my spaceship is not valid
    And the error message contains: "spaceship is missing a name"
    Scenarios:
      | species |
      | Human   |
      | Alien   |

  Scenario: Spaceships without a parent species are invalid
    When I have a spaceship named "U.S.S Enterprise"
    Then my spaceship is not valid
    And the error message contains: "spaceship has no owning species"

  Scenario: An Alien spaceship cannot have a name starting with U.S.S
    When I have an Alien spaceship named "U.S.S. Enterprise"
    Then my spaceship is not valid
    And the error message contains: "only human ships may use prefix U.S.S."

  Scenario: An Alien spaceship cannot have a name starting with S.S
    When I have an Alien spaceship named "S.S. Enterprise"
    Then my spaceship is not valid
    And the error message contains: "only human ships may use prefix S.S."

  Scenario: A spaceship must have a captain
    When I have an Alien spaceship named "Spaceship X"
    And the chief engineer is an Alien male named "Billy Bob"
    Then my spaceship is not valid
    And the error message contains: "missing captain"

  Scenario: A spaceship must have a classification
    When I have an Alien spaceship named "Spaceship X"
    And the captain is an Alien male named "Captain Jack"
    Then my spaceship is not valid
    And the error message contains: "missing classification"

  Scenario Outline: A spaceship must have a captain of the appropriate species
    When I have a <species> spaceship named "My Spaceship"
    And the captain is a <other species> male named "Captain Jack"
    Then my spaceship is not valid
    And the error message contains: "captain of the wrong species"
    Scenarios:
      | species | other species |
      | Human   | Alien         |
      | Alien   | Human         |

  Scenario: A spaceship must have a type
    When I have a Human spaceship named "Green Eggs and Ham"
    And the captain is a Human male named "Billy Bob"
    And my spaceship is a Civilian ship
    Then my spaceship is not valid
    And the error message contains: "missing type"

  Scenario: A military ship cannot be a freighter
    When I have a Human spaceship named "New York"
    And the captain is a Human female named "Annie"
    And my spaceship is a Military ship of type Freighter
    Then my spaceship is not valid
    And the error message contains: "military ships cannot be freighters"

  Scenario Outline: Non-military ships can be freighters
    When I have a Human spaceship named "New York"
    And the captain is a Human female named "Annie"
    And my spaceship is a <classification> ship of type Freighter
    And my spaceship is located at (0,0,0)
    Then my spaceship is valid
    And there is no error message
    Scenarios:
      | classification |
      | Civilian       |
      | Pirate         |

  Scenario: A civilian ship cannot be a dreadnaught
    When I have an Alien spaceship named "Zxi"
    And the captain is an Alien male named "Vazik"
    And my spaceship is a Civilian ship of type Dreadnaught
    Then my spaceship is not valid
    And the error message contains: "civilian ships cannot be dreadnaughts"

  Scenario Outline: Non-civilian ships can be dreadnaughts
    When I have an Alien spaceship named "Zxi"
    And the captain is an Alien male named "Vazik"
    And my spaceship is a <classification> ship of type Dreadnaught
    And my spaceship is located at (0,0,0)
    Then my spaceship is valid
    And there is no error message
    Scenarios:
      | classification |
      | Military       |
      | Pirate         |

  Scenario: A pirate ship cannot be a transport
    When I have a Human spaceship named "Lightspeed"
    And the captain is a Human male named "Peter Pan"
    And my spaceship is a Pirate ship of type Transport
    Then my spaceship is not valid
    And the error message contains: "pirate ships cannot be transports"

  Scenario Outline: Non-pirate ships can be transports
    When I have a Human spaceship named "Lightspeed"
    And the captain is a Human male named "Peter Pan"
    And my spaceship is a <classification> ship of type Transport
    And my spaceship is located at (0,0,0)
    Then my spaceship is valid
    And there is no error message
    Scenarios:
      | classification |
      | Military       |
      | Civilian       |

  Scenario Outline: A Corvette type spaceship may be any classification
    When I have a Human spaceship named "Sunny-side Up"
    And the captain is a Human male named "Goober"
    And my spaceship is a <classification> ship of type Corvette
    And my spaceship is located at (0,0,0)
    Then my spaceship is valid
    And there is no error message
    Scenarios:
      | classification |
      | Military       |
      | Pirate         |
      | Civilian       |

  Scenario Outline: Invalid spaceships with multiple problems have multiple error messages
    When I have a <species> spaceship named "<name>"
    And my spaceship is an <classification> ship of type <type>
    And the captain is a <captain species> <gender> named "<captain name>"
    Then my spaceship is not valid
    And the error message contains: "<messages>"
    Scenarios:
      | species | name              | classification | type     | captain name | gender | captain species | messages                                                                                                            |
      | Human   | X                 | Civilian       |          |              |        |                 | missing captain; missing type                                                                                       |
      | Human   | X                 |                | Corvette |              |        |                 | missing captain; missing classification                                                                             |
      | Human   |                   | Civilian       | Corvette |              |        |                 | missing captain; spaceship is missing a name                                                                        |
      |         | X                 | Civilian       | Corvette |              |        |                 | missing captain; spaceship has no owning species                                                                    |
      | Human   | X                 |                |          | Bubba        | Male   | Human           | missing type; missing classification                                                                                |
      | Human   |                   | Civilian       |          | Bubba        | Male   | Human           | missing type; spaceship is missing a name                                                                           |
      |         | X                 | Civilian       |          | Bubba        | Male   | Human           | missing type; spaceship has no owning species                                                                       |
      | Human   |                   |                | Corvette | Bubba        | Male   | Human           | missing classification; spaceship is missing a name                                                                 |
      |         | X                 |                | Corvette | Bubba        | Male   | Human           | missing classification; spaceship has no owning species                                                             |
      |         |                   | Civilian       | Corvette | Bubba        | Male   | Human           | spaceship is missing a name; spaceship has no owning species                                                        |
      | Human   | X                 |                |          |              |        |                 | missing captain; missing type; missing classification;                                                              |
      | Human   |                   | Civilian       |          |              |        |                 | missing captain; missing type; spaceship is missing a name                                                          |
      | Human   |                   |                | Corvette |              |        |                 | missing captain; missing classification; spaceship is missing a name                                                |
      | Human   |                   |                |          | Bubba        | Male   | Human           | missing type; missing classification; spaceship is missing a name                                                   |
      |         | X                 | Civilian       |          |              |        |                 | missing captain; missing type; spaceship has no owning species                                                      |
      |         | X                 |                | Corvette |              |        |                 | missing captain; missing classification; spaceship has no owning species                                            |
      |         | X                 |                |          | Bubba        | Male   | Human           | missing type; missing classification; spaceship has no owning species                                               |
      |         |                   | Civilian       | Corvette |              |        |                 | missing captain; spaceship is missing a name; spaceship has no owning species                                       |
      |         |                   | Civilian       |          | Bubba        | Male   | Human           | missing type; spaceship is missing a name; spaceship has no owning species                                          |
      |         |                   |                | Corvette | Bubba        | Male   | Human           | missing classification; spaceship is missing a name; spaceship has no owning species                                |
      | Human   |                   |                |          |              |        |                 | missing captain; missing type; missing classification; spaceship is missing a name                                  |
      |         | X                 |                |          |              |        |                 | missing captain; missing type; missing classification; spaceship has no owning species                              |
      |         |                   | Civilian       |          |              |        |                 | missing captain; missing type; spaceship is missing a name; spaceship has no owning species                         |
      |         |                   |                | Corvette |              |        |                 | missing captain; missing classification; spaceship is missing a name; spaceship has no owning species               |
      |         |                   |                |          | Bubba        | Male   | Human           | missing type; missing classification; spaceship is missing a name; spaceship has no owning species                  |
      |         |                   |                |          |              |        |                 | missing captain; missing type; missing classification; spaceship is missing a name; spaceship has no owning species |

  Scenario Outline: Valid spaceships have no error messages
    When I have a <species> spaceship named "<name>"
    And my spaceship is a <classification> ship of type <type>
    And the captain is a <species> <gender> named "<captain>"
    And my spaceship is located at (0,0,0)
    Then my spaceship is valid
    And there is no error message
    Scenarios:
      | species | name                | gender | captain | classification | type        |
      | Human   | U.S.S. Enterprise   | male   | Kirk    | Military       | Dreadnaught |
      | Alien   | X.S. Kobayashi Maru | female | Molly   | Military       | Dreadnaught |
      | Human   | U.S.S. Enterprise   | male   | Kirk    | Civilian       | Freighter   |
      | Alien   | X.S. Kobayashi Maru | female | Molly   | Civilian       | Freighter   |
      | Human   | U.S.S. Enterprise   | male   | Kirk    | Pirate         | Corvette    |
      | Alien   | X.S. Kobayashi Maru | female | Molly   | Pirate         | Corvette    |

  Scenario Outline: Ships with a crew complement of less than 1 are invalid
    When I have a Human spaceship named "Starlight"
    And my spaceship is a Civilian ship of type Freighter
    And the captain is a Human male named "Stryker"
    And my crew complement is <complement>
    Then my spaceship is not valid
    And the error message contains: "minimum crew complement is 1"
    Scenarios:
      | complement |
      | 0          |
      | -1         |

  Scenario: A spaceship must have a crew
    When I have a Human spaceship named "Starlight"
    And my spaceship is a Civilian ship of type Freighter
    Then my spaceship is not valid
    And the error message contains: "missing crew"

  Scenario Outline: Crew size must not exceed the complement
    When I have a Human spaceship named "Starlight"
    And my spaceship is a Civilian ship of type Freighter
    And the captain is a Human male named "Stryker"
    And the pilot is a Human male named "Otto"
    And the doctor is a Human male named "Mayo"
    And my crew complement is <complement>
    Then my spaceship is not valid
    And the error message contains: "crew size exceeds limits"
    Scenarios:
      | complement |
      | 1          |
      | 2          |

  Scenario Outline: Crews of size up to the complement are valid
    When I have a Human spaceship named "Starlight"
    And my spaceship is a Civilian ship of type Freighter
    And the captain is a Human male named "Stryker"
    And the pilot is a Human male named "Otto"
    And the doctor is a Human male named "Mayo"
    And my crew complement is <complement>
    And my spaceship is located at (0,0,0)
    Then my spaceship is valid
    And there is no error message
    Scenarios:
      | complement |
      | 5          |
      | 4          |
      | 3          |

  Scenario: A spaceship must have a position
    When I have a Human spaceship named "Starlight"
    And my spaceship is a Civilian ship of type Freighter
    And the captain is a Human male named "Stryker"
    And my crew complement is 1
    Then my spaceship is not valid
    And the error message contains: "spaceship missing recorded position"
