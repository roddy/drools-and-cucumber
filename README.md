# drools-and-cucumber
This is a demo application demonstrating how to use Cucumber (BDD) with Drools business rules.

This is a toy project that contains business logic regarding an imaginary application dealing with aliens, spaceships,
and outer space piracy. It only contains the business logic part of the application, which is encapsulated in a [JBoss
Drools](http://www.drools.org/) business rules engine. There is also some Java code, but that is limited to the input
objects for the rules.

## What is this?

This is the beginning of a sort of "Space Pirate" game. It uses Drools to do some validation and calculation (eg. 
verifying that a ship, planet, etc. is valid; calculating whether a ship can transit from point A to point B). Since the
purpose of this project isn't to actually have a working game, but rather to showcase how Behavior Driven Design (BDD)
principles can be used with Drools, it's not particularly interesting outside of the unit tests.

(Of course, it might eventually end up a full fledged game. Who knows? The original code was written in 2015, so 
anything is possible.)

## How to run

This application is primarily for showcasing the unit tests written in cucumber-java. This is a Maven project with the
following dependencies:

* Java 17 (OpenJDK)
* Drools 7.49.0.Final
* Cucumber 6.10.4
* JUnit 5.8.1 (with Vintage)

To build and run the unit tests, use the following command:

```
mvn clean test
```

If you are using IntelliJ IDEA, which has Gherkin support, you can explore the features under `src/test/features` and 
ctrl+click or cmd+click to navigate to the associated step definitions. 

## More info

There is an open Stackoverflow question about registering custom Cucumber ParameterTypes which was created while working
on this project. It can be found 
[here](https://stackoverflow.com/questions/69474687/cucumber-java-wont-use-custom-parametertype). In the meantime a 
less-efficient workaround was applied pending a resolution to that question. 

This project has a dependency on JavaFX, but it isn't technically a JavaFX project. The original version of this project
was written in Java 8, when JavaFX was part of the regular JDK. I used the Point3D class from JavaFX because it was 
easier than rolling my own and it came from some useful utility functions. Fast forward to 2021, and JavaFX is long 
divorced from the JDK. To date we still only use Point3D, but we have to pull in a decently sized geometry-related 
module from JavaFX to do so. Future work may either remove JavaFX entirely, or integrate further, depending on how this
project evolves (if ever.) 