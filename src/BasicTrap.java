public class BasicTrap extends Trap {

    public BasicTrap(Position position) {
        super(position);
    }

    @Override
    public int getDamage() {
        return GameConstants.BASIC_TRAP_DAMAGE;
    }
}
