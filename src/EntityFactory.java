import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class EntityFactory {
    private Random random;
    private GameMap map;

    public EntityFactory(GameMap map) {
        this.random = new Random();
        this.map = map;
    }

    public List<Trap> createTraps(int count, Position heroPosition, Position treasurePosition) {
        List<Trap> traps = new ArrayList<>();
        Set<Position> excludedPositions = new HashSet<>();
        excludedPositions.add(heroPosition);
        excludedPositions.add(treasurePosition);

        for (int i = 0; i < count; i++) {
            Position pos = getRandomWalkablePosition(excludedPositions);
            excludedPositions.add(pos);

            Trap trap = createRandomTrap(pos);
            traps.add(trap);
        }

        return traps;
    }

    private Trap createRandomTrap(Position position) {
        int trapType = random.nextInt(100);
        if (trapType < GameConstants.BASIC_TRAP_PROBABILITY) {
            return new BasicTrap(position);
        } else {
            return new HeavyTrap(position);
        }
    }

    public List<Guardian> createGuardians(int count, Position heroPosition, Position treasurePosition) {
        List<Guardian> guardians = new ArrayList<>();
        Set<Position> excludedPositions = new HashSet<>();
        excludedPositions.add(heroPosition);
        excludedPositions.add(treasurePosition);

        for (int i = 0; i < count; i++) {
            Position pos = getRandomWalkablePosition(excludedPositions);
            excludedPositions.add(pos);
            guardians.add(new Guardian(pos));
        }

        return guardians;
    }

    public Position getRandomWalkablePosition(Set<Position> excludedPositions) {
        Position pos;
        do {
            int row = random.nextInt(map.getSize());
            int col = random.nextInt(map.getSize());
            pos = new Position(row, col);
        } while (!map.isWalkable(pos) || excludedPositions.contains(pos));
        return pos;
    }

    public Position getRandomWalkablePositionExcluding(Position heroPosition, List<Obstacle> obstacles) {
        Position pos;
        Set<Position> occupiedPositions = new HashSet<>();
        occupiedPositions.add(heroPosition);

        for (Obstacle obstacle : obstacles) {
            occupiedPositions.add(obstacle.getPosition());
        }

        do {
            int row = random.nextInt(map.getSize());
            int col = random.nextInt(map.getSize());
            pos = new Position(row, col);
        } while (!map.isWalkable(pos) || occupiedPositions.contains(pos));

        return pos;
    }
}
