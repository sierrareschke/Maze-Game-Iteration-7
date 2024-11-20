package csci.ooad.polymorphia.maze;

import csci.ooad.polymorphia.artifacts.Armor;
import csci.ooad.polymorphia.artifacts.ArtifactFactory;
import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.NoSuchRoomException;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.characters.CharacterFactory;

import java.util.*;


public class Maze {
    private final Random rand = new Random();

    private List<Room> rooms = new ArrayList<>();

    public Maze() {
    }

    public static Builder getNewBuilder() {
        return new Builder();
    }

    public static Builder getNewBuilder(CharacterFactory characterFactory, ArtifactFactory artifactFactory) {
        return new Builder(characterFactory, artifactFactory);
    }

    public int size() {
        return rooms.size();
    }

    @Override
    public String toString() {
        return String.join("\n\n", rooms.stream().map(Object::toString).toList());
    }

    public Boolean hasLivingCreatures() {
        return rooms.stream().anyMatch(Room::hasLivingCreatures);
    }

    public Boolean hasLivingAdventurers() {
        return rooms.stream().anyMatch(Room::hasLivingAdventurers);
    }

    private Room getRandomRoom() {
        return rooms.get(rand.nextInt(rooms.size()));
    }

    public List<Character> getLivingAdventurers() {
        List<Character> adventurers = new ArrayList<>();
        for (Room room : rooms) {
            adventurers.addAll(room.getLivingAdventurers());
        }
        return Collections.unmodifiableList(adventurers);
    }

    public List<Character> getLivingCreatures() {
        List<Character> creatures = new ArrayList<>();
        for (Room room : rooms) {
            creatures.addAll(room.getLivingCreatures());
        }
        return Collections.unmodifiableList(creatures);
    }

    public List<Character> getLivingCharacters() {
        List<Character> characters = new ArrayList<>();
        for (Room room : rooms) {
            characters.addAll(room.getLivingCharacters());
        }
        return Collections.unmodifiableList(characters);
    }

    public boolean hasDemon() {
        return rooms.stream().anyMatch(Room::hasDemon);
    }

    public boolean hasCoward() {
        return rooms.stream().anyMatch(Room::hasCoward);
    }

    public Room getRoom(String roomName) throws NoSuchRoomException {
        Optional<Room> namedRoom = rooms.stream().filter(r -> r.getName().equals(roomName)).findFirst();
        if (namedRoom.isEmpty()) {
            throw new NoSuchRoomException(roomName);
        }
        return namedRoom.get();
    }

