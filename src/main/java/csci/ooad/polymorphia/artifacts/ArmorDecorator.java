package csci.ooad.polymorphia.artifacts;

import csci.ooad.polymorphia.characters.Character;

public class ArmorDecorator extends Character {
    private Character character;
    private Armor armor;

    // TODO - Not sure this is how we're suppose to do this
    public ArmorDecorator(Character character, Armor armor) {
        super(character);
        this.character = character;
        this.armor = armor;
    }

    @Override
    public String getName() {
        return character.getName();
    }

    @Override
    public int getHealth() {
        return character.getHealth();
    }

    @Override
    public int getEffectiveDefense() {
        return character.getEffectiveDefense() + armor.getDefense();
    }
}