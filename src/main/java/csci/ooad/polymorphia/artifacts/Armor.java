package csci.ooad.polymorphia.artifacts;
import csci.ooad.polymorphia.characters.Character;

public class Armor extends ArmorDecorator{
    String armorName;

    public Armor(Character character, String armorName) {
        super(character);
        this.armorName = armorName;
    }
}
