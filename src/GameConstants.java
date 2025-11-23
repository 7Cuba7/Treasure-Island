public class GameConstants {
    // Hero constants
    public static final int HERO_MAX_HEALTH = 10;
    public static final int HERO_START_ROW = 0;
    public static final int HERO_START_COL = 0;

    // Treasure constants
    public static final int TREASURE_ROW = 14;
    public static final int TREASURE_COL = 14;

    // Entity spawn constants
    public static final int MIN_TRAP_COUNT = 3;
    public static final int MAX_TRAP_COUNT = 7;
    public static final int MIN_GUARDIAN_COUNT = 3;
    public static final int MAX_GUARDIAN_COUNT = 6;

    // Trap type probability (percentage)
    public static final int BASIC_TRAP_PROBABILITY = 70;

    // Damage constants
    public static final int BASIC_TRAP_DAMAGE = 1;
    public static final int HEAVY_TRAP_DAMAGE = 2;
    public static final int GUARDIAN_DAMAGE = 2;

    // Map constants
    public static final int MAP_SIZE = 15;

    // Game loop constants
    public static final int GAME_LOOP_DELAY_MS = 50;

    private GameConstants() {
        // Prevent instantiation
    }
}
