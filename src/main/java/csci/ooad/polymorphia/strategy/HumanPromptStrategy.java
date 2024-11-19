package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.HumanOption;
import csci.ooad.polymorphia.characters.Character;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HumanPromptStrategy implements HumanStrategy{
    @Override
    public Optional<HumanOption> prompt(Character character) {
        System.out.print("You are in room " + character.getCurrentLocation() + "\n\n");

        List<HumanOption> options = List.of(HumanOption.EAT, HumanOption.FIGHT, HumanOption.MOVE, HumanOption.DO_NOTHING);
        for (HumanOption option : options) {
            System.out.println(option.value() + ": " + option.name());
        }

        while (true) {  // This exits the method if a valid option is selected
            System.out.print("Enter your option: ");
            Scanner scanner = new Scanner(System.in);

            int choiceNumber = Integer.parseInt(scanner.nextLine()) - 1;
            return options.stream()
                    .filter(option -> option.value() == choiceNumber)
                    .findFirst();
        }

    }
}
