import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {
    private Hero hero;

    @BeforeEach
    public void setUp() {
        hero = new Hero();
    }

    @Test
    public void testHeroTakesDamage() {
        int initialHealth = hero.getHealth();
        int damage = 3;
        hero.takeDamage(damage);
        assertEquals(initialHealth - damage, hero.getHealth(),
            "Hero health should decrease by damage amount");
    }

    @Test
    public void testHeroIsDeadWhenHealthZero() {
        hero.takeDamage(GameConstants.HERO_MAX_HEALTH);
        assertFalse(hero.isAlive(),
            "Hero should be dead when health is 0");
    }
}
