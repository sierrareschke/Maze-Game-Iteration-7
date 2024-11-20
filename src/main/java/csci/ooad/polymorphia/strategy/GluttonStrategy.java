package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

import java.util.List;

public class GluttonStrategy extends Strategy {
    @Override
    public Command eat(Character character) {
        Room room = character.getCurrentLocation();
        List<Food> foodItems = room.getFoodItems();
        if (!foodItems.isEmpty()) {
            return CommandFactory.createEatCommand(character);
        }
        return null;
    }

    @Override
    public Command fight(Character glutton) {
        Room currentRoom = glutton.getCurrentLocation();
        Boolean isDemonPresent = currentRoom.hasDemon();
        Boolean isFoodPresent = currentRoom.hasFood();
        if (isDemonPresent) {
            Character creature = currentRoom.getDemon();
            return CommandFactory.createFightCommand(glutton, creature);
        } else {
            return null;
        }
    }
}
