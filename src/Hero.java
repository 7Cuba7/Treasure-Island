public class Hero {
    private Position position;
    private int health;
    private boolean hasTreasure;
    private static final int MAX_HEALTH = 10;

    public Hero() {
        this.position = new Position(0, 0);
        this.health = MAX_HEALTH;
        this.hasTreasure = false;
    }

    public Position getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public boolean hasTreasure() {
        return hasTreasure;
    }

    public void setTreasure(boolean hasTreasure) {
        this.hasTreasure = hasTreasure;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void moveUp() {
        position.setRow(position.getRow() - 1);
    }

    public void moveDown() {
        position.setRow(position.getRow() + 1);
    }

    public void moveLeft() {
        position.setCol(position.getCol() - 1);
    }

    public void moveRight() {
        position.setCol(position.getCol() + 1);
    }

    public void resetPosition() {
        position.setRow(0);
        position.setCol(0);
    }
}
