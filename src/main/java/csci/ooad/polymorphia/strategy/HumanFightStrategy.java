package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Room;

public class HumanFightStrategy implements FightStrategy {

    @Override
    public Command fight(Character human) {
        System.out.println("Human fight");
        // Do something here
       return null;
    }
}

