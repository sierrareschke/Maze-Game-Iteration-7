package csci.ooad.polymorphia.strategy;

import csci.ooad.polymorphia.characters.Character;
import csci.ooad.polymorphia.command.Command;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanPromptStrategy implements HumanStrategy{
    @Override
    public Command prompt(Character character) {
        System.out.print("You are in room " + character.getCurrentLocation() + "\n\n");

        record Option(String name, int value){};
        List<Option> options = new ArrayList<Option>();
        options.add(new Option("Eat", 1));
        options.add(new Option("Fight", 2));
        options.add(new Option("Move", 3));
        options.add(new Option("Do Nothing", 4));
        for (Option option : options) {
            System.out.println(option.value + ": " + option.name);
        }

        while (true) {  // This exits the method if a valid option is selected
            System.out.print("Enter your option: ");
            Scanner scanner = new Scanner(System.in);


            int choiceNumber = Integer.parseInt(scanner.nextLine()) - 1;
            return options.get(choiceNumber);
        }

    }
}
