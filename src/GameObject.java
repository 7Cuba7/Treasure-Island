public interface GameObject {
    Position getPosition();
    boolean isAtPosition(Position pos);
    char getDisplayChar();
}
