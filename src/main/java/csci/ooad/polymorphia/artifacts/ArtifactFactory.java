package csci.ooad.polymorphia.artifacts;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ArtifactFactory {
    public static String[] FOOD_NAMES = new String[]{"cupcake", "apple", "banana", "steak", "salad", "fries", "burger", "pizza", "eggs",
            "bacon", "muffin", "donut", "chicken", "pasta", "rice", "sushi", "taco", "burrito", "nachos", "chips"};
    public static String[] ARMOR_NAMES = new String[]{"Netherite Chestplate", "Endersteel Leggings", "Obsidian Shield", "Blazeborn Helm",
            "Frostguard Boots", "Ironwood Armor", "Shadowsteel Plate", "Crystalwing Cloak", "Voidforged Mail", "Glowstone Mantle"};


    private static final Random random = new Random();

    public Food createFood(String name) {
        return new Food(name);
    }

    public Armor createArmor(String name) { return new Armor(name); }

    public List<Food> createNumFoods(Integer numFood) {
        return IntStream.range(0, numFood)
                .mapToObj(i -> new Food(FOOD_NAMES[i % FOOD_NAMES.length], random.nextDouble(1,2)))
                .map(Food.class::cast)
                .toList();
    }

    public List<Armor> createNumArmor(Integer numArmor) {
        return IntStream.range(0,numArmor)
                .mapToObj(i -> new Armor(ARMOR_NAMES[i % ARMOR_NAMES.length]))
                .map(Armor.class::cast)
                .toList();
    }
}


