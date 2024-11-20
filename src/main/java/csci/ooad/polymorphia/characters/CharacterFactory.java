package csci.ooad.polymorphia.characters;


import csci.ooad.polymorphia.strategy.*;
import csci.ooad.polymorphia.characters.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public class CharacterFactory {
    //////////////////////////////////////////// new below
    static final Double DEFAULT_INITIAL_HEALTH = 5.0;
    static final Double CREATURE_INITIAL_HEALTH = 3.0;
    static final Double DEMON_INITIAL_HEALTH = 15.0;
    public static String[] ADVENTURER_NAMES = new String[]{"Frodo", "Arwen", "Nikhil", "Sierra", "Matt", "Fran"};
    public static String[] KNIGHT_NAMES = new String[]{"Sir Lancelot", "Lady Brienne", "King Arthur", "Sir Jamey", "Aragorn", "Isildur"};
    public static String[] COWARD_NAMES = new String[]{"Sir Robin", "Sir Scaredy Cat", "Lady Faints-a-lot", "Lady Runaway", "Sir Chicken", "Lady Hides-a-lot"};
    public static String[] GLUTTON_NAMES = new String[]{"Sir Eats-a-lot", "Sir Gobbles", "Lady Munches", "Lady Snacks", "Sir Nibbles", "Lady Noshes"};
    public static String[] CREATURE_NAMES = new String[]{"Dragon", "Ogre", "Orc", "Shelob", "Troll", "Evil Wizard"};
    public static String[] DEMON_NAMES = new String[]{"Satan", "Beelzebub", "Devil", "Incubus", "Lucifer", "Succubus"};

    public static Character createAdventurer(String name, Optional<Double> healthInput) {
        Double health = healthInput.orElse(DEFAULT_INITIAL_HEALTH);
        return new Character(name, health, new AdventurerStrategy(), CharacterType.Adventurer);
    }

    public static Character createKnight(String name, Optional<Double> healthInput) {
        Double health = healthInput.orElse(DEFAULT_INITIAL_HEALTH);
        return new Character(name, health, new KnightStrategy(), CharacterType.Knight);
    }

    public static Character createGlutton(String name) {
        return new Character(name, DEFAULT_INITIAL_HEALTH, new GluttonStrategy(), CharacterType.Glutton);
    }

    public static Character createCoward(String name, Optional<Double> healthInput) {
        Double health = healthInput.orElse(DEFAULT_INITIAL_HEALTH);
        return new Character(name, health, new CowardStrategy(), CharacterType.Coward);
    }

    public static Character createDemon(String name) {
        return new Character(name, DEMON_INITIAL_HEALTH, new DemonStrategy(), CharacterType.Demon);
    }

    public static Character createCreature(String name, Optional<Double> healthInput) {
        Double health = healthInput.orElse(CREATURE_INITIAL_HEALTH);
        return new Character(name, health, new CreatureStrategy(), CharacterType.Creature);
        //return new Character(name, character -> new NoOpCommand()); // "Do nothing" for non-Demon creatures
    }
    public static Character createHuman(String name) {
        Double health = DEFAULT_INITIAL_HEALTH;
        return new Character(name, health, new HumanStrategy(), CharacterType.Human);
    }

    // Create many of each character type
    public static List<Character> createNumberOfAdventurers(int numAdventurers) {
        return IntStream.range(0, numAdventurers)
                .mapToObj(i -> new Character(ADVENTURER_NAMES[i % ADVENTURER_NAMES.length], DEFAULT_INITIAL_HEALTH, new AdventurerStrategy(), CharacterType.Adventurer))
                .map(Character.class::cast)
                .toList();
    }

    public static List<Character> createNumberOfKnights(int numAdventurers) {
        return IntStream.range(0, numAdventurers)
                .mapToObj(i -> new Character(KNIGHT_NAMES[i % KNIGHT_NAMES.length], DEFAULT_INITIAL_HEALTH, new KnightStrategy(), CharacterType.Knight))
                .map(Character.class::cast)
                .toList();
    }

    public static List<Character> createNumberOfGluttons(int numAdventurers) {
        return IntStream.range(0, numAdventurers)
                .mapToObj(i -> new Character(GLUTTON_NAMES[i % GLUTTON_NAMES.length], DEFAULT_INITIAL_HEALTH, new GluttonStrategy(), CharacterType.Glutton))
                .map(Character.class::cast)
                .toList();
    }

    public static List<Character> createNumberOfCowards(int numAdventurers) {
        return IntStream.range(0, numAdventurers)
                .mapToObj(i -> new Character(COWARD_NAMES[i % COWARD_NAMES.length], DEFAULT_INITIAL_HEALTH, new CowardStrategy(), CharacterType.Coward))
                .map(Character.class::cast)
                .toList();
    }

    public static List<Character> createNumberOfCreatures(int numCharacters) {
        return IntStream.range(0, numCharacters)
                .mapToObj(i -> new Character(CREATURE_NAMES[i % CREATURE_NAMES.length], CREATURE_INITIAL_HEALTH, new CreatureStrategy(), CharacterType.Creature))
                .map(Character.class::cast)
                .toList();
    }

    public static List<Character> createNumberOfDemons(int numCharacters) {
        return IntStream.range(0, numCharacters)
                .mapToObj(i -> new Character(DEMON_NAMES[i % DEMON_NAMES.length], DEMON_INITIAL_HEALTH, new DemonStrategy(), CharacterType.Demon))
                .map(Character.class::cast)
                .toList();
    }
}
