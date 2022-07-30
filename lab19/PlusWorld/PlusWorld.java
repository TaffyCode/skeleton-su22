package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        for (int x = 0; x < 50; x += 1) {
            for (int y = 0; y < 50; y += 1) {
                if (x > 15 && x < 35) {
                    if (y > 5 && y < 45) {
                        world[x][y] = Tileset.WALL;
                        world[y][x] = Tileset.WALL;
                    }
                }
            }
        }
        ter.renderFrame(world);
    }
}
