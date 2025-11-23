public abstract class Obstacle implements GameObject {
    protected Position position;
    protected boolean revealed;

    public Obstacle(Position position) {
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

    public boolean isAtPosition(Position pos) {
        return position.equals(pos);
    }

    public abstract int getDamage();
    public abstract void onEncounter(Hero hero);
}
