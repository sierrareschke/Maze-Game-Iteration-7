package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.HumanOption;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

import java.util.Optional;

public class AdventurerStrategy extends Strategy {

    @Override
    public Command fight(csci.ooad.polymorphia.characters.Character adventurer) {
        Room currentRoom = adventurer.getCurrentLocation();
        Boolean creatureInRoomWithMe = currentRoom.hasLivingCreatures();
        Boolean iAmHealthiestInRoom = currentRoom.getHealthiestAdventurer() == adventurer;
        if (creatureInRoomWithMe && iAmHealthiestInRoom) {
            Character creature = currentRoom.getHealthiestCreature();
            return CommandFactory.createFightCommand(adventurer, creature);
        } else {
            return null;
        }
    }

}
