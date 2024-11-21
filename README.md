[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/MktpU50_)
# OOAD Homework 8:
## Extending Polymorphia with a Decorator pattern and Human player
#### (55 points)

High Level TODOs:
- Create a static main
- Use the Decorator Pattern to implement Armor
- Create and place Armor (5 points)
- ArtifactFactory (5 points)
- ArmoredCharacter (15 points)
- Create Random Strategy (5 points)
- Create Human Strategy (15 points)
- Create a Command-line Interface in a static main method (10 points)

Armor and the Decorator Pattern
- Knights can now wear armor
- If the character loses a fight, it will lose 1 less health point because the armor protects them.
- There is no limit to the number of armored suits a character can wear, but make sure that an armored character cannot gain health if they lose a fight.
An armored character loses 0.1 more health points (per armored suit worn) when it moves, due to the weight of the armor.

Create a Random Strategy
- Create a strategy that will randomly select an action from all the possible actions for the character in their current circumstances (room). Make this the default strategy for Adventurers (not knights, gluttons, or cowards).



### Introduction
#### Team Members: 
Grace Ohlsen, Sierra Reschke and Nolan Brady 

#### Java Version: 21

#### Comments/Assumptions: 
* We assumed the Knight picked up armor if there was no creature in the room. 
Command line arguments for human-playable game:
--numberOfRooms 9 
--numberOfAdventurers 4 
--numberOfCreatures 5 
--numberOfDemons 1 
--numberOfFoodItems 5 
--numberOfArmor 4  
--humanPlayer "Sierra Reschke"



## Test Coverage
![](homework_7_tests.png)

## Grading Rubric:

### Deductions

    NOTE: for this assignment you do NOT need to worry about method coverage and you do NOT need to submit a screenshot of the coverage

* Meaningful names for everything: variables, methods, classes, interfaces, etc. (1% off for each bad name, up to 10% total)
* No "magic" numbers or strings (1% off for each one, up to 10%)
* No System.out.println() calls anywhere in your main code – replace with logging (see below) or eliminate outright. 1% off for each System.out.println statement in src/main/java code.
* 1% deduction for each missing required addition to the README.md (game outputs, screenshots, diagrams)

### Method Construction Possible Deductions (max is listed under Required Capabilities)

Methods should be:
* "short" -- with very few exceptions all methods should fit on a screen using a readable font.
* well named (duh).
* properly denoted as instance methods vs. static methods (static methods don't reference the _this_ pointer).
* limited complexity (level of indentation due to control structures).
* not have comments that could be turned into just as readable code.

All of this can be achieved through functional decomposition of more complicated methods (see lecture on October 2nd).

## Game Extensions
* If adventurers are in the room with the Demon, the Demon will fight the healthiest one
* If no adventurers are in the room, the Demon will move to a random neighboring room

### Required Capabilities

* Use the Strategy Pattern to inject behavior into the Adventurer class so that we can create Characters that behave like our previous subclasses of Knight, Glutton, and Coward. (15 points)
* Use the Strategy Pattern to do the same with Demon subclass of Creature. (5 points)
* Use the Command Pattern to return the action to execute from the Strategy Pattern classes (20 points)
* Use the Factory Pattern to create the Command objects (5 points)

