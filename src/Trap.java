public class Trap {
    private Position position;
    private boolean revealed;
    private static final int DAMAGE = 1;

    public Trap(Position position) {
        this.position = position;
        this.revealed = false;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.revealed = false;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        this.revealed = true;
    }

    public int getDamage() {
        return DAMAGE;
    }

    public boolean isAtPosition(Position pos) {
        return position.equals(pos);
    }
}
