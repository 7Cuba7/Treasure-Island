public class Treasure implements GameObject {
    private Position position;
    private boolean collected;

    public Treasure() {
        this.position = new Position(GameConstants.TREASURE_ROW, GameConstants.TREASURE_COL);
        this.collected = false;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }

    @Override
    public boolean isAtPosition(Position pos) {
        return position.equals(pos);
    }

    @Override
    public char getDisplayChar() {
        return '$';
    }
}
