import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void testPositionEquality() {
        Position pos1 = new Position(2, 3);
        Position pos2 = new Position(2, 3);
        assertTrue(pos1.equals(pos2), "Positions with same coordinates should be equal");
    }

    @Test
    public void testPositionIsValidWithinBounds() {
        Position pos = new Position(5, 5);
        assertTrue(pos.isValid(10, 10), "Position should be valid within bounds");
    }
}
