package byow.Core;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final int x;
    private final int y;
    private final int length;
    private final int height;
    private final List<Room> connected;

    public Room(int x, int y, int length, int height) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
        this.connected = new ArrayList<Room>();
        connected.add(this);
    }

    public void connect(Room connection) {
        connected.add(connection);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public List<Room> getConnected() {
        return connected;
    }

    public boolean checkConnected(Room room) {
        for (Room each : connected) {
            if (each.equals(room)) {
                return true;
            }
        }
        return false;
    }
}
