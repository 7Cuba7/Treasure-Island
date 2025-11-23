import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TrapTest {
    private BasicTrap basicTrap;
    private HeavyTrap heavyTrap;
    private Position testPosition;

    @BeforeEach
    public void setUp() {
        testPosition = new Position(5, 5);
        basicTrap = new BasicTrap(testPosition);
        heavyTrap = new HeavyTrap(testPosition);
    }

    @Test
    public void testBasicTrapDamage() {
        assertEquals(GameConstants.BASIC_TRAP_DAMAGE, basicTrap.getDamage(),
            "Basic trap should deal correct damage");
    }

    @Test
    public void testTrapOnEncounterDamagesHero() {
        Hero hero = new Hero();
        int initialHealth = hero.getHealth();
        basicTrap.onEncounter(hero);
        assertEquals(initialHealth - GameConstants.BASIC_TRAP_DAMAGE, hero.getHealth(),
            "Hero should take damage when encountering trap");
    }
}
