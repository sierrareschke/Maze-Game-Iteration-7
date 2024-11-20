package csci.ooad.polymorphia.strategy;
import csci.ooad.polymorphia.HumanOption;
import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HumanStrategy extends Strategy {
    @Override
    public Optional<HumanOption> prompt(Character character) {
        System.out.print("You are in room " + character.getCurrentLocation() + "\n\n");

        List<HumanOption> options = List.of(HumanOption.EAT, HumanOption.FIGHT, HumanOption.MOVE, HumanOption.DO_NOTHING);
        for (HumanOption option : options) {
            System.out.println(option.value() + ": " + option.name());
        }

        while (true) {  // This exits the method if a valid option is selected
            System.out.print("Enter your option: ");
            Scanner scanner = new Scanner(System.in);

            int choiceNumber = Integer.parseInt(scanner.nextLine()) - 1;
            return options.stream()
                    .filter(option -> option.value() == choiceNumber)
                    .findFirst();
        }

    }


    @Override
    public Command fight(Character human) {
        Room currentRoom = human.getCurrentLocation();
        Character creature = currentRoom.getHealthiestCreature();
        return CommandFactory.createFightCommand(human, creature);
    }

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
