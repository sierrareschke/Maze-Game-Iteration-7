package csci.ooad.polymorphia.stepdefs

import csci.ooad.polymorphia.artifacts.Food
import csci.ooad.polymorphia.maze.Maze
import csci.ooad.polymorphia.Polymorphia
import csci.ooad.polymorphia.maze.Room
import io.cucumber.java.Before
import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.When

class MazeStepDefs {
    Maze maze
    World world
    Maze.Builder mazeBuilder;

    MazeStepDefs(World world) {
        this.world = world
    }

    // Step 1: Initialize MazeBuilder before all @Given steps
    @Before(order = 0)
    void setupMazeBuilder() {
        mazeBuilder = Maze.getNewBuilder().create2x2Grid()
    }


    @Given("I have a maze with the following attributes:")
    public void iHaveAGameWithTheFollowingAttributes(Map<String, Integer> gameAttributes) {

        int numRooms = gameAttributes.getOrDefault("number of rooms", 0);
        int numAdventurers = gameAttributes.getOrDefault("number of adventurers", 0);
        int numKnights = gameAttributes.getOrDefault("number of knights", 0);
        int numCowards = gameAttributes.getOrDefault("number of cowards", 0);
        int numGluttons = gameAttributes.getOrDefault("number of gluttons", 0);
        int numCreatures = gameAttributes.getOrDefault("number of creatures", 0);
        int numDemons = gameAttributes.getOrDefault("number of demons", 0);
        int numFoodItems = gameAttributes.getOrDefault("number of food items", 0);

        mazeBuilder.createFullyConnectedRooms(numRooms)
                .createAndAddAdventurers(numAdventurers)
                .createAndAddKnights(numKnights)
                .createAndAddCowards(numCowards)
                .createAndAddGluttons(numGluttons)
                .createAndAddCreatures(numCreatures)
                .createAndAddDemons(numDemons)
                .createAndAddFoodItems(numFoodItems)
    }

    @Given("a room named {string} with {int} neighbors")
    public void createRoomWithNNeighbors(String roomName, int numNeighbors) {

        List<String> roomNames = new ArrayList<>();

        // Add the initial room name
        roomNames.add(roomName);

        // Add neighbors as "Room 1", "Room 2", etc.
        for (int i = 1; i <= numNeighbors; i++) {
            roomNames.add("Room " + i);
        }

        // Convert List<String> to String[] and call the builder method
        mazeBuilder.createFullyConnectedRooms(roomNames.toArray(new String[0]));
    }

    @When("all characters are in room {string}")
    void placeAllCharactersInRoom(String roomName) {
        println("Placing all characters in room: $roomName")

        Room targetRoom = maze.getRoom(roomName)

        // Retrieve all characters from the maze
        List<Character> allCharacters = maze.getLivingCharacters()

        // Move each character to the specified room
        allCharacters.each { character ->
            targetRoom.enter(character)
        }

        println("All characters have been placed in room: $roomName")
    }


    @Given(/^a ([A-Z][a-zA-Z]*) "([^"]+)"$/)
    void addCharacterWithName(String typeOfCharacter, String characterName) {
        println "Character Type: $typeOfCharacter"
        println "Character Name: $characterName"

        switch (typeOfCharacter) {
            case 'Coward':
                mazeBuilder.createAndAddCowards(new String[]{characterName});
                break;
            case 'Glutton':
                mazeBuilder.createAndAddGluttons(new String[]{characterName});
                break;
            case 'Adventurer':
                mazeBuilder.createAndAddAdventurers(new String[]{characterName});
                break;
            case 'Knight':
                mazeBuilder.createAndAddKnights(new String[]{characterName});
                break;
            case 'Creature':
                mazeBuilder.createAndAddCreatures(new String[]{characterName});
                break;
            case 'Demon':
                mazeBuilder.createAndAddDemons(new String[]{characterName});
                break;
            default:
                break;
        }
    }

    @And(/^there is a Food "([^"]+)" added to Room "([^"]+)"$/)
    void addFoodItem(String foodName, String roomName) {
        mazeBuilder.addToRoom(roomName, new Food(foodName))
    }

    // Step 2: Finalize maze with .build() after @Given but before @When
    @Given("the maze is successfully created")
    void finalizeMaze() {
        maze = mazeBuilder.build();
        world.polymorphia = new Polymorphia("Polymorphia Maze", maze);
    }


}
