import java.util.Random;
import java.util.Scanner;

public class Guardian {
    private Position position;
    private boolean revealed;
    private static final int DAMAGE = 2;
    private Random random;

    public Guardian(Position position) {
        this.position = position;
        this.revealed = false;
        this.random = new Random();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.revealed = false;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
    }

    public int getDamage() {
        return DAMAGE;
    }

    public boolean isAtPosition(Position pos) {
        return position.equals(pos);
    }


    public boolean combat() {
        Scanner scanner = new Scanner(System.in);
        int secretNumber = random.nextInt(10) + 1;
        int attempts = 3;

        System.out.println("        🗡️  GUARDIAN CHALLENGE! 🗡️         ");
        System.out.println("                                            ");
        System.out.println("  I'm thinking of a number between 1-10.    ");
        System.out.println("  You have 3 tries to guess it!             ");

        
        for (int i = 1; i <= attempts; i++) {
            System.out.print("Attempt " + i + "/3 - Enter your guess: ");
            
            try {
                int guess = scanner.nextInt();
                
                if (guess < 1 || guess > 10) {
                    System.out.println("Please enter a number between 1 and 10!");
                    i--;
                    continue;
                }
                
                if (guess == secretNumber) {
                    System.out.println("\n CORRECT! You defeated the guardian!");
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    scanner.nextLine();
                    return true;
                } else if (guess < secretNumber) {
                    if (i < attempts) {
                        System.out.println(" Too low! Try again.");
                    }
                } else {
                    if (i < attempts) {
                        System.out.println(" Too high! Try again.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                i--;
            }
        }
        
        System.out.println("\n Out of attempts! The number was: " + secretNumber);
        System.out.println(" The guardian strikes you for " + DAMAGE + " damage!");
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
        return false;
    }
}
