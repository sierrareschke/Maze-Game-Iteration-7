package csci.ooad.polymorphia.command;

import csci.ooad.polymorphia.artifacts.ArmorDecorator;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.artifacts.Armor;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.maze.Room;

public class WearArmorCommand implements Command {
    private Character character;

    public WearArmorCommand(Character character) {
        this.character = character;
    }

    @Override
    public void execute() {
        Room room = character.getCurrentLocation();
        if (!room.getArmor().isEmpty()) {
            room.getArmor().remove(0); // Remove armor from the room
            Character wrappedCharacter = new ArmorDecorator(character);   // Wrap character in armor
            room.remove(character);
            room.add(wrappedCharacter);
            character = wrappedCharacter;
        }
    }
}
