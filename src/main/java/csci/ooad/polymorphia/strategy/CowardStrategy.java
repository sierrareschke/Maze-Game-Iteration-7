package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

public class CowardStrategy extends Strategy{
    @Override
    public Command fight(Character coward){
        Room currentRoom = coward.getCurrentLocation();
        Boolean cannotMove = currentRoom.getNeighbors().isEmpty();
        Boolean isDemonPresent = currentRoom.hasDemon();
        if (cannotMove || isDemonPresent) {
            Character demon = currentRoom.getDemon();
            return CommandFactory.createFightCommand(coward, demon);
        } else {
            return null;
        }
    }

}
