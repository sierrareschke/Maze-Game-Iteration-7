package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

import java.util.List;

public class HumanEatStrategy implements EatStrategy {
    @Override
    public Command eat(Character character) {
        Room room = character.getCurrentLocation();
        List<Food> foodItems = room.getFoodItems();
        // if there is food, the character will eat
        if (!foodItems.isEmpty()) {
            return CommandFactory.createEatCommand(character);
        }
        else{ // if there is not food, the character will move
            return null; // CommandFactory.createMoveCommand(character);
        }
    }
}


