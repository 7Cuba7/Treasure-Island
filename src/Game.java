import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.IOException;

public class Game {
    private GameMap map;
    private Hero hero;
    private Treasure treasure;
    private List<Trap> traps;
    private List<Guardian> guardians;
    private EntityFactory entityFactory;
    private CollisionHandler collisionHandler;
    private Random random;
    private boolean gameOver;
    private String lastMessage;

    public Game() {
        this.map = new GameMap();
        this.hero = new Hero();
        this.treasure = new Treasure();
        this.random = new Random();
        this.gameOver = false;
        this.lastMessage = "";
        this.entityFactory = new EntityFactory(map);
        initializeEntities();
        this.collisionHandler = new CollisionHandler(hero, treasure, traps, guardians);
    }

    private void initializeEntities() {
        int trapCount = random.nextInt(GameConstants.MAX_TRAP_COUNT - GameConstants.MIN_TRAP_COUNT + 1)
                        + GameConstants.MIN_TRAP_COUNT;
        int guardianCount = random.nextInt(GameConstants.MAX_GUARDIAN_COUNT - GameConstants.MIN_GUARDIAN_COUNT + 1)
                            + GameConstants.MIN_GUARDIAN_COUNT;

        this.traps = entityFactory.createTraps(trapCount, hero.getPosition(), treasure.getPosition());
        this.guardians = entityFactory.createGuardians(guardianCount, hero.getPosition(), treasure.getPosition());
    }

    public void start() {
        displayWelcomeMessage();
        gameLoop();
    }

    private void displayWelcomeMessage() {
        System.out.println("\nMISSION:");
        System.out.println("   Find the treasure at the bottom-right corner");
        System.out.println("   and return to the starting point!");
        System.out.println("\nISLAND DANGERS:");
        System.out.println("   Traps: " + traps.size() + " (Basic: 1 damage, Heavy: 2 damage)");
        System.out.println("   Guardians: " + guardians.size() + " (" + GameConstants.GUARDIAN_DAMAGE + " damage if you lose minigame)");
        System.out.println("\nYour Health: " + GameConstants.HERO_MAX_HEALTH + " HP");
        System.out.println("\nControls: W (up), A (left), S (down), D (right)");
        System.out.println("\nPress any key to begin...");
        waitForInput();
    }

    private void waitForInput() {
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

                if (checkWinCondition()) {
                    displayWinMessage();
                    break;
                }

                char input = readPlayerInput();
                if (input == 'q') {
                    System.out.println("Thanks for playing!");
                    gameOver = true;
                    break;
                }

                processMove(String.valueOf(input));
                Thread.sleep(GameConstants.GAME_LOOP_DELAY_MS);
            }

            if (!hero.isAlive()) {
                displayLoseMessage();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean checkWinCondition() {
        return hero.hasTreasure() && hero.isAtPosition(new Position(GameConstants.HERO_START_ROW, GameConstants.HERO_START_COL));
    }

    private char readPlayerInput() throws IOException {
        int ch = System.in.read();
        return Character.toLowerCase((char) ch);
    }

    private void processMove(String input) {
        Position oldPos = new Position(hero.getPosition().getRow(), hero.getPosition().getCol());

        if (!moveHero(input)) {
            return;
        }

        if (!isValidMove()) {
            revertMove(oldPos);
            lastMessage = "Cannot move there! (Obstacle or out of bounds)";
            return;
        }

        lastMessage = collisionHandler.checkCollisions();

        if (hero.hasTreasure() && lastMessage.contains("TREASURE")) {
            relocateObstacles();
        }
    }

    private boolean moveHero(String input) {
        switch (input) {
            case "w":
                hero.moveUp();
                return true;
            case "s":
                hero.moveDown();
                return true;
            case "a":
                hero.moveLeft();
                return true;
            case "d":
                hero.moveRight();
                return true;
            default:
                lastMessage = "Invalid input! Use W, A, S, D to move.";
                return false;
        }
    }

    private boolean isValidMove() {
        return map.isWalkable(hero.getPosition());
    }

    private void revertMove(Position oldPos) {
        hero.getPosition().setRow(oldPos.getRow());
        hero.getPosition().setCol(oldPos.getCol());
    }

    private void relocateObstacles() {
        List<Trap> activeTraps = new ArrayList<>();
        List<Guardian> activeGuardians = new ArrayList<>();
        List<Obstacle> allObstacles = new ArrayList<>();

        for (Trap trap : traps) {
            if (!trap.isRevealed()) {
                Position newPos = entityFactory.getRandomWalkablePositionExcluding(hero.getPosition(), allObstacles);
                trap.setPosition(newPos);
                activeTraps.add(trap);
                allObstacles.add(trap);
            }
        }

        for (Guardian guardian : guardians) {
            if (!guardian.isRevealed()) {
                Position newPos = entityFactory.getRandomWalkablePositionExcluding(hero.getPosition(), allObstacles);
                guardian.setPosition(newPos);
                activeGuardians.add(guardian);
                allObstacles.add(guardian);
            }
        }

        traps = activeTraps;
        guardians = activeGuardians;
        collisionHandler.setTraps(traps);
        collisionHandler.setGuardians(guardians);
    }

    private void displayWinMessage() {
        map.display(hero, treasure, traps, guardians, lastMessage);
        System.out.println("\n========================================");
        System.out.println("     CONGRATULATIONS! YOU WON!");
        System.out.println("  You found the treasure and made it back alive!");
        System.out.println("  Final Health: " + hero.getHealth() + " HP");
        System.out.println("========================================");
    }

    private void displayLoseMessage() {
        map.display(hero, treasure, traps, guardians, lastMessage);
        System.out.println("\n========================================");
        System.out.println("            GAME OVER");
        System.out.println("  You have perished on the island.");
        System.out.println("  Better luck next time!");
        System.out.println("========================================");
    }
}
