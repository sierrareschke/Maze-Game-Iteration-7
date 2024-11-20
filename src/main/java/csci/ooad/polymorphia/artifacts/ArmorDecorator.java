package csci.ooad.polymorphia.artifacts;

import csci.ooad.polymorphia.characters.Character;

public class ArmorDecorator extends Character {
    protected Character wrappedCharacter;

    public ArmorDecorator(Character character) {
        super(character.getName(), character.getFightStrategy(), character.getMoveStrategy(), character.getEatStrategy());
        this.wrappedCharacter = character;
    }

    @Override
    public void loseFightDamage(double fightDamage) {
        // Reduce fight damage by 1 but ensure health cannot increase
        double adjustedDamage = Math.max(0, fightDamage - 1);
        wrappedCharacter.loseFightDamage(adjustedDamage);
    }

    @Override
    public void loseMoveDamage(double moveDamage) {
        // Add 0.1 extra damage per armor suit
        double adjustedDamage = moveDamage + 0.1;
        wrappedCharacter.loseMoveDamage(adjustedDamage);
    }

    @Override
    public String getName() {
        return wrappedCharacter.getName();
    }

    // Delegate all other methods to the wrappedCharacter
}
