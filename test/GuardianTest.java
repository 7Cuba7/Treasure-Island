import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class GuardianTest {
    private Guardian guardian;
    private Position testPosition;

    @BeforeEach
    public void setUp() {
        testPosition = new Position(7, 7);
        guardian = new Guardian(testPosition);
    }

    @Test
    public void testGuardianDamage() {
        assertEquals(GameConstants.GUARDIAN_DAMAGE, guardian.getDamage(),
            "Guardian should deal correct damage");
    }

    @Test
    public void testGuardianCanChangeCombatStrategy() {
        CombatStrategy alwaysLoseStrategy = new CombatStrategy() {
            @Override
            public boolean executeCombat() {
                return false;
            }


        };

        guardian.setCombatStrategy(alwaysLoseStrategy);
        assertFalse(guardian.combat(),
            "Guardian should use new strategy after setCombatStrategy");
    }
}
