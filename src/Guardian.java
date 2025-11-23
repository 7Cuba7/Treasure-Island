public class Guardian extends Obstacle {
    private CombatStrategy combatStrategy;

    public Guardian(Position position) {
        super(position);
        this.combatStrategy = new NumberGuessingStrategy();
    }

    public Guardian(Position position, CombatStrategy combatStrategy) {
        super(position);
        this.combatStrategy = combatStrategy;
    }

    @Override
    public int getDamage() {
        return GameConstants.GUARDIAN_DAMAGE;
    }

    @Override
    public void onEncounter(Hero hero) {
        reveal();
        boolean won = combat();
        if (!won) {
            hero.takeDamage(getDamage());
        }
    }

    @Override
    public char getDisplayChar() {
        return 'G';
    }

    public boolean combat() {
        return combatStrategy.executeCombat();
    }

    public void setCombatStrategy(CombatStrategy combatStrategy) {
        this.combatStrategy = combatStrategy;
    }
}
