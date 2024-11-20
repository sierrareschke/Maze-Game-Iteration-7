package csci.ooad.polymorphia.command;

import csci.ooad.polymorphia.characters.Character;

public class CommandFactory {

    public static Command createMoveCommand(Character character) {
        return new MoveCommand(character);
    }

    // Note: add another move command method if need target room passed in

    public static Command createEatCommand(Character character) {
        return new EatCommand(character);
    }


    public static Command createFightCommand(Character character, Character opponent) {
        return new FightCommand(character, opponent);
    }

    public static Command createWearArmorCommand(Character character) {
        return new WearArmorCommand(character);
    }




}
