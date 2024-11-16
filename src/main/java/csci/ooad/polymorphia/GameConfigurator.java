package csci.ooad.polymorphia;

public class GameConfigurator {
    static int SECONDS_TO_PAUSE_BETWEEN_TURNS = 1;


    private final Maze.Builder mazeBuilder;


    GameConfigurator(CommandLine cmdLine) {
        mazeBuilder = Maze.getNewBuilder();
        buildMazeFromArguments(cmdLine);
    }


    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmdLine = parser.parse(getOptions(), args);
        GameConfigurator gameConfig = new GameConfigurator(cmdLine);
        gameConfig.createAndStartGame();
        System.exit(0);
    }

    void buildMazeFromArguments(CommandLine cmdLine) throws ParseException {
        int numRooms = 6;
        if (cmdLine.hasOption("r")) {
            numRooms = ((Number) cmdLine.getParsedOptionValue("r")).intValue();
            mazeBuilder.createFullyConnectedRooms(numRooms);
        }
    }

    public void createAndStartGame() {
        Polymorphia polymorphia = new Polymorphia(mazeBuilder.build());
        polymorphia.play();
    }
}
