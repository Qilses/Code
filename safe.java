import java.util.*;

public class safe {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the number of players
        System.out.println("Welcome to Yatzy 30!");
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Create a list to store player names
        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for player " + (i + 1) + ": ");
            playerNames.add(scanner.nextLine());
        }

        // Create a map to store dice throws for each player
        Map<String, List<Integer>> playerDiceThrows = new HashMap<>();
        for (String playerName : playerNames) {
            playerDiceThrows.put(playerName, new ArrayList<>());
        }

        // Keep track of remaining dice for each player
        Map<String, Integer> remainingDiceMap = new HashMap<>();
        for (String playerName : playerNames) {
            remainingDiceMap.put(playerName, 6); // Each player starts with 5 dice
        }

        // Play the game
        boolean gameRunning = true;
        while (gameRunning) {
            // Check if any player has remaining dice
            boolean anyPlayerHasDiceLeft = false;
            for (String playerName : playerNames) {
                if (remainingDiceMap.get(playerName) > 0) {
                    anyPlayerHasDiceLeft = true;
                    break;
                }
            }

            if (!anyPlayerHasDiceLeft) {
                gameRunning = false; // End the game if no player has dice left
                break;
            }

            // Roll the dice for each player
            for (String playerName : playerNames) {
                if (remainingDiceMap.get(playerName) == 0) {
                    continue; // Skip the player if they have no dice left
                }

                System.out.println("\n" + playerName + "'s turn:");
                System.out.println("Rolling dice...");

                int numDice = remainingDiceMap.get(playerName);
                int[] dice = rollDice(numDice); // Roll the remaining dice for the player
                System.out.println("Your roll: " + Arrays.toString(dice));

                List<Integer> keptDice = new ArrayList<>();
                List<Integer> remainingDice = new ArrayList<>();

                // Ask the player which dice to keep
                System.out.print("Enter the indexes (1-" + numDice + ") of dice you want to keep, separated by spaces (or 0 to skip): ");
                String input = scanner.nextLine();
                String[] keptIndexes = input.split(" ");
                for (String index : keptIndexes) {
                    int i = Integer.parseInt(index) - 1; // Adjust index
                    if (i >= 0 && i < numDice) {
                        keptDice.add(dice[i]);
                    }
                }

                // Filter out the kept dice and add the remaining ones to the list
                for (int i = 0; i < numDice; i++) {
                    if (!keptDice.contains(dice[i])) {
                        remainingDice.add(dice[i]);
                    }
                }

                // Update remaining dice count for the player
                remainingDiceMap.put(playerName, remainingDice.size());

                // Save the kept dice for the player
                playerDiceThrows.get(playerName).addAll(keptDice);

                System.out.println("Your kept dice: " + keptDice);
                System.out.println("Your remaining dice: " + remainingDice);
            }
        }

        // Display saved numbers for each player
        System.out.println("\nGame over! Saved numbers for each player:");
        for (String playerName : playerNames) {
            List<Integer> savedNumbers = playerDiceThrows.get(playerName);
            System.out.println(playerName + ": " + savedNumbers + " Sum: " + sum(savedNumbers));
        }
        scanner.close();
    }

    // Method to roll the dice
    private static int[] rollDice(int numDice) {
        Random rand = new Random();
        int[] dice = new int[numDice];
        for (int i = 0; i < numDice; i++) {
            dice[i] = rand.nextInt(6) + 1; // Random number between 1 and 6
        }
        return dice;

    }
    private static int sum(List<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;

    }
}

