public class HeavyTrap extends Trap {

    public HeavyTrap(Position position) {
        super(position);
    }

    @Override
    public int getDamage() {
        return GameConstants.HEAVY_TRAP_DAMAGE;
    }

    @Override
    public char getDisplayChar() {
        return 'X';
    }
}
