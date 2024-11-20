package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStrategy extends Strategy {
    @Override
    public Command fight(csci.ooad.polymorphia.characters.Character adventurer) {
        final Random random = new Random();

        Room currentRoom = adventurer.getCurrentLocation();
        List<Command> possibleCommands = new ArrayList<>();

        // Add "fight" command if there are creatures in the room
        Boolean creatureInRoomWithMe = currentRoom.hasLivingCreatures();
        if (creatureInRoomWithMe) {
            Character creature = currentRoom.getHealthiestCreature();
            possibleCommands.add(CommandFactory.createFightCommand(adventurer, creature));
        }

        // Add "move" command if there are neighboring rooms
        if (!currentRoom.getNeighbors().isEmpty()) {
            possibleCommands.add(CommandFactory.createMoveCommand(adventurer));
        }

        // Add "eat" command if there are neighboring rooms
        if (currentRoom.hasFood()) {
            possibleCommands.add(CommandFactory.createEatCommand(adventurer));
        }

        if (currentRoom.hasArmor()) {
            possibleCommands.add(CommandFactory.createWearArmorCommand(adventurer));
        }

        return possibleCommands.isEmpty() ? null : possibleCommands.get(random.nextInt(possibleCommands.size()));

    }
}
