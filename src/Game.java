import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;

public class Game {
    private GameMap map;
    private Hero hero;
    private Treasure treasure;
    private ArrayList<Trap> traps;
    private ArrayList<Guardian> guardians;
    private Random random;
    private boolean gameOver;
    private String lastMessage;

    public Game() {
        this.map = new GameMap();
        this.hero = new Hero();
        this.treasure = new Treasure();
        this.traps = new ArrayList<>();
        this.guardians = new ArrayList<>();
        this.random = new Random();
        this.gameOver = false;
        this.lastMessage = "";
    }

    public void start() {
        initializeGame();
        gameLoop();
    }

    private void initializeGame() {

        int trapCount = random.nextInt(4) + 3;
        for (int i = 0; i < trapCount; i++) {
            Position pos = getRandomWalkablePosition();
            traps.add(new Trap(pos));
        }

        int guardianCount = random.nextInt(3) + 3;
        for (int i = 0; i < guardianCount; i++) {
            Position pos = getRandomWalkablePosition();
            guardians.add(new Guardian(pos));
        }

        System.out.println("\nMISSION:");
        System.out.println("   Find the treasure at the bottom-right corner");
        System.out.println("   and return to the starting point!");
        System.out.println("\nISLAND DANGERS:");
        System.out.println("   Traps: " + trapCount + " (1 damage each)");
        System.out.println("   Guardians: " + guardianCount + " (2 damage if you lose minigame)");
        System.out.println("\nYour Health: 10 HP");
        System.out.println("\nControls: W (up), A (left), S (down), D (right)");
        System.out.println("\nPress any key to begin...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gameLoop() {
        try {

            while (!gameOver && hero.isAlive()) {
                map.display(hero, treasure, traps, guardians, lastMessage);


                if (hero.hasTreasure() && hero.getPosition().equals(new Position(0, 0))) {
                    win();
                    break;
                }

                // input
                int ch = System.in.read();
                char input = Character.toLowerCase((char) ch);

                if (input == 'q') {
                    System.out.println("Thanks for playing!");
                    gameOver = true;
                    break;
                }

                processMove(String.valueOf(input));


                Thread.sleep(50);
            }

            if (!hero.isAlive()) {
                lose();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    private void processMove(String input) {
        Position oldPos = new Position(hero.getPosition().getRow(), hero.getPosition().getCol());


        switch (input) {
            case "w":
                hero.moveUp();
                break;
            case "s":
                hero.moveDown();
                break;
            case "a":
                hero.moveLeft();
                break;
            case "d":
                hero.moveRight();
                break;
            default:
                lastMessage = "Invalid input! Use W, A, S, D to move.";
                return;
        }


        if (!map.isWalkable(hero.getPosition())) {
            lastMessage = "Cannot move there! (Obstacle or out of bounds)";
            hero.getPosition().setRow(oldPos.getRow());
            hero.getPosition().setCol(oldPos.getCol());
            return;
        }

        // Check treasure
        if (!hero.hasTreasure() && treasure.isAtPosition(hero.getPosition())) {
            treasure.collect();
            hero.setTreasure(true);
            lastMessage = "*** YOU FOUND THE TREASURE! Return to start [0][0]! ***";
            relocateTrapsAndGuardians();
            return;
        }

        // Check trap
        for (Trap trap : traps) {
            if (!trap.isRevealed() && trap.isAtPosition(hero.getPosition())) {
                trap.reveal();
                hero.takeDamage(trap.getDamage());
                lastMessage = "*** TRAP! You took " + trap.getDamage() + " damage! ***";
                return;
            }
        }

        // Check guardian
        for (Guardian guardian : guardians) {
            if (!guardian.isRevealed() && guardian.isAtPosition(hero.getPosition())) {
                guardian.reveal();

                
                boolean won = guardian.combat();

                
                if (won) {
                    lastMessage = "*** GUARDIAN! You defeated it! ***";
                } else {
                    hero.takeDamage(guardian.getDamage());
                    lastMessage = "*** GUARDIAN! It hit you for " + guardian.getDamage() + " damage! ***";
                }
                
                return;
            }
        }


        lastMessage = "";
    }

    private void relocateTrapsAndGuardians() {
        

        ArrayList<Trap> newTraps = new ArrayList<>();
        ArrayList<Guardian> newGuardians = new ArrayList<>();
        
        for (Trap trap : traps) {
            if (!trap.isRevealed()) {
                Position newPos = getRandomWalkablePositionExcludingHero();
                trap.setPosition(newPos);
                newTraps.add(trap);
            }
        }
        
        for (Guardian guardian : guardians) {
            if (!guardian.isRevealed()) {
                Position newPos = getRandomWalkablePositionExcludingHero();
                guardian.setPosition(newPos);
                newGuardians.add(guardian);
            }
        }
        
        traps = newTraps;
        guardians = newGuardians;
    }

    private Position getRandomWalkablePosition() {
        Position pos;
        do {
            int row = random.nextInt(map.getSize());
            int col = random.nextInt(map.getSize());
            pos = new Position(row, col);
        } while (!map.isWalkable(pos) || 
                 pos.equals(new Position(0, 0)) || 
                 pos.equals(treasure.getPosition()) ||
                 isPositionOccupied(pos));
        return pos;
    }

    private Position getRandomWalkablePositionExcludingHero() {
        Position pos;
        do {
            int row = random.nextInt(map.getSize());
            int col = random.nextInt(map.getSize());
            pos = new Position(row, col);
        } while (!map.isWalkable(pos) || 
                 pos.equals(hero.getPosition()) ||
                 isPositionOccupied(pos));
        return pos;
    }

    private boolean isPositionOccupied(Position pos) {
        for (Trap trap : traps) {
            if (trap.isAtPosition(pos)) {
                return true;
            }
        }
        for (Guardian guardian : guardians) {
            if (guardian.isAtPosition(pos)) {
                return true;
            }
        }
        return false;
    }


    private void win() {
        map.display(hero, treasure, traps, guardians, lastMessage);
        System.out.println("\n========================================");
        System.out.println("     CONGRATULATIONS! YOU WON!");
        System.out.println();
        System.out.println("  You found the treasure and made");
        System.out.println("  it back alive!");
        System.out.println();
        System.out.println("  Final Health: " + hero.getHealth() + " HP");
        System.out.println("========================================");
    }

    private void lose() {
        map.display(hero, treasure, traps, guardians, lastMessage);
        System.out.println("\n========================================");
        System.out.println("            GAME OVER");
        System.out.println();
        System.out.println("  You have perished on the island.");
        System.out.println("  Better luck next time!");
        System.out.println("========================================");
    }
}
