package csci.ooad.polymorphia;

import csci.ooad.polymorphia.maze.Maze;
import org.apache.commons.cli.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameConfiguratorTest {

    // Test 1: Validate `getOptions` returns non-null Options
    @Test
    public void testGetOptions() {
        Options options = GameConfigurator.getOptions();
        assertNotNull(options, "Options should not be null");
        assertNotNull(options.getOption("r"), "Option for 'numberOfRooms' should exist");
    }

    // Test 2: Validate `buildMazeFromArguments` basic behavior
    @Test
    public void testBuildMazeFromArguments() throws ParseException {
        // Mock the Maze.Builder class
        Maze.Builder mazeBuilderMock = mock(Maze.Builder.class);

        // Create a mock command-line
        String[] args = {"-r", "5", "-a", "3", "-c", "4"};
        CommandLine cmdLine = new DefaultParser().parse(GameConfigurator.getOptions(), args);

        // Call the GameConfigurator and pass the mock command-line
        GameConfigurator configurator = new GameConfigurator(cmdLine);
        configurator.buildMazeFromArguments(cmdLine);

        // Verify that the Maze.Builder methods are called
        verify(mazeBuilderMock).createFullyConnectedRooms(5);
        verify(mazeBuilderMock).createAndAddAdventurers(3);
        verify(mazeBuilderMock).createAndAddCreatures(4);
    }

    // Test 3: Simple validation for `main` with valid arguments
    @Disabled
    public void testMainValidArgs() {
        String[] args = {"-r", "10", "-a", "2"};
        assertDoesNotThrow(() -> GameConfigurator.main(args));
    }

    // Test 4: Validate `main` fails gracefully with invalid arguments
    @Disabled
    public void testMainInvalidArgs() {
        String[] args = {"-x", "invalid"};
        Exception exception = assertThrows(Exception.class, () -> GameConfigurator.main(args));
        assertTrue(exception.getMessage().contains("Unrecognized option"));
    }

    // Test 5: Verify `createAndStartGame` triggers expected methods
    @Disabled
    public void testCreateAndStartGame() {
        // Mock the Maze.Builder and Polymorphia classes
        Maze.Builder mazeBuilderMock = mock(Maze.Builder.class);
        Maze mazeMock = mock(Maze.class);
        when(mazeBuilderMock.build()).thenReturn(mazeMock);

        Polymorphia polymorphiaMock = mock(Polymorphia.class);

        // Call the method
        GameConfigurator configurator = new GameConfigurator(mazeBuilderMock);
        configurator.createAndStartGame();

        // Verify interactions
        verify(mazeBuilderMock).build();
        verify(polymorphiaMock).play();
    }
}