package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

public class WorldGenerator {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    private final Random RANDOM;

    private int[] playerLocation = new int[2];

    private TETile playerCurrent;


    private TERenderer ter;

    private List<Room> roomsList = new ArrayList<Room>();

    private TETile[][] world = new TETile[WIDTH][HEIGHT];
    public WorldGenerator(Long seed) {
        ter = new TERenderer();
        RANDOM = new Random(seed);
        generate();
    }

    public void draw() {
        ter.renderFrame(world);
    }

    public void generate() {

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        rooms();
        hallways();
        spawnCharacter();
    }

    public void rooms() {
        int roomCount = RandomUtils.uniform(RANDOM, 20, 30);
        while (roomCount != 0) {
            int xCoord = RandomUtils.uniform(RANDOM, 0, WIDTH - 20);
            int yCoord = RandomUtils.uniform(RANDOM, 0, HEIGHT - 20);
            if (roomBuild(xCoord, yCoord)) {
                roomCount--;
            }
        }
    }

    public void hallways() {
        List<Room> already = new ArrayList<>();
        for (Room each : roomsList) {
            List<Room> list = new ArrayList<>(roomsList);
            list.remove(each);
            boolean created = false;
            while (!created) {
                Room closest = closestRoom(each, list);
                if (list.isEmpty()) {
                    created = true;
                    hallwayBuild(each, closestRoom(each, roomsList));
                } else if (already.contains(closest)) {
                    list.remove(closest);
                } else {
                    created = true;
                    already.add(each);
                    hallwayBuild(each, closest);
                }
            }
        }
    }

    public void hallwayBuild(Room first, Room second) {
        Integer[][] list = Helper.overlap(first, second);
        if (null != list) {
            if (list[0][0] == 500) {
                int builder;
                int finish;
                if (first.getX() < second.getX()) {
                    builder = first.getX() + first.getLength();
                    finish = second.getX();
                } else {
                    builder = second.getX() + second.getLength();
                    finish = first.getX();
                }
                int y;
                if (Objects.equals(list[1][0], list[1][1])) {
                    y = list[1][0];
                } else {
                    y = RandomUtils.uniform(RANDOM, list[1][0], list[1][1]);
                }
                if (y == 0) {
                    y++;
                }
                if (y == HEIGHT) {
                    y--;
                }
                while (builder <= finish) {
                    if (attemptPlace(builder, y)) {
                        attemptWall(builder, y + 1);
                        attemptWall(builder, y - 1);
                    }
                    builder++;
                }
            } else {
                int builder;
                int finish;
                if (first.getY() < second.getY()) {
                    builder = first.getY() + first.getHeight();
                    finish = second.getY();
                } else {
                    builder = second.getY() + second.getHeight();
                    finish = first.getY();
                }
                int x;
                if (Objects.equals(list[0][0], list[0][1])) {
                    x = list[0][0];
                } else {
                    x = RandomUtils.uniform(RANDOM, list[0][0], list[0][1]);
                }
                if (x == 0) {
                    x++;
                }
                if (x == WIDTH) {
                    x--;
                }
                while (builder <= finish) {
                    world[x][builder] = Tileset.FLOOR;
                    if (world[x - 1][builder] == Tileset.NOTHING) {
                        world[x - 1][builder] = Tileset.WALL;
                    }
                    if (world[x + 1][builder] == Tileset.NOTHING) {
                        world[x + 1][builder] = Tileset.WALL;
                    }
                    builder++;
                }
            }
        } else {
            hallwayHelper(first, second);
        }
    }

    public void hallwayHelper(Room first, Room second) {
        int coordX = second.getX() + second.getLength() - 1;
        int coordY = first.getY() + first.getHeight() - 1;
        int jointX = RandomUtils.uniform(RANDOM, second.getX() + 1, coordX);
        int jointY = RandomUtils.uniform(RANDOM, first.getY() + 1, coordY);
        int currentX = jointX;
        int currentY = jointY;
        if (jointX <= first.getX()) {
            while (currentX <= first.getX()) {
                if (attemptPlace(currentX, currentY)) {
                    attemptWall(currentX, currentY + 1);
                    attemptWall(currentX, currentY - 1);
                }
                currentX++;
            }
        } else if (jointX >= first.getX()) {
            while (currentX > first.getX()) {
                if (attemptPlace(currentX, currentY)) {
                    attemptWall(currentX, currentY + 1);
                    attemptWall(currentX, currentY - 1);
                }
                currentX--;
            }
        }
        if (jointY <= second.getY()) {
            while (currentY <= second.getY()) {
                if (attemptPlace(jointX, currentY)) {
                    attemptWall(jointX + 1, currentY);
                    attemptWall(jointX - 1, currentY);
                }
                currentY++;
            }
        } else if (jointY >= second.getY()) {
            while (currentY > second.getY()) {
                if (attemptPlace(jointX, currentY)) {
                    attemptWall(jointX + 1, currentY);
                    attemptWall(jointX - 1, currentY);
                }
                currentY--;
            }
        }
        for (int x = jointX - 1; x <= jointX + 1; x++) {
            for (int y = jointY - 1; y <= jointY + 1; y++) {
                attemptWall(x, y);
            }
        }
    }

