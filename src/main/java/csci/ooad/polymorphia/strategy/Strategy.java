package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.HumanOption;
import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

import java.util.List;
import java.util.Optional;

public abstract class Strategy {
    public Command move(Character character){
        return CommandFactory.createMoveCommand(character);
    }
    public Command fight(Character character){
        return null;
    }
    public Command eat(Character character){
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
    public Command wearArmor(Character character){
        return null;
    }

    public Optional<HumanOption> prompt(Character character) {
        return Optional.empty();
    }
}
