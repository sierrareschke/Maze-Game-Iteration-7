package csci.ooad.polymorphia.maze;

import csci.ooad.polymorphia.Die;
import csci.ooad.polymorphia.NoArmorException;
import csci.ooad.polymorphia.artifacts.Armor;
import csci.ooad.polymorphia.artifacts.Food;
import csci.ooad.polymorphia.NoFoodException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import csci.ooad.polymorphia.characters.Character;

import static java.util.Collections.*;


public class Room {
    private String name;
    private List<Room> neighbors = new ArrayList<>();
    private List<Character> characters = new ArrayList<>();
    private List<Food> foodItems = new ArrayList<>();
    private List<Armor> armor = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Food> getFoodItems() {
        return foodItems;
    }

    public List<Armor> getArmor() {
        return armor;
    }

    public List<Character> getLivingAdventurers() {
        return characters.stream()
                .filter(Character::isAdventurer)
                .filter(Character::isAlive)
                .map(Character.class::cast)
                .sorted()
                .toList();
    }

    public List<Character> getLivingCreatures() {
        return characters.stream()
                .filter(character -> character.isDemon() || character.isCreature()) // Check for demons or creatures
                .filter(Character::isAlive) // Check if alive
                .map(Character.class::cast) // Cast if necessary
                .sorted() // Assuming Character implements Comparable
                .toList(); // Collect as a list
    }


    public List<String> getContents() {
        List<String> contents = new ArrayList<>(getLivingCharacters().stream()
                .map(Object::toString)
                .toList());
        contents.addAll(this.foodItems.stream()
                .map(Object::toString)
                .toList());
        contents.addAll(this.armor.stream()
                .map(Object::toString)
                .toList());
        return unmodifiableList(contents);
    }

    private void addNeighbor(Room neighbor) {
        // Make sure we are never a neighbor of ourselves
        assert this != neighbor;
        this.neighbors.add(neighbor);
    }

    public void connect(Room neighbor) {
        this.addNeighbor(neighbor);
        neighbor.addNeighbor(this);
    }

    @Override
    public String toString() {
        String representation = "\t" + name + ":\n\t\t";
        representation += String.join("\n\t\t", getContents());
        return representation;
    }

    public void add(Character character) {
        characters.add(character);
        character.enterRoom(this);
    }

    public Boolean hasLivingCreatures() {
        return characters.stream()
                .anyMatch(character -> character.isAlive() &&
                        (character.isDemon() || character.isCreature()));
    }

    public Boolean hasLivingAdventurers() {
        return characters.stream()
                .anyMatch(character -> character.isAlive() &&
                        (character.isHuman() || character.isAdventurer() || character.isCoward() || character.isKnight() || character.isGlutton()));
    }

    public Boolean hasLivingCoward() {
        return characters.stream()
                .filter(Character::isCoward)
                .filter(Character::isAlive)
                .anyMatch(Character::isAlive);
    }

    public void remove(Character character) {
        characters.remove(character);
    }

    public Room getRandomNeighbor() {
        if (neighbors.isEmpty()) {
            return null;
        }
        return neighbors.get(Die.randomLessThan(neighbors.size()));
    }

    public void enter(Character character) {
        Room currentRoom = character.getCurrentLocation();
        currentRoom.remove(character);
        this.add(character);
    }

    public List<Character> getLivingCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .toList();
    }

    public void add(Food foodItem) {
        foodItems.add(foodItem);
    }

    public void add(Armor newArmor){
        armor.add(newArmor);
    }

    public Character getHealthiestAdventurer() {
        return getLivingAdventurers().stream().max(Comparator.naturalOrder()).orElse(null);
    }

    public Character getHealthiestCreature() {
        return getLivingCreatures().stream().max(Comparator.naturalOrder()).orElse(null);
    }

    public boolean hasFood() {
        return !foodItems.isEmpty();
    }

    public boolean hasArmor() {
        return !armor.isEmpty();
    }

    public Food removeFoodItem() {
        if (foodItems.isEmpty()) {
            throw new NoFoodException("No food in room");
        }
        return foodItems.removeFirst();
    }

    public Armor removeArmor() {
        if(armor.isEmpty()) {
            throw new NoArmorException("No armor in room");
        }
        return armor.removeFirst();
    }

    public Boolean hasDemon() {
        return characters.stream().filter(Character::isAlive).anyMatch(Character::isDemon);
    }

    public int numberOfNeighbors() {
        return neighbors.size();
    }

    public boolean hasNeighbor(Room neighbor) {
        return neighbors.contains(neighbor);
    }

    public boolean hasCoward() {
        return characters.stream().filter(Character::isAlive).anyMatch(Character::isCoward);
    }

    public List<Room> getNeighbors() {
        return List.copyOf(neighbors);
    }

    public Character getDemon() {
        return characters.stream().filter(Character::isAlive).filter(Character::isDemon).findFirst().get();
    }
}
