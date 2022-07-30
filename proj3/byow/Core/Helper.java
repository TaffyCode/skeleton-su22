package byow.Core;

public class Helper {
    public static int absMinus(int x, int y) {
        int z = x - y;
        if (z < 0) {
            return -z;
        }
        return z;
    }

    public static Integer[][] overlap(Room first, Room second) {
        Integer[][] array = new Integer[2][2];
        int lowestX = 500;
        int highestX = 0;
        int lowestY = 500;
        int highestY = 0;
        for (int x = first.getX() + 1; x <= (first.getLength() + first.getX() - 1); x++) {
            if (x > second.getX() + 1 && x < second.getX() + second.getLength() - 1) {
                if (lowestX > x) {
                    lowestX = x;
                }
                if (highestX < x) {
                    highestX = x;
                }
            }
        }
        for (int y = first.getY() + 1; y <= (first.getHeight() + first.getY() - 1); y++) {
            if (y > second.getY() + 1 && y < second.getY() + second.getHeight() - 1) {
                if (lowestY > y) {
                    lowestY = y;
                }
                if (highestY < y) {
                    highestY = y;
                }
            }
        }
        array[0][0] = lowestX;
        array[0][1] = highestX;
        array[1][0] = lowestY;
        array[1][1] = highestY;
        if (array[0][0] == 500 && array[0][1] == 0 && array[1][0] == 500 && array[1][1] == 0) {
            return null;
        }
        return array;
    }
}
