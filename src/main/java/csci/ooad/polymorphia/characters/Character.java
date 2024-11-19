package csci.ooad.polymorphia.characters;

import csci.ooad.polymorphia.*;
import csci.ooad.polymorphia.command.Command;
import csci.ooad.polymorphia.maze.Room;
import csci.ooad.polymorphia.strategy.EatStrategy;
import csci.ooad.polymorphia.strategy.FightStrategy;
import csci.ooad.polymorphia.strategy.HumanPromptStrategy;
import csci.ooad.polymorphia.strategy.MoveStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.Optional;

import static csci.ooad.polymorphia.EventBus.post;


public class Character implements Comparable<Character> {
    static Double DEFAULT_INITIAL_HEALTH = 5.0;
    static Double HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME = 0.5;
    static Double HEALTH_LOST_IN_MOVING_ROOMS = 0.25;
    private static Logger logger = LoggerFactory.getLogger(Character.class);
    private static DecimalFormat formatter = new DecimalFormat("0.0");
    protected String name;
    private HumanPromptStrategy humanPromptStrategy;
    private FightStrategy fightStrategy;
    private EatStrategy eatStrategy;
    private MoveStrategy moveStrategy;
    private Double health;
    private CharacterType type;
    private Room currentLocation;


    public Character(String name, Double initialHealth, FightStrategy fightStrategy, EatStrategy eatStrategy, MoveStrategy moveStrategy, CharacterType type) {
        this.name = name;
        this.health = initialHealth;
        this.fightStrategy = fightStrategy;
        this.eatStrategy = eatStrategy;
        this.moveStrategy = moveStrategy;
        this.type = type;
    }

    public Character(String name, Double initialHealth, FightStrategy fightStrategy, EatStrategy eatStrategy, MoveStrategy moveStrategy, HumanPromptStrategy humanPromptStrategy, CharacterType type) {
        this.name = name;
        this.health = initialHealth;
        this.fightStrategy = fightStrategy;
        this.eatStrategy = eatStrategy;
        this.moveStrategy = moveStrategy;
        this.type = type;
        this.humanPromptStrategy = humanPromptStrategy;
    }

    // TODO - Make sure this is the correct way to incorporate the decorator pattern
    public Character(Character character) {
        this.fightStrategy = character.getFightStrategy();
        this.eatStrategy = character.getEatStrategy();
        this.moveStrategy = character.getMoveStrategy();
        this.health = character.getHealth();
        this.name = character.getName();
        this.currentLocation = character.getCurrentLocation();
        this.type = character.type;
    }

    public EatStrategy getEatStrategy() {
        return eatStrategy;
    }

    public FightStrategy getFightStrategy() {
        return fightStrategy;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
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

    protected boolean cannotMove() {
        return getCurrentLocation().getNeighbors().isEmpty();
    }

    public Boolean isAdventurer() {
        return type == CharacterType.Adventurer;
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
                Optional<HumanOption> selection = this.humanPromptStrategy.prompt(this);
                if (selection.isPresent()) {
                    HumanOption humanOption = selection.get();
                    if (humanOption.value() == 1) this.eatStrategy.eat(this);
                    if(humanOption.value() == 2) this.fightStrategy.fight(this);
                    if(humanOption.value() == 3) this.moveStrategy.move(this);
                }
            } else {
                // If they're a fake robot then just keep going
                Command fight = fightStrategy.fight(this);
                Command eat = eatStrategy.eat(this);
                Command move = moveStrategy.move(this);
                if (fight != null) {
                    fight.execute();
                } else if (eat != null) {
                    eat.execute();
                }else if (move != null) {
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


}
