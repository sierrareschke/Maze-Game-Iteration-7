package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;

public class CreatureStrategy extends Strategy {

    @Override
    Command eat(Character character){
        return null;
    }

    @Override
    public Command move(Character character) {
        return null;
    }

}
