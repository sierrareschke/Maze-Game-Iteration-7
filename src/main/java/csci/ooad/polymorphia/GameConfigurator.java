package csci.ooad.polymorphia;

import csci.ooad.polymorphia.maze.Maze;
import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;
import java.util.Arrays;

public class GameConfigurator {
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
            System.err.println("Error parsing command-line arguments: " + e.getMessage()); // TODO delete sys out
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
                .longOpt("armoredSuits")
                .argName("armorList")
                .hasArg()
                .numberOfArgs(1)
                .type(String.class)
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
            //System.out.println("numRooms: " + numRooms);
            mazeBuilder.createFullyConnectedRooms(numRooms);
        }

        if (cmdLine.hasOption("a")) {
            int numAdventurers = ((Number) cmdLine.getParsedOptionValue("a")).intValue();
            //System.out.println("numAdventurers: " + numAdventurers);
            mazeBuilder.createAndAddAdventurers(numAdventurers);
        }

        if (cmdLine.hasOption("c")) {
            int numCreatures = ((Number) cmdLine.getParsedOptionValue("c")).intValue();
            //System.out.println("numCreatures: " + numCreatures);
            mazeBuilder.createAndAddCreatures(numCreatures);
        }

        if (cmdLine.hasOption("d")) {
            int numDemons = ((Number) cmdLine.getParsedOptionValue("d")).intValue();
            //System.out.println("numDemons: " + numDemons);
            mazeBuilder.createAndAddDemons(numDemons);
        }

        int numFoodItems = 6;
        if (cmdLine.hasOption("f")) {
            numFoodItems = ((Number) cmdLine.getParsedOptionValue("f")).intValue();
            //System.out.println("numFoodItems: " + numFoodItems);
            mazeBuilder.createAndAddFoodItems(numFoodItems);
        }

//        int numKnights = 2;
        if (cmdLine.hasOption("k")) {
            int numKnights = ((Number) cmdLine.getParsedOptionValue("m")).intValue();
            //System.out.println("numKnights: " + numKnights);
            mazeBuilder.createAndAddKnights(numKnights);
        }

        if (cmdLine.hasOption("m")) {
            String armorList = cmdLine.getOptionValue("m");
            String[] armorArray = armorList.split(","); // Split by commas
            // TODO delete below and all sys out
//            System.out.println("Armor names: ");
//            for (String armor : armorArray) {
//                System.out.println(armor.trim());
//            }
            mazeBuilder.createAndAddArmor(String.valueOf(Arrays.asList(armorArray)));
        }

        if (cmdLine.hasOption("h")) {
            String humanPlayerName = cmdLine.getOptionValue("h");
            //System.out.println("human name: " + humanPlayerName);
            mazeBuilder.createAndAddHuman(humanPlayerName);
        }
    }

    public void createAndStartGame() {
        Polymorphia polymorphia = new Polymorphia(mazeBuilder.build());
        polymorphia.play();
    }
}
