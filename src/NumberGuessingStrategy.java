import java.util.Random;
import java.util.Scanner;

public class NumberGuessingStrategy implements CombatStrategy {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 10;
    private static final int MAX_ATTEMPTS = 3;
    private Random random;

    public NumberGuessingStrategy() {
        this.random = new Random();
    }

    @Override
    public boolean executeCombat() {
        Scanner scanner = new Scanner(System.in);
        int secretNumber = random.nextInt(MAX_NUMBER) + MIN_NUMBER;

        System.out.println("        🗡️  GUARDIAN CHALLENGE! 🗡️         ");
        System.out.println("                                            ");
        System.out.println("  I'm thinking of a number between " + MIN_NUMBER + "-" + MAX_NUMBER + ".");
        System.out.println("  You have " + MAX_ATTEMPTS + " tries to guess it!");

        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            System.out.print("Attempt " + i + "/" + MAX_ATTEMPTS + " - Enter your guess: ");

            if (!processGuess(scanner, secretNumber, i)) {
                continue;
            }

            System.out.println("\n CORRECT! You defeated the guardian!");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
            return true;
        }

        displayDefeat(scanner, secretNumber);
        return false;
    }

    private boolean processGuess(Scanner scanner, int secretNumber, int attemptNumber) {
        try {
            int guess = scanner.nextInt();

            if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                System.out.println("Please enter a number between " + MIN_NUMBER + " and " + MAX_NUMBER + "!");
                return false;
            }

            if (guess == secretNumber) {
                return true;
            }

            provideHint(guess, secretNumber, attemptNumber);
            return false;

        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine();
            return false;
        }
    }

    private void provideHint(int guess, int secretNumber, int attemptNumber) {
        if (attemptNumber < MAX_ATTEMPTS) {
            if (guess < secretNumber) {
                System.out.println(" Too low! Try again.");
            } else {
                System.out.println(" Too high! Try again.");
            }
        }
    }

    private void displayDefeat(Scanner scanner, int secretNumber) {
        System.out.println("\n Out of attempts! The number was: " + secretNumber);
        System.out.println(" The guardian strikes you!");
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }

}
