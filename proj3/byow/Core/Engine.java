package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 60;

    public boolean play = false;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        titleScreen();
        while (true) {
            InputSource keyboard = new KeyboardInputSource();

            while (!play) {
                char each = Character.toUpperCase(keyboard.getNextKey());
                if ()
            }
        }
    }

    public void titleScreen() {
        WorldGenerator world = new WorldGenerator(8451L);
        ter.initialize(WIDTH, HEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font title = new Font("Arial", Font.BOLD, 64);
        StdDraw.setFont(title);
        StdDraw.text(50,45, "CS61B: The Game");
        Font context = new Font("Arial", Font.BOLD, 24);
        StdDraw.setFont(context);
        StdDraw.text(50, 40, "New Game (N)");
        StdDraw.text(50, 38, "Load Game (L)");
        StdDraw.text(50, 36, "Quit (Q)");
        StdDraw.show();
    }



    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        WorldGenerator a;
        if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
            String last = input.substring(input.length() - 1);
            char lastCharacter = last.charAt(0);
            if (lastCharacter == 'S' || lastCharacter == 's') {
                String seed = input.substring(1, input.length() - 1);
                Long newSeed = Long.parseLong(seed);
                a = new WorldGenerator(newSeed);
            } else {
                throw new Error("Seed didn't end with 'S' or 's'");
            }
        } else {
            throw new Error("Seed didn't start with 'N' or 'n'");
        }
        TETile[][] finalWorldFrame = a.getWorld();
        return finalWorldFrame;
    }

    public void load() {
        File load = new File("./save.txt");
        String inputs = null;
        if (load.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(load);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                inputs = objectInputStream.readObject().toString();
            } catch (FileNotFoundException exception) {
                System.out.println("Error: File Not Found");
                return;
            } catch (IOException exception) {
                System.out.println("IOException occurred when loading file");
                return;
            } catch (ClassNotFoundException exception) {
                System.out.println("Error occurred finding class while loading save file");
                return;
            }
            interactWithInputString(inputs);
        }
    }
}
