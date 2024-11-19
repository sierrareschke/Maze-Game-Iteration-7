package csci.ooad.polymorphia.strategy;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;

public interface HumanStrategy {
    Command prompt(Character character);
}
