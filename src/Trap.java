public abstract class Trap extends Obstacle {

    public Trap(Position position) {
        super(position);
    }

    @Override
    public void onEncounter(Hero hero) {
        reveal();
        hero.takeDamage(getDamage());
    }

    @Override
    public char getDisplayChar() {
        return 'T';
    }
}
