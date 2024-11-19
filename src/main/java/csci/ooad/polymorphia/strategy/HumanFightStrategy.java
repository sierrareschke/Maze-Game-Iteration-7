package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

public class HumanFightStrategy implements FightStrategy {

    @Override
    public Command fight(Character human) {
        Room currentRoom = human.getCurrentLocation();
        Character creature = currentRoom.getHealthiestCreature();
       return CommandFactory.createFightCommand(human, creature);
    }
}

