public class GameMap {
    private static final int SIZE = 15;
    private char[][] baseMap;
    private static final String[] MAP_LAYOUT = {
        "...............",
        "..#..#....#....",
        "..#..#....#..#.",
        "..#..###..#....",
        "...............",
        "..####..#..#...",
        "..............#",
        ".....#...#.....",
        "..#..#...#..#..",
        "..#..#...#.....",
        "..#....#.......",
        "..#.......##.##",
        "......#####....",
        "....#..........",
        "..........#...."
    };


    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";      // Žalias
    private static final String YELLOW = "\u001B[33m";     // Geltona
    private static final String RED = "\u001B[31m";        // Raudona
    private static final String MAGENTA = "\u001B[35m";    // Violetinė
    private static final String GRAY = "\u001B[97m";       // Pilka

    public GameMap() {
        initializeMap();
    }

    private void initializeMap() {
        baseMap = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            baseMap[i] = MAP_LAYOUT[i].toCharArray();
        }
    }

    public char[][] getBaseMap() {
        return baseMap;
    }

    public boolean isWalkable(Position pos) {
        if (!pos.isValid(SIZE, SIZE)) {
            return false;
        }
        return baseMap[pos.getRow()][pos.getCol()] == '.';
    }

    public boolean isObstacle(Position pos) {
        if (!pos.isValid(SIZE, SIZE)) {
            return true;
        }
        return baseMap[pos.getRow()][pos.getCol()] == '#';
    }

    public int getSize() {
        return SIZE;
    }

    public void display(Hero hero, Treasure treasure, java.util.ArrayList<Trap> traps, java.util.ArrayList<Guardian> guardians, String message) {

        clearScreen();


        char[][] displayMap = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                displayMap[i][j] = baseMap[i][j];
            }
        }


        for (Trap trap : traps) {
            if (trap.isRevealed()) {
                Position pos = trap.getPosition();
                displayMap[pos.getRow()][pos.getCol()] = 'T';
            }
        }


        for (Guardian guardian : guardians) {
            if (guardian.isRevealed()) {
                Position pos = guardian.getPosition();
                displayMap[pos.getRow()][pos.getCol()] = 'G';
            }
        }


        if (!treasure.isCollected()) {
            Position treasurePos = treasure.getPosition();
            displayMap[treasurePos.getRow()][treasurePos.getCol()] = '$';
        }


        Position heroPos = hero.getPosition();
        displayMap[heroPos.getRow()][heroPos.getCol()] = 'H';


        System.out.println("=== ISLAND TREASURE HUNT ===\n");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                char cell = displayMap[i][j];
                String coloredCell = getColoredChar(cell);
                System.out.print(coloredCell + " ");
            }
            System.out.println();
        }


        System.out.println("\n=== STATUS ===");
        System.out.println("Health: " + hero.getHealth() + " HP");
        System.out.println("Treasure: " + (hero.hasTreasure() ? "COLLECTED" : "Not collected"));
        System.out.println("\nControls: W (up), A (left), S (down), D (right), Q (quit)");
        

        if (message != null && !message.isEmpty()) {
            System.out.println("\n" + message);
        }
    }

    private String getColoredChar(char c) {
        switch (c) {
            case 'H':
                return GREEN + c + RESET;
            case '$':
                return YELLOW + c + RESET;
            case 'T':
                return RED + c + RESET;
            case 'G':
                return MAGENTA + c + RESET;
            case '#':
                return GRAY + c + RESET;
            default:
                return String.valueOf(c);
        }
    }

    private void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("win")) {

                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (Exception e) {

                    for (int i = 0; i < 50; i++) {
                        System.out.println();
                    }
                }
            } else {

                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {

            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
