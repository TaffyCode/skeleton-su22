package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
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

    private long seed;

    private WorldGenerator world;

    private boolean play = false;
    private boolean openScreen = true;
    private boolean seedScreen = false;

    private StringBuilder totalInputs;
    private StringBuilder seedInputs;

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
                if (openScreen && each == 'L') {
                    load();
                } else if (openScreen && each == 'N') {
                    openScreen = false;
                    seedScreen = true;
                    totalInputs.append(each);
                    int midWidth = WIDTH / 2;
                    int midHeight = HEIGHT / 2;

                    String input = totalInputs.toString();
                    input = input.substring(input.indexOf('N') + 1);

                    StdDraw.clear(Color.black);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 64));
                    StdDraw.text(midWidth, HEIGHT - 10, "NEW GAME");
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
                    StdDraw.text(midWidth, midHeight, "Enter any number, then press \"S\".");
                    StdDraw.setPenColor(Color.yellow);
                    StdDraw.text(midWidth, midHeight - 2, input);
                    StdDraw.show();
                } else if (seedScreen && Character.isDigit(each)) {
                    seedInputs.append(each);
                    totalInputs.append(each);

                    int midWidth = WIDTH / 2;
                    int midHeight = HEIGHT / 2;

                    String input = totalInputs.toString();
                    input = input.substring(input.indexOf('N') + 1);

                    StdDraw.clear(Color.black);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 64));
                    StdDraw.text(midWidth, HEIGHT - 10, "NEW GAME");
                    StdDraw.setFont(new Font("Arial", Font.BOLD, 24));
                    StdDraw.text(midWidth, midHeight, "Enter any number, then press \"S\".");
                    StdDraw.setPenColor(Color.yellow);
                    StdDraw.text(midWidth, midHeight - 2, input);
                    StdDraw.show();
                } else if (seedScreen && each == 'S') {
                    totalInputs.append('S');
                    if (seedInputs.length() > 18) {
                        seed = Long.parseLong(seedInputs.substring(0, 18));
                    } else {
                        seed = Long.parseLong(seedInputs.toString());
                    }
                    play = true;
                    seedScreen = false;
                    world = new WorldGenerator(seed);
                } else if (each == 'Q') {
                    System.exit(0);
                }
            }
            render();
            if (play) {
                moves(keyboard, false);
            }
        }
    }

    public void render() {
        TETile[][] frame = world.getWorld();
        ter.renderFrame(frame);
        StdDraw.show();
    }

    public void moves(InputSource keyboard, boolean render) {
        char each = Character.toUpperCase(keyboard.getNextKey());
        switch (each) {
            case 'W' -> {
                totalInputs.append('W');
                world.move('W');
            }
            case 'A' -> {
                totalInputs.append('A');
                world.move('A');
            }
            case 'S' -> {
                totalInputs.append('S');
                world.move('S');
            }
            case 'D' -> {
                totalInputs.append('D');
                world.move('D');
            }
            case ':' -> save();
            case 'Q' -> {
                return;
            }
            default -> {
            }
        }
        if (render) {
            world.draw();
        }
    }

    public void titleScreen() {
        ter.initialize(WIDTH, HEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font title = new Font("Arial", Font.BOLD, 64);
        StdDraw.setFont(title);
        StdDraw.text(50, 45, "CS61B: The Game");
        Font context = new Font("Arial", Font.BOLD, 24);
        StdDraw.setFont(context);
        StdDraw.text(50, 40, "New Game (N)");
        StdDraw.text(50, 38, "Load Game (L)");
        StdDraw.text(50, 36, "Quit (Q)");
        StdDraw.show();
        totalInputs = new StringBuilder("");
        seedInputs = new StringBuilder("");
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
        totalInputs = new StringBuilder("");
        seedInputs = new StringBuilder("");
        seed = -999;
        world = null;
        InputSource keyboard = new StringInputDevice(input);

        while (!play && keyboard.possibleNextInput()) {
            char each = Character.toUpperCase(keyboard.getNextKey());
            if (openScreen && each == 'L') {
                load();
            } else if (openScreen && each == 'N') {
                openScreen = false;
                seedScreen = true;
                totalInputs.append(each);

            } else if (seedScreen && Character.isDigit(each)) {
                seedInputs.append(each);
                totalInputs.append(each);

            } else if (seedScreen && each == 'S') {
                totalInputs.append('S');
                if (seedInputs.length() > 18) {
                    seed = Long.parseLong(seedInputs.substring(0, 18));
                } else {
                    seed = Long.parseLong(seedInputs.toString());
                }
                play = true;
                seedScreen = false;
                world = new WorldGenerator(seed);
            } else if (each == 'Q') {
                return world.getWorld();
            }
        }
        while (play && keyboard.possibleNextInput()) {
            moves(keyboard, false);
        }
        if (world != null) {
            return world.getWorld();
        } else {
            return null;
        }
    }

    public void save() {
        File save = new File("./save.txt");
        try {
            if (!save.exists()) {
                save.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(save);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(totalInputs.toString());
        } catch (FileNotFoundException exception) {
            System.out.println("File not found");
        } catch (IOException exception) {
            System.out.println("IOException occurred while saving");
        }
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
