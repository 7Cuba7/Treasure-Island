import java.util.List;

public class CollisionHandler {
    private Hero hero;
    private Treasure treasure;
    private List<Trap> traps;
    private List<Guardian> guardians;

    public CollisionHandler(Hero hero, Treasure treasure, List<Trap> traps, List<Guardian> guardians) {
        this.hero = hero;
        this.treasure = treasure;
        this.traps = traps;
        this.guardians = guardians;
    }

    public String checkCollisions() {
        String message = checkTreasureCollision();
        if (!message.isEmpty()) {
            return message;
        }

        message = checkTrapCollision();
        if (!message.isEmpty()) {
            return message;
        }

        message = checkGuardianCollision();
        return message;
    }

    private String checkTreasureCollision() {
        if (!hero.hasTreasure() && treasure.isAtPosition(hero.getPosition())) {
            treasure.collect();
            hero.setTreasure(true);
            return "*** YOU FOUND THE TREASURE! Return to start [0][0]! ***";
        }
        return "";
    }

    private String checkTrapCollision() {
        for (Trap trap : traps) {
            if (!trap.isRevealed() && trap.isAtPosition(hero.getPosition())) {
                trap.onEncounter(hero);
                return "*** TRAP! You took " + trap.getDamage() + " damage! ***";
            }
        }
        return "";
    }

    private String checkGuardianCollision() {
        for (Guardian guardian : guardians) {
            if (!guardian.isRevealed() && guardian.isAtPosition(hero.getPosition())) {
                guardian.reveal();
                boolean won = guardian.combat();

                if (won) {
                    return "*** GUARDIAN! You defeated it! ***";
                } else {
                    hero.takeDamage(guardian.getDamage());
                    return "*** GUARDIAN! It hit you for " + guardian.getDamage() + " damage! ***";
                }
            }
        }
        return "";
    }

    public void setTraps(List<Trap> traps) {
        this.traps = traps;
    }

    public void setGuardians(List<Guardian> guardians) {
        this.guardians = guardians;
    }
}
