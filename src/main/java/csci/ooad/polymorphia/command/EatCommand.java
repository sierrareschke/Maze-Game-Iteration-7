package csci.ooad.polymorphia.command;

import csci.ooad.polymorphia.EventType;
import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.maze.Room;

import static csci.ooad.polymorphia.EventBus.post;


public class EatCommand implements Command {
    private Character character;
    // TODO - pass room or food item to EatCommand ?

    public EatCommand(Character character) {
        this.character = character;
    }

    @Override
    public void execute() {
        Room room = character.getCurrentLocation();
        Boolean hasFood = room.hasFood();
        if (hasFood) {
            Food foodItem = room.getFoodItems().get(0);
            character.gainHealth(foodItem.getHealthValue());
            String message = character.getName() + " just ate " + foodItem.getName();
            post(EventType.AteSomething, message);
            room.removeFoodItem();
        }
    }
}
