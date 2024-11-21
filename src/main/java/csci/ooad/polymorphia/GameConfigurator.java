package csci.ooad.polymorphia;

import csci.ooad.polymorphia.maze.Maze;
import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class GameConfigurator {
    private static final Logger logger = LoggerFactory.getLogger(GameConfigurator.class);
    static int SECONDS_TO_PAUSE_BETWEEN_TURNS = 1;
    private final Maze.Builder mazeBuilder;


    GameConfigurator(CommandLine cmdLine) throws ParseException {
        mazeBuilder = Maze.getNewBuilder();
        buildMazeFromArguments(cmdLine);
    }


    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmdLine = parser.parse(getOptions(), args);
            GameConfigurator gameConfig = new GameConfigurator(cmdLine);
            gameConfig.createAndStartGame();
        } catch (ParseException e) {
            logger.error("Error parsing command-line arguments: {}", e.getMessage());
            System.exit(1); // error
        }
        System.exit(0);
    }

    static Options getOptions() {
        Option numRooms = Option.builder("r")
                .longOpt("numberOfRooms")
                .argName("numRooms")
                .hasArg()
                .numberOfArgs(1)
                .type(Number.class)
                .desc("the number of rooms in the maze")
                .build();

        Option numAdventurers = Option.builder("a")
                .longOpt("numberOfAdventurers")
                .argName("numAdventurers")
                .hasArg()
                .numberOfArgs(1)
                .type(Number.class)
                .desc("the number of adventurers to place in the maze")
                .build();

        Option numCreatures = Option.builder("c")
                .longOpt("numberOfCreatures")
                .argName("numCreatures")
                .hasArg()
                .type(Number.class)
                .numberOfArgs(1)
                .desc("the number of creatures to place in the maze")
                .build();

        Option numDemons = Option.builder("d")
                .longOpt("numberOfDemons")
                .argName("numDemons")
                .hasArg()
                .type(Number.class)
                .numberOfArgs(1)
                .desc("the number of demons to place in the maze")
                .build();

        Option numFoodItems = Option.builder("f")
                .longOpt("numberOfFoodItems")
                .argName("numFoodItems")
                .hasArg()
                .numberOfArgs(1)
                .type(Number.class)
                .desc("the number of food items to place in the maze")
                .build();

        Option armoredSuits = Option.builder("m")
                .longOpt("numberOfArmor")
                .argName("numArmor")
                .hasArg()
                .numberOfArgs(1)
                .type(Number.class)
                .desc("a comma-separated list of armored suit names")
                .build();

        Option humanPlayer = Option.builder("h")
                .longOpt("humanPlayer")
                .argName("humanPlayerName")
                .hasArg()
                .numberOfArgs(1)
                .type(String.class)
                .desc("the human player's name")
                .build();

        Options options = new Options();
        options.addOption(numAdventurers);
        options.addOption(numCreatures);
        options.addOption(numDemons);
        options.addOption(numFoodItems);
        options.addOption(humanPlayer);
        options.addOption(armoredSuits);
        options.addOption(numRooms);


        return options;
    }

    // TODO - default values for others?
    void buildMazeFromArguments(CommandLine cmdLine) throws ParseException {
        int numRooms = 6; // Default number of rooms
        if (cmdLine.hasOption("r")) {
            numRooms = ((Number) cmdLine.getParsedOptionValue("r")).intValue();
            mazeBuilder.createFullyConnectedRooms(numRooms);
        }

        int numAdventurers = 5; // Default number of adventurers
        if (cmdLine.hasOption("a")) {
            numAdventurers = ((Number) cmdLine.getParsedOptionValue("a")).intValue();
            mazeBuilder.createAndAddAdventurers(numAdventurers);
        }

        int numCreatures = 4; // Default number of creatures
        if (cmdLine.hasOption("c")) {
            numCreatures = ((Number) cmdLine.getParsedOptionValue("c")).intValue();
            mazeBuilder.createAndAddCreatures(numCreatures);
        }

        int numDemons = 1; // Default number of demons
        if (cmdLine.hasOption("d")) {
            numDemons = ((Number) cmdLine.getParsedOptionValue("d")).intValue();
            mazeBuilder.createAndAddDemons(numDemons);
        }

        int numFoodItems = 6; // default number of food items
        if (cmdLine.hasOption("f")) {
            numFoodItems = ((Number) cmdLine.getParsedOptionValue("f")).intValue();
            mazeBuilder.createAndAddFoodItems(numFoodItems);
        }

        int numKnights = 2; // default number of knights
        if (cmdLine.hasOption("k")) {
            numKnights = ((Number) cmdLine.getParsedOptionValue("k")).intValue();
            mazeBuilder.createAndAddKnights(numKnights);
        }

        int numArmor = 2;
        if (cmdLine.hasOption("m")) {
            numArmor = ((Number) cmdLine.getParsedOptionValue("m")).intValue();
            mazeBuilder.createAndAddArmor(numArmor);
        }

        String humanPlayerName = "HumanPlayer";
        if (cmdLine.hasOption("h")) {
            humanPlayerName = cmdLine.getOptionValue("h");
            mazeBuilder.createAndAddHuman(humanPlayerName);
        }
    }

    public void createAndStartGame() {
        Polymorphia polymorphia = new Polymorphia(mazeBuilder.build());
        polymorphia.play();
    }
}
