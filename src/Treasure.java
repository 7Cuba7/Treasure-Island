public class Treasure {
    private Position position;
    private boolean collected;

    public Treasure() {
        this.position = new Position(14, 14);
        this.collected = false;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }

    public boolean isAtPosition(Position pos) {
        return position.equals(pos);
    }
}
