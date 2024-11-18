package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.maze.Maze;
import csci.ooad.polymorphia.NoSuchRoomException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GluttonTest {

    @Test
    void testEating() {
        // Arrange
        Double initialHealth = 5.0;
        Character glutton = CharacterFactory.createGlutton("Brad");
        Maze.getNewBuilder()
                .createFullyConnectedRooms(1)
                .addAdventurers(glutton)
                .createAndAddCreatures("Ogre")
                .createAndAddFoodItems("Cake")
                .build();

        // Act - the glutton should not fight. It should eat
        glutton.doAction();

        // Assert – the glutton ate the cake and gain 1 health point
        assertEquals(initialHealth + 1, glutton.getHealth());
    }

    @Test
    void testFighting() throws NoSuchRoomException {
        // Arrange - put Demon in room with Glutton
        Character glutton = CharacterFactory.createGlutton("Charlie");
        Character satan = CharacterFactory.createDemon("Demon");
        Double satanInitialHealth = satan.getHealth();
        Food steak = new Food("Steak");
        Maze twoRoomMaze = Maze.getNewBuilder()
                .createFullyConnectedRooms("initial", "final")
                .addToRoom("initial", glutton)
                .addToRoom("initial", satan)
                .addToRoom("initial", steak)
                .build();

        // Act - the coward must fight a Demon
        glutton.doAction();

        // Assert – the coward ran to the other room
        assertTrue(satan.getHealth() < satanInitialHealth, "Expected health to be less than health, but was " + satan.getHealth());
        assertTrue(twoRoomMaze.getRoom("initial").hasFood());
    }
}
