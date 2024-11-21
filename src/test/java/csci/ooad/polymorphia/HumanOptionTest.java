package csci.ooad.polymorphia;

import static org.junit.jupiter.api.Assertions.*;

import csci.ooad.polymorphia.HumanOption;
import org.junit.jupiter.api.Test;

public class HumanOptionTest {
    @Test
    public void testImmutability() {
        assertEquals("Eat", HumanOption.EAT.name());
        assertEquals(1, HumanOption.EAT.value());

        assertEquals("Fight", HumanOption.FIGHT.name());
        assertEquals(2, HumanOption.FIGHT.value());
    }
}