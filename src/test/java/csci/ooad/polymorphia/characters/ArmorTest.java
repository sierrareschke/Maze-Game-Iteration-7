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
        Character bilbo = characterFactory.createAdventurer("Bilbo", Optional.empty());
        Character ogre = characterFactory.createCreature("Ogre", Optional.empty());
        Armor bronzeArmor = artifactFactory.createArmor("Bronze");
        Armor steelArmor = artifactFactory.createArmor("Steel");


        Maze maze = Maze.getNewBuilder()
                .createFullyConnectedRooms("Adventurer Room", "Another Armor Room", "Ogre Room")
                .addToRoom("Adventurer Room", bilbo)
                .addToRoom("Adventurer Room", bronzeArmor)
                .addToRoom("Another Armor Room", steelArmor)
                .addToRoom("Ogre Room", ogre)
                .build();


        Command wearArmor = CommandFactory.createWearArmorCommand(bilbo);
        wearArmor.execute();


        // Get the armored adventurer
        assertEquals(1, maze.getLivingAdventurers().size());
        Character armoredBilbo = maze.getLivingAdventurers().getFirst();


        System.out.println("armoredBilbo: " + armoredBilbo);
        assertTrue(armoredBilbo.toString().contains("armor"));


        Command moveCommand = CommandFactory.createMoveCommand(armoredBilbo);
        moveCommand.execute();
        assertEquals(1, maze.getLivingAdventurers().size());

        Command wearSecondArmor = CommandFactory.createWearArmorCommand(armoredBilbo);
        wearSecondArmor.execute();
        assertEquals(1, maze.getLivingAdventurers().size());


        Character doublyArmoredBilbo = maze.getLivingAdventurers().getFirst();
        System.out.println("doublyArmoredBilbo: " + doublyArmoredBilbo);


        Room ogreRoom = maze.getRoom("Ogre Room");
        ogreRoom.enter(doublyArmoredBilbo);


        // Now fight the Ogre with two sets of Armor
        Command fightCommand = CommandFactory.createFightCommand(doublyArmoredBilbo, ogre);
        fightCommand.execute();
    }

}
