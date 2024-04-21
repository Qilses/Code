import java.util.Scanner;
import java.util.Random;

public class DiceGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int numDice = 6;
        while (numDice > 0) {
            System.out.println("You have " + numDice + " dice.");
            System.out.println("Press Enter to roll the dice...");
            scanner.nextLine();

            int[] diceRolls = rollDice(numDice, random);
            System.out.print("You rolled: ");
            for (int i = 0; i < numDice; i++) {
                System.out.print(diceRolls[i] + " ");
            }
            System.out.println();

            // Choose which die to save
            System.out.print("Enter the index of the die you want to save (1 to " + numDice + "): ");
            int saveIndex = scanner.nextInt() - 1;
            if (saveIndex < 0 || saveIndex >= numDice) {
                System.out.println("Invalid input. Please enter a number between 1 and " + numDice);
                continue;
            }
            // Calculate remaining dice
            numDice--;

            System.out.println("You have " + numDice + " dice left.");
        }
        scanner.close();
    }

    public static int[] rollDice(int numDice, Random random) {
        int[] diceRolls = new int[numDice];
        for (int i = 0; i < numDice; i++) {
            diceRolls[i] = random.nextInt(6) + 1; // Roll a die (random number between 1 and 6)
        }
        return diceRolls;
    }
}
