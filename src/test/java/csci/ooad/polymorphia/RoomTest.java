package csci.ooad.polymorphia;
import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.CharacterFactory;
import csci.ooad.polymorphia.maze.Room;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void getRandomNeighbor() {
        Room room = new Room("mainRoom");
        Room neighbor = new Room("neighbor");
        room.connect(neighbor);

        assertEquals(room.getRandomNeighbor(), neighbor);
    }

    @Test
    void testGetRandomNeighborOnRoomWithNoNeighbors() {
        Room room = new Room("onlyRoom");
        assertNull(room.getRandomNeighbor());
    }

    @Test
    void testToString() {
        Room room = new Room("onlyRoom");
        Character frodo = CharacterFactory.createAdventurer("Frodo", Optional.empty());
        room.add(frodo);
        Character ogre = CharacterFactory.createCreature("Ogre", Optional.empty());
        room.add(ogre);
    }
//    @Test
//    void testToString() {
//        Room room = new Room("onlyRoom");
//        room.add(new Adventurer("Frodo"));
//        room.add(new Creature("Ogre"));
//
//        assertTrue(room.toString().contains("onlyRoom"));
//        assertTrue(room.toString().contains("Frodo"));
//        assertTrue(room.toString().contains("Ogre"));
//    }

//    @Test
//    void testGetHealthiestAdventurer() {
//        // Arrange
//        double highestHealth = 5;
//        double lowestHealth = 3;
//
//        Room room = new Room("onlyRoom");
//        Character bilbo = new Character("Bilbo", highestHealth);
//        room.add(bilbo);
//        room.add(new Character("Frodo", lowestHealth));
//        Creature troll = new Creature("Troll", highestHealth);
//        room.add(troll);
//        room.add(new Creature("Orc", lowestHealth));
//
//        // Act
//        Adventurer fittestAdventurer = room.getHealthiestAdventurer();
//        Creature fittestCreature = room.getHealthiestCreature();
//
//        // Assert
//        assertEquals(bilbo, fittestAdventurer);
//        assertEquals(troll, fittestCreature);
//    }

    @Test
    void testGetHealthiestAdventurer() {
        // Arrange
        double highestHealth = 5.0;
        double lowestHealth = 3.0;

        Room room = new Room("onlyRoom");
        Character bilbo = CharacterFactory.createAdventurer("Bilbo", Optional.of(highestHealth));
        room.add(bilbo);
        Character frodo = CharacterFactory.createAdventurer("Frodo", Optional.of(lowestHealth));
        room.add(frodo);
        Character troll = CharacterFactory.createCreature("Troll", Optional.of(highestHealth));
        room.add(troll);
        Character orc = CharacterFactory.createCreature("Orc", Optional.of(lowestHealth));
        room.add(orc);

        // Act
        Character fittestAdventurer = room.getHealthiestAdventurer();
        Character fittestCreature = room.getHealthiestCreature();

        // Assert
        assertEquals(bilbo, fittestAdventurer);
        assertEquals(troll, fittestCreature);
    }

    @Test
    void testHealthiestEatsFood() {
        // Arrange
        double highestHealth = 5;
        double lowestHealth = 3;

        Room room = new Room("onlyRoom");
        room.add(new Food("burger"));
        Character bilbo = CharacterFactory.createAdventurer("Bilbo", Optional.of(highestHealth));
        room.add(bilbo);
        Character frodo = CharacterFactory.createAdventurer("Frodo", Optional.of(lowestHealth));
        room.add(frodo);
        Boolean hasFood = room.hasFood();

        // Act
        bilbo.doAction();

        Boolean stillHasFood = room.hasFood();
        
        // Assert
        assertEquals(Food.DEFAULT_FOOD_HEALTH_VALUE + highestHealth, bilbo.getHealth());
    }

//    @Test
//    void testHealthiestEatsFood() {
//        // Arrange
//        double highestHealth = 5;
//        double lowestHealth = 3;
//
//        Room room = new Room("onlyRoom");
//        Adventurer bilbo = new Adventurer("Bilbo", highestHealth);
//        room.add(bilbo);
//        room.add(new Adventurer("Frodo", lowestHealth));
//        room.add(new Food("burger"));
//
//        // Act
//        bilbo.doAction();
//
//        // Assert
//        assertEquals(bilbo.getHealth(), highestHealth + Food.DEFAULT_FOOD_HEALTH_VALUE);
//    }


    @Test
    void testEatNonExistentFood() {
        // Arrange
        Room room = new Room("onlyRoom");
        assertThrows(NoFoodException.class, room::removeFoodItem);
    }
}