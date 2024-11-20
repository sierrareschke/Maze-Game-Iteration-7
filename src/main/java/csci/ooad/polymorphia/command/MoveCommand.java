package csci.ooad.polymorphia.command;

import csci.ooad.polymorphia.EventType;
import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.maze.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static csci.ooad.polymorphia.EventBus.post;

public class MoveCommand implements Command {
    static final Double CHARACTER_HEALTH_LOST_IN_MOVING_ROOMS = 0.25;
    private static final Logger logger = LoggerFactory.getLogger(MoveCommand.class);
    Character character;
    Room room;

    public MoveCommand(Character character) {
        this.character = character;
        this.room = character.getCurrentLocation();
    }

    @Override
    public void execute() {
        Room nextLocation = character.getCurrentLocation().getRandomNeighbor();
        if (nextLocation != null) {
            String message = character.getName() + " moved from " + character.getCurrentLocation().getName() + " to " + nextLocation.getName();
            logger.info(message);
            post(EventType.Moved, message);
            nextLocation.enter(character);
            character.loseMoveDamage(CHARACTER_HEALTH_LOST_IN_MOVING_ROOMS);
        } else {
            logger.warn("{} has no neighbors!", character.getCurrentLocation().getName());
        }
    }
}
