package csci.ooad.polymorphia.artifacts;

import csci.ooad.polymorphia.characters.Character;

public abstract class ArmorDecorator extends Character {
    protected Character wrappedCharacter;

    public ArmorDecorator(Character character) {
        super(character);
        this.wrappedCharacter = character;
    }

//    @Override
//    public String getDescription() {
//        return wrappedCharacter.getDescription();
//    }
//
//    @Override
//    public int fightImpact() {
//        return wrappedCharacter.fightImpact();
//    }
}
