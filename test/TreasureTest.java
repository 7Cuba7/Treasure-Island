import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TreasureTest {
    private Treasure treasure;

    @BeforeEach
    public void setUp() {
        treasure = new Treasure();
    }

    @Test
    public void testTreasureInitiallyNotCollected() {
        assertFalse(treasure.isCollected(),
            "Treasure should not be collected initially");
    }

    @Test
    public void testTreasureCanBeCollected() {
        treasure.collect();
        assertTrue(treasure.isCollected(),
            "Treasure should be collected after collect() is called");
    }
}
