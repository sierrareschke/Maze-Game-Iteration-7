package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.Food;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

import java.util.List;

public class HumanEatStrategy implements EatStrategy {
    @Override
    public Command eat(Character character) {
        System.out.println("Human eat");
        // Do something here
        return null;
    }
}


