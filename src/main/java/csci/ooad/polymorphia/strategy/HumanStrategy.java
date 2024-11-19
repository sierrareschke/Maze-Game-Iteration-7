package csci.ooad.polymorphia.strategy;
import csci.ooad.polymorphia.HumanOption;
import csci.ooad.polymorphia.characters.Character;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface HumanStrategy {
    Optional<HumanOption> prompt(Character character);
}
