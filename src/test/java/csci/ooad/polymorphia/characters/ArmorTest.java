package csci.ooad.polymorphia.characters;
import csci.ooad.polymorphia.NoSuchRoomException;
import csci.ooad.polymorphia.artifacts.Armor;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.command.CommandFactory;
import csci.ooad.polymorphia.maze.Maze;
import csci.ooad.polymorphia.maze.Room;
import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.BeforeEach;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArmorTest {
    Character joe;
    CharacterFactory characterFactory = new CharacterFactory();
    ArtifactFactory artifactFactory = new ArtifactFactory();

    @BeforeEach
    void setUp() {
        Double initialHealth = 5.0;
        joe = CharacterFactory.createAdventurer("Joe", Optional.of(initialHealth));
    }

    @Test
    void testFindingArmorAndFighting() throws NoSuchRoomException {
        // If a character picks up Armor, then they are more resistant to damage.


        // Arrange - put creature in room with adventurer
        Character knight = characterFactory.createKnight("Knight", Optional.empty());
        Character ogre = characterFactory.createCreature("Ogre", Optional.empty());
        Armor bronzeArmor = artifactFactory.createArmor("Bronze");
        Armor steelArmor = artifactFactory.createArmor("Steel");


        Maze maze = Maze.getNewBuilder().createFullyConnectedRooms("Adventurer Room", "Another Armor Room", "Ogre Room")
                .addToRoom("Adventurer Room", knight)
                .addToRoom("Adventurer Room", bronzeArmor)
                .addToRoom("Another Armor Room", steelArmor)
                .addToRoom("Ogre Room", ogre)
                .build();


        Command wearArmor = CommandFactory.createWearArmorCommand(knight);
        wearArmor.execute();


        // Get the armored adventurer
        assertEquals(1, maze.getLivingAdventurers().size());
        Character armoredKnight = maze.getLivingAdventurers().getFirst();


        System.out.println("armoredKnight: " + armoredKnight);
        assertTrue(armoredKnight.toString().contains("armor"));


        Command moveCommand = CommandFactory.createMoveCommand(armoredKnight);
        moveCommand.execute();
        assertEquals(1, maze.getLivingAdventurers().size());

        Command wearSecondArmor = CommandFactory.createWearArmorCommand(armoredKnight);
        wearSecondArmor.execute();
        assertEquals(1, maze.getLivingAdventurers().size());


        Character doublyArmoredKnight = maze.getLivingAdventurers().getFirst();
        System.out.println("doublyArmoredBilbo: " + doublyArmoredKnight);


        Room ogreRoom = maze.getRoom("Ogre Room");
        ogreRoom.enter(doublyArmoredKnight);


        // Now fight the Ogre with two sets of Armor
        Command fightCommand = CommandFactory.createFightCommand(doublyArmoredKnight, ogre);
        fightCommand.execute();
    }
}
