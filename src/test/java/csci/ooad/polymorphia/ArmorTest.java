package csci.ooad.polymorphia;

import csci.ooad.polymorphia.artifacts.Armor;
import csci.ooad.polymorphia.artifacts.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ArmorTest {

    @Test
    void testArmorCreation() {
        Armor armor = new Armor("Bronze");
        String name = armor.name();
        assertTrue(name.equals("Bronze"));
    }
}