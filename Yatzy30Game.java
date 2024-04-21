import java.util.*;

public class Yatzy30Game {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numPlayers = 0;
        List<String> spelarNamns = new ArrayList<>();
        Map<String, List<Integer>> playerDiceThrows = new HashMap<>();
        Map<String, Integer> remDiceMap = new HashMap<>();
        
       
                if (numPlayers == 0) {
                    // Hämta antalet spelare om det inte redan är initialiserat
                    System.out.println("Välkommen till Yatzy 30!");
                    System.out.print("Ange antalet spelare: ");
                    numPlayers = scanner.nextInt();
                    scanner.nextLine(); // Konsumera ny rad

                    // Skapa en lista för att lagra spelarnamn
                    for (int i = 0; i < numPlayers; i++) {
                        System.out.print("Ange namn för spelare " + (i + 1) + ": ");
                        spelarNamns.add(scanner.nextLine());
                    }

                    // Initialisera playerDiceThrows och remDiceMap
                    for (String spelarNamn : spelarNamns) {
                        playerDiceThrows.put(spelarNamn, new ArrayList<>());
                        remDiceMap.put(spelarNamn, 6); // Varje spelare börjar med 5 tärningar
                    }
                }
                try {
                    boolean restartGame;
                    do {
                // Spela spelet
                boolean gameRunning = true;
                while (gameRunning) {
                    // Kontrollera om någon spelare har kvarvarande tärningar
                    boolean anyPlayerHasDiceLeft = false;
                    for (String spelarNamn : spelarNamns) {
                        if (remDiceMap.get(spelarNamn) > 0) {
                            anyPlayerHasDiceLeft = true;
                            break;
                        }
                    }

                    if (!anyPlayerHasDiceLeft) {
                        gameRunning = false; // Avsluta spelet om ingen spelare har tärningar kvar
                        break;
                    }

                    // Slå tärningarna för varje spelare
                    for (String spelarNamn : spelarNamns) {
                        if (remDiceMap.get(spelarNamn) == 0) {
                            continue; // Hoppa över spelaren om de inte har några tärningar kvar
                        }

                        System.out.println("\n" + spelarNamn + "'s tur:");
                        System.out.println("Slår tärningarna...");

                        int tärningsNumber = remDiceMap.get(spelarNamn);
                        int[] dice = tärKast(tärningsNumber); // Slå de återstående tärningarna för spelaren
                        System.out.println("Ditt slag: " + Arrays.toString(dice));

                        List<Integer> keptDice = new ArrayList<>();
                        List<Integer> remDice = new ArrayList<>();

                        // Fråga spelaren vilka tärningar de vill behålla
                        System.out.print("Ange index (1-" + tärningsNumber + ") för de tärningar du vill behålla, separerade av mellanslag (eller 0 för att hoppa över): ");
                        String input = scanner.nextLine();
                        String[] keptIndexes = input.split(" ");
                        for (String index : keptIndexes) {
                            int i = Integer.parseInt(index) - 1; // Justera index
                            if (i >= 0 && i < tärningsNumber) {
                                keptDice.add(dice[i]);
                            }
                        }

                        // Filtrera ut de behållna tärningarna och lägg till de återstående i listan
                        for (int i = 0; i < tärningsNumber; i++) {
                            if (!keptDice.contains(dice[i])) {
                                remDice.add(dice[i]);
                            }
                        }

                        // Uppdatera antalet återstående tärningar för spelaren
                        remDiceMap.put(spelarNamn, remDice.size());

                        // Spara de behållna tärningarna för spelaren
                        playerDiceThrows.get(spelarNamn).addAll(keptDice);

                        System.out.println("Dina behållna tärningar: " + keptDice);
                        System.out.println("Dina återstående tärningar: " + remDice);
                    }
                }

                // Visa sparade nummer för varje spelare
                System.out.println("\nSpelet är över! Sparade nummer för varje spelare:");
                for (String spelarNamn : spelarNamns) {
                    List<Integer> sparradeNumber = playerDiceThrows.get(spelarNamn);
                    System.out.println(spelarNamn + ": " + sparradeNumber + " Summa: " + sum(sparradeNumber));
                }

                // Fråga om användaren vill starta om
                System.out.println("\nTryck 'r' för att starta om eller annan tangent för att avsluta:");
                char userInput = scanner.nextLine().charAt(0); // Hämta det första tecknet av inmatningen
                restartGame = (userInput == 'r');
            } while (restartGame);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Metod för att slå tärningarna
    private static int[] tärKast(int tärningsNumber) {
        Random rand = new Random();
        int[] dice = new int[tärningsNumber];
        for (int i = 0; i < tärningsNumber; i++) {
            dice[i] = rand.nextInt(6) + 1; // Slumpmässigt tal mellan 1 och 6
        }
        return dice;
    }

    // Metod för att beräkna summan av nummer i en lista
    private static int sum(List<Integer> numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }
}
