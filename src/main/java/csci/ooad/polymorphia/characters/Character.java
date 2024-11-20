package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.*;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.maze.Room;
import csci.ooad.polymorphia.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.DecimalFormat;
import java.util.Optional;

public class Character implements Comparable<Character> {
    private static Logger logger = LoggerFactory.getLogger(Character.class);
    private static DecimalFormat formatter = new DecimalFormat("0.0");
    protected String name;
    private Strategy strategy;
    private Double health;
    private CharacterType type;
    private Room currentLocation;


    public Character(String name, Double initialHealth, Strategy strategy, CharacterType type) {
        this.name = name;
        this.health = initialHealth;
        this.strategy = strategy;
        this.type = type;
    }


    public Strategy getStrategy() {
        return strategy;
    }

    public Room getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public int compareTo(Character otherCharacter) {
        return getHealth().compareTo(otherCharacter.getHealth());
    }

    public void enterRoom(Room room) {
        this.currentLocation = room;
    }

    @Override
    public String toString() {
        return getName() + "(health: " + formatter.format(getHealth()) + ")";
    }

    public void loseHealth(Double healthPoints) {
        if (health <= 0) {
            return;     // already dead, probably called for mandatory health loss for having a fight
        }

        health -= healthPoints;

        String lostHealthDescription = this.getName() + " just lost " + healthPoints + " points";
        EventBus.getInstance().postMessage(EventType.LostHealth, lostHealthDescription);

        if (health <= 0) {
            String eventDescription = this.getName() + " just died!";
            logger.info(eventDescription);
            EventBus.getInstance().postMessage(EventType.Death, eventDescription);
        }
    }

    public Double getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public Boolean isAlive() {
        return getHealth() > 0;
    }

    public void loseFightDamage(double fightDamage) {
        loseHealth(fightDamage);
    }

    public void loseMoveDamage(double moveDamage) {loseHealth(moveDamage);}

    protected boolean cannotMove() {
        return getCurrentLocation().getNeighbors().isEmpty();
    }

    public Boolean isAdventurer() {
        return type == CharacterType.Adventurer;
    }
    public Boolean isHuman(){
        return type == CharacterType.Human;
    }

    public Boolean isCreature() {
        return type == CharacterType.Creature;
    }
    public Boolean isDemon() {
        return type == CharacterType.Demon;
    }
    public Boolean isCoward() {
        return type == CharacterType.Coward;
    }
    public Boolean isKnight() {
        return type == CharacterType.Knight;
    }
    public Boolean isGlutton() {
        return type == CharacterType.Glutton;
    }

    public void doAction() {
        try {
            // If it's a human player handle the action through prompting
            if (this.type == CharacterType.Human) {
                Optional<HumanOption> selection = this.strategy.prompt(this);
                if (selection.isPresent()) {
                    HumanOption humanOption = selection.get();
                    if (humanOption.value() == 1) this.strategy.eat(this);
                    if(humanOption.value() == 2) this.strategy.fight(this);
                    if(humanOption.value() == 3) this.strategy.move(this);
                }
            } else {
                // If they're a fake robot then just keep going
                Command fight = strategy.fight(this);
                Command eat = strategy.eat(this);
                Command move = strategy.move(this);
                if (fight != null) {
                    fight.execute();
                } else if (eat != null) {
                    eat.execute();
                }else if (move != null) {
                    move.execute();
                }
            }
            // If it's a human player, handle the action through prompting
            if (this.type == CharacterType.Human) {
                Optional<HumanOption> selection = this.strategy.prompt(this);
                // TODO - need to make sure to only prompt/allow action if applicable (i.e. right now can eat when no food present)

                if (selection.isPresent()) {
                    HumanOption humanOption = selection.get();
                    Command command = null;

                    // Determine the command based on the selected option
                    if (humanOption.value() == 1) {
                        command = this.strategy.eat(this);
                    } else if (humanOption.value() == 2) {
                        command = this.strategy.fight(this);
                    } else if (humanOption.value() == 3) {
                        command = this.strategy.move(this);
                    } else if (humanOption.value() == 4) {
                        command = this.strategy.wearArmor(this);
                    }

                    // Execute the command if it is not null
                    if (command != null) {
                        command.execute();
                    } else {
                        System.out.println("No valid action could be performed for the selected option.");
                    }
                }
            } else {
                // For non-human characters, execute strategies directly
                Command fight = strategy.fight(this);
                Command eat = strategy.eat(this);
                Command move = strategy.move(this);

                if (fight != null) {
                    fight.execute();
                } else if (eat != null) {
                    eat.execute();
                } else if (move != null) {
                    move.execute();
                }
            }
        } catch (NoFoodException e) {
            throw new RuntimeException(e);
        }
    }

    public void gainHealth(double healthValue) {
        this.health += healthValue;
        logger.info("{} gained health: {}", getName(), formatter.format(getHealth()));
    }


    public CharacterType getType() {
        return type;
    }
}
