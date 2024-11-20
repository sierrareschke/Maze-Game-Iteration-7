package csci.ooad.polymorphia;

public record HumanOption(String name, int value) {
    // Globally accessible, fixed options
    public static final HumanOption EAT = new HumanOption("Eat", 1);
    public static final HumanOption FIGHT = new HumanOption("Fight", 2);
    public static final HumanOption MOVE = new HumanOption("Move", 3);
    public static final HumanOption PUT_ON_ARMOR = new HumanOption("Put on armor", 4);
    public static final HumanOption DO_NOTHING = new HumanOption("Do Nothing", 5);
}