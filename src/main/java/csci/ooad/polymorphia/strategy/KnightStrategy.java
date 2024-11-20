package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

public class KnightStrategy extends Strategy {

    @Override
    public Command fight(Character knight) {
        Room currentRoom = knight.getCurrentLocation();
        Boolean isCreaturePresent = currentRoom.hasLivingCreatures();
        if (isCreaturePresent) {
            Character creature = currentRoom.getHealthiestCreature();
            return CommandFactory.createFightCommand(knight, creature);
        } else {
            return null;
        }
    }

    @Override
    public Command wearArmor(Character knight) {
        return CommandFactory.createWearArmorCommand(knight);
    }
}
