package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.maze.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    Character joe;

    @BeforeEach
    void setUp() {
        Double initialHealth = 5.0;
        joe = CharacterFactory.createAdventurer("Joe", Optional.of(initialHealth));
    }

    @Test
    void testToString() {

        assertTrue(joe.toString().contains("Joe"));
    }

    @Test
    void isAlive() {
        assertTrue(joe.isAlive());
    }

    @Test
    void testSorting() {
        double mostHealth = 10.0;
        double mediumHealth = 5.0;
        double leastHealth = 3.0;

        List<Character> characters = new ArrayList<>(Arrays.asList(
                CharacterFactory.createAdventurer("Frodo", Optional.of(mostHealth)),
                CharacterFactory.createCreature("Ogre", Optional.of(mediumHealth)),
                CharacterFactory.createAdventurer("Arwen", Optional.of(leastHealth))
        ));

        Collections.sort(characters);
    }

    @Test
    void testLoseHealthAndDeath() {
        joe.loseHealth(3.0);
        assertEquals(2.0, joe.getHealth());

        joe.loseHealth(2.0);
        assertFalse(joe.isAlive());
    }

    @Test
    void testFightingMandatoryLossOfHalfAPoint() {
        Character ogre = CharacterFactory.createCreature("Ogre", Optional.empty());
        Room room = new Room("Room 1");
        room.add(ogre);
        room.add(joe);
        joe.doAction();
        // Joe should have lost 0.5 health and he started with a integer health value
        // of 5.0. After the fight he should have 4.5 health. Or 3.5, or 2.5, etc. depending
        // upon the outcome of the fight. So, we just check to make sure the health is x.5
        assertEquals(0.5, joe.getHealth() % 1);
    }

    @Test
    void testMovingWithNoNeighbors() {
        Room room = new Room("room");
        Character adventurer = CharacterFactory.createAdventurer("Adventurer", Optional.empty());
        room.add(adventurer);

        // Act -- no error occurs
        adventurer.doAction();
    }

    @Test
    void testEatingFood() {
        Room room = new Room("room");
        Character adventurer = CharacterFactory.createAdventurer("Adventurer", Optional.empty());
        Double characterInitialHealth = adventurer.getHealth();
        room.add(adventurer);
        Food popcorn = new Food("popcorn");
        room.add(popcorn);

        adventurer.doAction();

        assertEquals(adventurer.getHealth(), characterInitialHealth + popcorn.getHealthValue() );
        assertFalse(room.hasFood());
    }

    @Test
    void testFighting() {
        Character adventurer = CharacterFactory.createAdventurer("Adventurer", Optional.empty());
        Character creature = CharacterFactory.createCreature("Creature", Optional.empty());
        Room room = new Room("room");
        room.add(adventurer);
        room.add(creature);
        double initialHealth = adventurer.getHealth();
        adventurer.doAction();

        assertNotEquals(initialHealth, adventurer.getHealth());
    }

    @Test
    void testCreatureDoesNotDoAction() {
        Character creature = CharacterFactory.createCreature("Creature", Optional.empty());
        creature.doAction();
    }
}