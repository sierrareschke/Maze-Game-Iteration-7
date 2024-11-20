package csci.ooad.polymorphia.artifacts;
import csci.ooad.polymorphia.characters.Character;

public record Armor(String name){
    String armorName;

    public Armor(Character character, String armorName) {
        super(character);
        this.armorName = armorName;
    }
}
