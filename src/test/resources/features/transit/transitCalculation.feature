@transit
Feature: Transit Calculation

  Scenario: If no planet is specified, transit is out of range
    When there is a spaceship:
      | location | classification | type      |
      | 0,0,0    | civilian       | freighter |
    Then the trip is out of range

  Scenario: If no spaceship is specified, transit is out of range
    When there is a planet at (0,0,0)
    Then the trip is out of range

  Scenario: If there is a spaceship with no location, transit is out of range
    When there is a spaceship:
      | classification | type      |
      | civilian       | freighter |
    And there is a planet at (0,0,0)
    Then the trip is out of range

  Scenario: If there is a spaceship with no classification, transit is out of range
    When there is a spaceship:
      | location | type      |
      | 0,0,0    | freighter |
    And there is a planet at (1,1,1)
    Then the trip is out of range

  Scenario: If there is a spaceship with no type, transit is out of range
    When there is a spaceship:
      | location | classification |
      | 0,0,0    | civilian       |
    And there is a planet at (1,1,1)
    Then the trip is out of range

  Scenario: If there is a planet without a location, transit is out of range
    When there is a spaceship:
      | location | classification | type      |
      | 0,0,0    | civilian       | freighter |
    And there is a planet
    Then the trip is out of range

  Scenario Outline: Base Range calculation
    When there is a spaceship at (0,0,0)
    And the spaceship is a <classification> <type>
    And there is a planet at (1,1,1)
    Then the trip is in range
    And the base range should be <expected>
    And the max range should be <expected>
    Scenarios:
      | classification | type        | expected |
      | civilian       | freighter   | 10       |
      | pirate         | freighter   | 10       |
      | civilian       | corvette    | 5        |
      | pirate         | corvette    | 6        |
      | military       | corvette    | 7        |
      | civilian       | transport   | 15       |
      | military       | transport   | 17       |
      | pirate         | dreadnaught | 25       |
      | military       | dreadnaught | 20       |

  Scenario Outline: Pilot modifier calculation for learned skills
    When there is a spaceship at (0,0,0)
    And the pilot has been trained to the <learned> level
    And there is a planet at (1,1,1)
    Then the pilot modifier should be <expected>
    Scenarios:
      | learned      | expected |
      | none         | 0        |
      | elementary   | 1        |
      | trade school | 2        |
      | university   | 3        |
      | master       | 4        |
      | doctorate    | 5        |

  Scenario Outline: Pilot modifier calculation for latent skills
    When there is a spaceship at (0,0,0)
    And the pilot has <latent> latent skills
    And there is a planet at (1,1,1)
    Then the pilot modifier should be <expected>
    Scenarios:
      | latent        | expected |
      | abysmal       | -2       |
      | below average | -1       |
      | average       | 0        |
      | superior      | 1        |
      | prodigy       | 2        |

  Scenario Outline: Pilot modifier calculation for learned and latent skills
    When there is a spaceship at (0,0,0)
    And the pilot has <latent> latent skills
    And the pilot has been trained to the <learned> level
    And there is a planet at (1,1,1)
    Then the pilot modifier should be <expected>
    Scenarios:
      | learned      | latent        | expected |
      | none         | abysmal       | -2       |
      | elementary   | abysmal       | -1       |
      | trade school | abysmal       | 0        |
      | university   | abysmal       | 1        |
      | master       | abysmal       | 2        |
      | doctorate    | abysmal       | 3        |
      | none         | below average | -1       |
      | elementary   | below average | 0        |
      | trade school | below average | 1        |
      | university   | below average | 2        |
      | master       | below average | 3        |
      | doctorate    | below average | 4        |
      | none         | average       | 0        |
      | elementary   | average       | 1        |
      | trade school | average       | 2        |
      | university   | average       | 3        |
      | master       | average       | 4        |
      | doctorate    | average       | 5        |
      | none         | superior      | 1        |
      | elementary   | superior      | 2        |
      | trade school | superior      | 3        |
      | university   | superior      | 4        |
      | master       | superior      | 5        |
      | doctorate    | superior      | 6        |
      | none         | prodigy       | 2        |
      | elementary   | prodigy       | 3        |
      | trade school | prodigy       | 4        |
      | university   | prodigy       | 5        |
      | master       | prodigy       | 6        |
      | doctorate    | prodigy       | 7        |

  Scenario Outline: Max range calculation with pilot
    When there is a spaceship at (0,0,0)
    And the spaceship is a <classification> <type>
    And the pilot has <latent> latent skills
    And the pilot has been trained to the <learned> level
    And there is a planet at (1,1,1)
    Then the trip is in range
    And the max range should be <expected>
    Scenarios:
      | learned      | latent        | classification | type        | expected |
      | none         | abysmal       | civilian       | freighter   | 8        |
      | elementary   | abysmal       | civilian       | freighter   | 9        |
      | trade school | abysmal       | civilian       | freighter   | 10       |
      | university   | abysmal       | civilian       | freighter   | 11       |
      | master       | abysmal       | civilian       | freighter   | 12       |
      | doctorate    | abysmal       | civilian       | freighter   | 13       |
      | none         | below average | civilian       | freighter   | 9        |
      | elementary   | below average | civilian       | freighter   | 10       |
      | trade school | below average | civilian       | freighter   | 11       |
      | university   | below average | civilian       | freighter   | 12       |
      | master       | below average | civilian       | freighter   | 13       |
      | doctorate    | below average | civilian       | freighter   | 14       |
      | none         | average       | civilian       | freighter   | 10       |
      | elementary   | average       | civilian       | freighter   | 11       |
      | trade school | average       | civilian       | freighter   | 12       |
      | university   | average       | civilian       | freighter   | 13       |
      | master       | average       | civilian       | freighter   | 14       |
      | doctorate    | average       | civilian       | freighter   | 15       |
      | none         | superior      | civilian       | freighter   | 11       |
      | elementary   | superior      | civilian       | freighter   | 12       |
      | trade school | superior      | civilian       | freighter   | 13       |
      | university   | superior      | civilian       | freighter   | 14       |
      | master       | superior      | civilian       | freighter   | 15       |
      | doctorate    | superior      | civilian       | freighter   | 16       |
      | none         | prodigy       | civilian       | freighter   | 12       |
      | elementary   | prodigy       | civilian       | freighter   | 13       |
      | trade school | prodigy       | civilian       | freighter   | 14       |
      | university   | prodigy       | civilian       | freighter   | 15       |
      | master       | prodigy       | civilian       | freighter   | 16       |
      | doctorate    | prodigy       | civilian       | freighter   | 17       |
      | none         | abysmal       | pirate         | freighter   | 8        |
      | elementary   | abysmal       | pirate         | freighter   | 9        |
      | trade school | abysmal       | pirate         | freighter   | 10       |
      | university   | abysmal       | pirate         | freighter   | 11       |
      | master       | abysmal       | pirate         | freighter   | 12       |
      | doctorate    | abysmal       | pirate         | freighter   | 13       |
      | none         | below average | pirate         | freighter   | 9        |
      | elementary   | below average | pirate         | freighter   | 10       |
      | trade school | below average | pirate         | freighter   | 11       |
      | university   | below average | pirate         | freighter   | 12       |
      | master       | below average | pirate         | freighter   | 13       |
      | doctorate    | below average | pirate         | freighter   | 14       |
      | none         | average       | pirate         | freighter   | 10       |
      | elementary   | average       | pirate         | freighter   | 11       |
      | trade school | average       | pirate         | freighter   | 12       |
      | university   | average       | pirate         | freighter   | 13       |
      | master       | average       | pirate         | freighter   | 14       |
      | doctorate    | average       | pirate         | freighter   | 15       |
      | none         | superior      | pirate         | freighter   | 11       |
      | elementary   | superior      | pirate         | freighter   | 12       |
      | trade school | superior      | pirate         | freighter   | 13       |
      | university   | superior      | pirate         | freighter   | 14       |
      | master       | superior      | pirate         | freighter   | 15       |
      | doctorate    | superior      | pirate         | freighter   | 16       |
      | none         | prodigy       | pirate         | freighter   | 12       |
      | elementary   | prodigy       | pirate         | freighter   | 13       |
      | trade school | prodigy       | pirate         | freighter   | 14       |
      | university   | prodigy       | pirate         | freighter   | 15       |
      | master       | prodigy       | pirate         | freighter   | 16       |
      | doctorate    | prodigy       | pirate         | freighter   | 17       |
      | none         | abysmal       | civilian       | corvette    | 3        |
      | elementary   | abysmal       | civilian       | corvette    | 4        |
      | trade school | abysmal       | civilian       | corvette    | 5        |
      | university   | abysmal       | civilian       | corvette    | 6        |
      | master       | abysmal       | civilian       | corvette    | 7        |
      | doctorate    | abysmal       | civilian       | corvette    | 8        |
      | none         | below average | civilian       | corvette    | 4        |
      | elementary   | below average | civilian       | corvette    | 5        |
      | trade school | below average | civilian       | corvette    | 6        |
      | university   | below average | civilian       | corvette    | 7        |
      | master       | below average | civilian       | corvette    | 8        |
      | doctorate    | below average | civilian       | corvette    | 9        |
      | none         | average       | civilian       | corvette    | 5        |
      | elementary   | average       | civilian       | corvette    | 6        |
      | trade school | average       | civilian       | corvette    | 7        |
      | university   | average       | civilian       | corvette    | 8        |
      | master       | average       | civilian       | corvette    | 9        |
      | doctorate    | average       | civilian       | corvette    | 10       |
      | none         | superior      | civilian       | corvette    | 6        |
      | elementary   | superior      | civilian       | corvette    | 7        |
      | trade school | superior      | civilian       | corvette    | 8        |
      | university   | superior      | civilian       | corvette    | 9        |
      | master       | superior      | civilian       | corvette    | 10       |
      | doctorate    | superior      | civilian       | corvette    | 11       |
      | none         | prodigy       | civilian       | corvette    | 7        |
      | elementary   | prodigy       | civilian       | corvette    | 8        |
      | trade school | prodigy       | civilian       | corvette    | 9        |
      | university   | prodigy       | civilian       | corvette    | 10       |
      | master       | prodigy       | civilian       | corvette    | 11       |
      | doctorate    | prodigy       | civilian       | corvette    | 12       |
      | none         | abysmal       | pirate         | corvette    | 4        |
      | elementary   | abysmal       | pirate         | corvette    | 5        |
      | trade school | abysmal       | pirate         | corvette    | 6        |
      | university   | abysmal       | pirate         | corvette    | 7        |
      | master       | abysmal       | pirate         | corvette    | 8        |
      | doctorate    | abysmal       | pirate         | corvette    | 9        |
      | none         | below average | pirate         | corvette    | 5        |
      | elementary   | below average | pirate         | corvette    | 6        |
      | trade school | below average | pirate         | corvette    | 7        |
      | university   | below average | pirate         | corvette    | 8        |
      | master       | below average | pirate         | corvette    | 9        |
      | doctorate    | below average | pirate         | corvette    | 10       |
      | none         | average       | pirate         | corvette    | 6        |
      | elementary   | average       | pirate         | corvette    | 7        |
      | trade school | average       | pirate         | corvette    | 8        |
      | university   | average       | pirate         | corvette    | 9        |
      | master       | average       | pirate         | corvette    | 10       |
      | doctorate    | average       | pirate         | corvette    | 11       |
      | none         | superior      | pirate         | corvette    | 7        |
      | elementary   | superior      | pirate         | corvette    | 8        |
      | trade school | superior      | pirate         | corvette    | 9        |
      | university   | superior      | pirate         | corvette    | 10       |
      | master       | superior      | pirate         | corvette    | 11       |
      | doctorate    | superior      | pirate         | corvette    | 12       |
      | none         | prodigy       | pirate         | corvette    | 8        |
      | elementary   | prodigy       | pirate         | corvette    | 9        |
      | trade school | prodigy       | pirate         | corvette    | 10       |
      | university   | prodigy       | pirate         | corvette    | 11       |
      | master       | prodigy       | pirate         | corvette    | 12       |
      | doctorate    | prodigy       | pirate         | corvette    | 13       |
      | none         | abysmal       | military       | corvette    | 5        |
      | elementary   | abysmal       | military       | corvette    | 6        |
      | trade school | abysmal       | military       | corvette    | 7        |
      | university   | abysmal       | military       | corvette    | 8        |
      | master       | abysmal       | military       | corvette    | 9        |
      | doctorate    | abysmal       | military       | corvette    | 10       |
      | none         | below average | military       | corvette    | 6        |
      | elementary   | below average | military       | corvette    | 7        |
      | trade school | below average | military       | corvette    | 8        |
      | university   | below average | military       | corvette    | 9        |
      | master       | below average | military       | corvette    | 10       |
      | doctorate    | below average | military       | corvette    | 11       |
      | none         | average       | military       | corvette    | 7        |
      | elementary   | average       | military       | corvette    | 8        |
      | trade school | average       | military       | corvette    | 9        |
      | university   | average       | military       | corvette    | 10       |
      | master       | average       | military       | corvette    | 11       |
      | doctorate    | average       | military       | corvette    | 12       |
      | none         | superior      | military       | corvette    | 8        |
      | elementary   | superior      | military       | corvette    | 9        |
      | trade school | superior      | military       | corvette    | 10       |
      | university   | superior      | military       | corvette    | 11       |
      | master       | superior      | military       | corvette    | 12       |
      | doctorate    | superior      | military       | corvette    | 13       |
      | none         | prodigy       | military       | corvette    | 9        |
      | elementary   | prodigy       | military       | corvette    | 10       |
      | trade school | prodigy       | military       | corvette    | 11       |
      | university   | prodigy       | military       | corvette    | 12       |
      | master       | prodigy       | military       | corvette    | 13       |
      | doctorate    | prodigy       | military       | corvette    | 14       |
      | none         | abysmal       | civilian       | transport   | 13       |
      | elementary   | abysmal       | civilian       | transport   | 14       |
      | trade school | abysmal       | civilian       | transport   | 15       |
      | university   | abysmal       | civilian       | transport   | 16       |
      | master       | abysmal       | civilian       | transport   | 17       |
      | doctorate    | abysmal       | civilian       | transport   | 18       |
      | none         | below average | civilian       | transport   | 14       |
      | elementary   | below average | civilian       | transport   | 15       |
      | trade school | below average | civilian       | transport   | 16       |
      | university   | below average | civilian       | transport   | 17       |
      | master       | below average | civilian       | transport   | 18       |
      | doctorate    | below average | civilian       | transport   | 19       |
      | none         | average       | civilian       | transport   | 15       |
      | elementary   | average       | civilian       | transport   | 16       |
      | trade school | average       | civilian       | transport   | 17       |
      | university   | average       | civilian       | transport   | 18       |
      | master       | average       | civilian       | transport   | 19       |
      | doctorate    | average       | civilian       | transport   | 20       |
      | none         | superior      | civilian       | transport   | 16       |
      | elementary   | superior      | civilian       | transport   | 17       |
      | trade school | superior      | civilian       | transport   | 18       |
      | university   | superior      | civilian       | transport   | 19       |
      | master       | superior      | civilian       | transport   | 20       |
      | doctorate    | superior      | civilian       | transport   | 21       |
      | none         | prodigy       | civilian       | transport   | 17       |
      | elementary   | prodigy       | civilian       | transport   | 18       |
      | trade school | prodigy       | civilian       | transport   | 19       |
      | university   | prodigy       | civilian       | transport   | 20       |
      | master       | prodigy       | civilian       | transport   | 21       |
      | doctorate    | prodigy       | civilian       | transport   | 22       |
      | none         | abysmal       | military       | transport   | 15       |
      | elementary   | abysmal       | military       | transport   | 16       |
      | trade school | abysmal       | military       | transport   | 17       |
      | university   | abysmal       | military       | transport   | 18       |
      | master       | abysmal       | military       | transport   | 19       |
      | doctorate    | abysmal       | military       | transport   | 20       |
      | none         | below average | military       | transport   | 16       |
      | elementary   | below average | military       | transport   | 17       |
      | trade school | below average | military       | transport   | 18       |
      | university   | below average | military       | transport   | 19       |
      | master       | below average | military       | transport   | 20       |
      | doctorate    | below average | military       | transport   | 21       |
      | none         | average       | military       | transport   | 17       |
      | elementary   | average       | military       | transport   | 18       |
      | trade school | average       | military       | transport   | 19       |
      | university   | average       | military       | transport   | 20       |
      | master       | average       | military       | transport   | 21       |
      | doctorate    | average       | military       | transport   | 22       |
      | none         | superior      | military       | transport   | 18       |
      | elementary   | superior      | military       | transport   | 19       |
      | trade school | superior      | military       | transport   | 20       |
      | university   | superior      | military       | transport   | 21       |
      | master       | superior      | military       | transport   | 22       |
      | doctorate    | superior      | military       | transport   | 23       |
      | none         | prodigy       | military       | transport   | 19       |
      | elementary   | prodigy       | military       | transport   | 20       |
      | trade school | prodigy       | military       | transport   | 21       |
      | university   | prodigy       | military       | transport   | 22       |
      | master       | prodigy       | military       | transport   | 23       |
      | doctorate    | prodigy       | military       | transport   | 24       |
      | none         | abysmal       | pirate         | dreadnaught | 23       |
      | elementary   | abysmal       | pirate         | dreadnaught | 24       |
      | trade school | abysmal       | pirate         | dreadnaught | 25       |
      | university   | abysmal       | pirate         | dreadnaught | 26       |
      | master       | abysmal       | pirate         | dreadnaught | 27       |
      | doctorate    | abysmal       | pirate         | dreadnaught | 28       |
      | none         | below average | pirate         | dreadnaught | 24       |
      | elementary   | below average | pirate         | dreadnaught | 25       |
      | trade school | below average | pirate         | dreadnaught | 26       |
      | university   | below average | pirate         | dreadnaught | 27       |
      | master       | below average | pirate         | dreadnaught | 28       |
      | doctorate    | below average | pirate         | dreadnaught | 29       |
      | none         | average       | pirate         | dreadnaught | 25       |
      | elementary   | average       | pirate         | dreadnaught | 26       |
      | trade school | average       | pirate         | dreadnaught | 27       |
      | university   | average       | pirate         | dreadnaught | 28       |
      | master       | average       | pirate         | dreadnaught | 29       |
      | doctorate    | average       | pirate         | dreadnaught | 30       |
      | none         | superior      | pirate         | dreadnaught | 26       |
      | elementary   | superior      | pirate         | dreadnaught | 27       |
      | trade school | superior      | pirate         | dreadnaught | 28       |
      | university   | superior      | pirate         | dreadnaught | 29       |
      | master       | superior      | pirate         | dreadnaught | 30       |
      | doctorate    | superior      | pirate         | dreadnaught | 31       |
      | none         | prodigy       | pirate         | dreadnaught | 27       |
      | elementary   | prodigy       | pirate         | dreadnaught | 28       |
      | trade school | prodigy       | pirate         | dreadnaught | 29       |
      | university   | prodigy       | pirate         | dreadnaught | 30       |
      | master       | prodigy       | pirate         | dreadnaught | 31       |
      | doctorate    | prodigy       | pirate         | dreadnaught | 32       |
      | none         | abysmal       | military       | dreadnaught | 18       |
      | elementary   | abysmal       | military       | dreadnaught | 19       |
      | trade school | abysmal       | military       | dreadnaught | 20       |
      | university   | abysmal       | military       | dreadnaught | 21       |
      | master       | abysmal       | military       | dreadnaught | 22       |
      | doctorate    | abysmal       | military       | dreadnaught | 23       |
      | none         | below average | military       | dreadnaught | 19       |
      | elementary   | below average | military       | dreadnaught | 20       |
      | trade school | below average | military       | dreadnaught | 21       |
      | university   | below average | military       | dreadnaught | 22       |
      | master       | below average | military       | dreadnaught | 23       |
      | doctorate    | below average | military       | dreadnaught | 24       |
      | none         | average       | military       | dreadnaught | 20       |
      | elementary   | average       | military       | dreadnaught | 21       |
      | trade school | average       | military       | dreadnaught | 22       |
      | university   | average       | military       | dreadnaught | 23       |
      | master       | average       | military       | dreadnaught | 24       |
      | doctorate    | average       | military       | dreadnaught | 25       |
      | none         | superior      | military       | dreadnaught | 21       |
      | elementary   | superior      | military       | dreadnaught | 22       |
      | trade school | superior      | military       | dreadnaught | 23       |
      | university   | superior      | military       | dreadnaught | 24       |
      | master       | superior      | military       | dreadnaught | 25       |
      | doctorate    | superior      | military       | dreadnaught | 26       |
      | none         | prodigy       | military       | dreadnaught | 22       |
      | elementary   | prodigy       | military       | dreadnaught | 23       |
      | trade school | prodigy       | military       | dreadnaught | 24       |
      | university   | prodigy       | military       | dreadnaught | 25       |
      | master       | prodigy       | military       | dreadnaught | 26       |
      | doctorate    | prodigy       | military       | dreadnaught | 27       |