    public boolean attemptPlace(int x, int y) {
        TETile place = world[x][y];
        if (place == Tileset.NOTHING) {
            world[x][y] = Tileset.FLOOR;
            return true;
        } else if (place == Tileset.WALL) {
            world[x][y] = Tileset.FLOOR;
            return true;
        } else {
            return false;
        }
    }

    public void attemptWall(int x, int y) {
        if (world[x][y] == Tileset.NOTHING) {
            world[x][y] = Tileset.WALL;
        }
    }

    public boolean roomBuild(int x, int y) {
        int roomLength = RandomUtils.uniform(RANDOM, 6, 15);
        int roomHeight = RandomUtils.uniform(RANDOM, 6, 15);
        if (searchSpace(x, y, x + roomLength, y + roomHeight)) {
            for (int xCoord = x; xCoord <= roomLength + x; xCoord += 1) {
                for (int yCoord = y; yCoord <= roomHeight + y; yCoord += 1) {
                    if (xCoord == x || yCoord == y) {
                        world[xCoord][yCoord] = Tileset.WALL;
                    } else if (xCoord == x + roomLength || yCoord == y + roomHeight) {
                        world[xCoord][yCoord] = Tileset.WALL;
                    } else {
                        world[xCoord][yCoord] = Tileset.FLOOR;
                    }
                }
            }
            roomsList.add(new Room(x, y, roomLength, roomHeight));
            return true;
        }
        return false;
    }

    public boolean searchSpace(int x, int y, int xSearch, int ySearch) {
        for (int xCoord = x; xCoord < xSearch; xCoord += 1) {
            for (int yCoord = y; yCoord < ySearch; yCoord += 1) {
                if (world[xCoord][yCoord] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public Room closestRoom(Room room, List<Room> list) {
        int store = 200;
        Room closestSoFar = null;
        for (Room each : list) {
            int xDistance = Helper.absMinus(room.getX(), each.getX());
            int yDistance = Helper.absMinus(room.getY(), each.getY());
            int total = xDistance + yDistance;
            if (total != 0) {
                if (store > total) {
                    store = total;
                    closestSoFar = each;
                }
            }
        }
        return closestSoFar;
    }

    public void spawnCharacter() {
        while (true) {
            int x = RandomUtils.uniform(RANDOM, 0, 90);
            int y = RandomUtils.uniform(RANDOM, 0, 50);
            if (world[x][y] == Tileset.FLOOR) {
                playerCurrent = world[x][y];
                world[x][y] = Tileset.AVATAR;
                playerLocation[0] = x;
                playerLocation[1] = y;
                break;
            }
        }
    }

    public void move(char direction) {
        if (direction == 'W') {
            if (world[playerLocation[0]][playerLocation[1] + 1] != Tileset.WALL) {
                world[playerLocation[0]][playerLocation[1]] = playerCurrent;
                world[playerLocation[0]][playerLocation[1] + 1] = Tileset.AVATAR;
                playerLocation[1] += 1;
            }
        } else if (direction == 'A') {
            if (world[playerLocation[0] - 1][playerLocation[1]] != Tileset.WALL) {
                world[playerLocation[0]][playerLocation[1]] = playerCurrent;
                world[playerLocation[0] - 1][playerLocation[1]] = Tileset.AVATAR;
                playerLocation[0] -= 1;
            }
        } else if (direction == 'S') {
            if (world[playerLocation[0]][playerLocation[1] - 1] != Tileset.WALL) {
                world[playerLocation[0]][playerLocation[1]] = playerCurrent;
                world[playerLocation[0]][playerLocation[1] - 1] = Tileset.AVATAR;
                playerLocation[1] -=1;
            }
        } else if (direction == 'D') {
            if (world[playerLocation[0] + 1][playerLocation[1]] != Tileset.WALL) {
                world[playerLocation[0]][playerLocation[1]] = playerCurrent;
                world[playerLocation[0] + 1][playerLocation[1]] = Tileset.AVATAR;
                playerLocation[0] += 1;
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }
}