    private void addRoom(Room aRoom) {
        rooms.add(aRoom);
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    public static class Builder {
        private final static String[] NAMES = new String[]{
                "Rivendell", "Mordor", "BagEnd", "Swamp", "Crystal Palace", "Pool of Lava",
                "Stalactite Cave", "Goblin's Fountain", "Dragon's Den", "Troll Bridge",
                "Dungeon", "Pit of Despair", "Sanctuary",
                "Den of Souls", "Map Room", "Fangorn Forest", "Room of Horrors",
                "Misty Mountains", "Shire", "Hobbiton",
                "Gondor", "Chamber of Secrets", "Room of Doom", "Room of Gloom",
                "Titan's Lair", "Room of Shadows", "Room of Echoes", "Room of Whispers"
        };


        private final CharacterFactory characterFactory;
        final private ArtifactFactory artifactFactory;
        private final Maze maze = new Maze();
        private final Map<String, Room> roomMap = new HashMap<>();
        private Boolean distributeSequentially = false;
        private int currentRoomIndex = -1;  // This is incremented before any use

        Builder() {
            this(new CharacterFactory(), new ArtifactFactory());
        }
        private Builder(CharacterFactory characterFactory, ArtifactFactory artifactFactory) {
            this.characterFactory = characterFactory;
            this.artifactFactory = artifactFactory;
        }

        private static String[] createRoomNames(Integer numRooms) {
            // Room names must be unique, as the maze drawer uses them as keys to connect rooms
            String[] roomNames = new String[numRooms];
            int roomNameIndex = (new Random()).nextInt(NAMES.length);
            for (int i = 0; i < numRooms; i++) {
                roomNames[i] = NAMES[(roomNameIndex + i) % NAMES.length];
            }
            return roomNames;
        }

        private Room nextRoom() {
            if (distributeSequentially) {
                currentRoomIndex = (currentRoomIndex + 1) % maze.getRooms().size();
                return maze.getRooms().get(currentRoomIndex);
            }
            return maze.getRandomRoom();
        }

        public Builder createRoom(String name) {
            Room initialRoom = new Room(name);
            maze.addRoom(initialRoom);
            return this;
        }

        private Builder createGridOfRooms(int rows, int columns, String[][] roomNames) {
            Room[][] roomGrid = new Room[rows][columns];
            maze.rooms = new ArrayList<>();
            // Notice -- don't use i and j. Use row and column -- they are better
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Room newRoom = new Room(roomNames[row][column]);
                    roomGrid[row][column] = newRoom;
                    maze.rooms.add(newRoom);
                }
            }

            // Now connect the rooms
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    Room currentRoom = roomGrid[row][column];
                    if (row > 0) {
                        currentRoom.connect(roomGrid[row - 1][column]);
                    }
                    if (column > 0) {
                        currentRoom.connect(roomGrid[row][column - 1]);
                    }
                }
            }
            return this;
        }

        public Builder create2x2Grid() {
            String[][] roomNames = new String[][]{{"Northwest", "Northeast"}, {"Southwest", "Southeast"}};
            return createGridOfRooms(2, 2, roomNames);
        }

        public Builder create3x3Grid() {
            String[][] roomNames = new String[][]{{"Northwest", "North", "Northeast"}, {"West", "Center", "East"}, {"Southwest", "South", "Southeast"}};
            return createGridOfRooms(3, 3, roomNames);
        }

        public Builder createFullyConnectedRooms(Integer numRooms) {
            String[] roomNames = createRoomNames(numRooms);
            return createFullyConnectedRooms(roomNames);
        }

        public Builder createFullyConnectedRooms(String... roomNames) {
            maze.rooms = new ArrayList<>();
            for (String roomName : roomNames) {
                Room currentRoom = new Room(roomName);
                roomMap.put(roomName, currentRoom);
                for (Room otherRoom : maze.rooms) {
                    currentRoom.connect(otherRoom);
                }
                maze.rooms.add(currentRoom);
            }
            return this;
        }

        public Builder createConnectedRooms(Integer minConnections, Integer numRooms) {
            return createConnectedRooms(minConnections, createRoomNames(numRooms));
        }

        public Builder createConnectedRooms(Integer minConnections, String... roomNames) {
            maze.rooms = new ArrayList<>();
            for (String roomName : roomNames) {
                Room currentRoom = new Room(roomName);
                roomMap.put(roomName, currentRoom);
                maze.rooms.add(currentRoom);
            }

            Boolean oldDistributionSetting = this.distributeSequentially;
            distributeSequentially();
            int realMinimumConnections = Math.min(minConnections, Math.max(maze.rooms.size() - 1, 1));
            for (Room room : maze.rooms) {
                while (room.numberOfNeighbors() < realMinimumConnections) {
                    Room neighbor = nextRoom();
                    if (!room.equals(neighbor) && !room.hasNeighbor(neighbor)) {
                        room.connect(neighbor);
                    }
                }
            }
            this.distributeSequentially = oldDistributionSetting;
            return this;
        }

        public Builder createAndAddAdventurers(String... adventurerNames) {
            for (String adventurerName : adventurerNames) {
                nextRoom().add(characterFactory.createAdventurer(adventurerName, Optional.empty()));
            }
            return this;
        }

        private Builder addAdventurers(List<Character> adventurers) {
            for (Character adventurer : adventurers) {
                nextRoom().add(adventurer);
            }
            return this;
        }
        public Builder createAndAddAdventurers(Integer numAdventurers) {
            return addAdventurers(characterFactory.createNumberOfAdventurers(numAdventurers));
        }

        public Builder createAndAddKnights(Integer numKnights) {
            addAdventurers(characterFactory.createNumberOfKnights(numKnights));
            return this;
        }

        public Builder createAndAddHuman(String name) {
            nextRoom().add(characterFactory.createHuman(name));
            return this;
        }

        public Builder createAndAddGluttons(Integer numGluttons) {
            addAdventurers(characterFactory.createNumberOfGluttons(numGluttons));
            return this;
        }

        public Builder createAndAddCowards(Integer numCowards) {
            addAdventurers(characterFactory.createNumberOfCowards(numCowards));
            return this;
        }

        public Builder addAdventurers(Character... adventurers) {
            for (Character adventurer : adventurers) {
                nextRoom().add(adventurer);
            }
            return this;
        }

        private Builder addCreatures(List<Character> creatures) {
            for (Character creature : creatures) {
                nextRoom().add(creature);
            }
            return this;
        }

        public Builder createAndAddCreatures(Integer numCreatures) {
            return addCreatures(characterFactory.createNumberOfCreatures(numCreatures));
        }


        public Builder createAndAddCreatures(String... names) {
            for (String name : names) {
                nextRoom().add(characterFactory.createCreature(name, Optional.empty()));
            }
            return this;
        }

        public Builder createAndAddDemons(Integer numDemons) {
            addCreatures(characterFactory.createNumberOfDemons(numDemons));
            return this;
        }

        public Builder createAndAddDemons(String... names) {
            for (String name : names) {
                nextRoom().add(characterFactory.createDemon(name));
            }
            return this;
        }

        public Maze build() {
            return maze;
        }

        public Builder createAndAddFoodItems(String... foodNames) {
            for (String foodName : foodNames) {
                nextRoom().add(artifactFactory.createFood(foodName));
            }
            return this;
        }

        public Builder createAndAddFoodItems(Integer numItems) {
            List<Food> foodItems = artifactFactory.createNumFoods(numItems);
            for (Food food : foodItems) {
                nextRoom().add(food);
            }
            return this;
        }

        public Builder createAndAddArmor(String... armorNames) {
            for(String armorName : armorNames) {
                nextRoom().add(artifactFactory.createArmor(armorName));
            }
            return this;
        }

        public Builder createAndAddArmor(Integer numArmor){
            List<Armor> armor = artifactFactory.createNumArmor(numArmor);
            for(Armor armorItem : armor) {
                nextRoom().add(armorItem);
            }
            return this;
        }

        public Builder addCreatures(Character... creatures) {
            for (Character creature : creatures) {
                nextRoom().add(creature);
            }
            return this;
        }

        public Builder addToRoom(String roomName, Character character) {
            roomMap.get(roomName).add(character);
            return this;
        }

        public Builder addToRoom(String roomName, Food foodItem) {
            roomMap.get(roomName).add(foodItem);
            return this;
        }

        public Builder addToRoom(String roomName, Armor armorItem) {
            roomMap.get(roomName).add(armorItem);
            return this;
        }

        public Builder createAndAddKnights(String... names) {
            for (String name : names) {
                nextRoom().add(characterFactory.createKnight(name, Optional.empty()));
            }
            return this;
        }

        public Builder createAndAddGluttons(String... names) {
            for (String name : names) {
                nextRoom().add(characterFactory.createGlutton(name));
            }
            return this;
        }

        public Builder createAndAddCowards(String... names) {
            for (String name : names) {
                nextRoom().add(characterFactory.createCoward(name, Optional.empty()));
            }
            return this;
        }

        public Builder distributeSequentially() {
            distributeSequentially = true;
            return this;
        }

        public Builder distributeRandomly() {
            distributeSequentially = false;
            return this;
        }

    }
}
