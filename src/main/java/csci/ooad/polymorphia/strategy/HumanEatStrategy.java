package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;

public class HumanEatStrategy implements EatStrategy {
    @Override
    public Command eat(Character character) {
        System.out.println("Human eat");
        // Do something here
        return null;
    }
}


