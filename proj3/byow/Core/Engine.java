package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private boolean off = false;

    private StringBuilder totalInputs;
    private StringBuilder seedInputs;

    private String tip = "Tip: Click on a tile!";

    private boolean dark = false;
    private boolean saved = false;

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
                    StdDraw.setFont(new Font("Monaco", Font.BOLD, 64));
                    StdDraw.text(midWidth, HEIGHT - 10, "NEW GAME");
                    StdDraw.setFont(new Font("Monaco", Font.BOLD, 24));
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
                    StdDraw.setFont(new Font("Monaco", Font.BOLD, 64));
                    StdDraw.text(midWidth, HEIGHT - 10, "NEW GAME");
                    StdDraw.setFont(new Font("Monaco", Font.BOLD, 24));
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
                    StdDraw.setFont(new Font("Monaco", Font.BOLD, 16));
                    world = new WorldGenerator(seed);
                } else if (each == 'Q') {
                    System.exit(0);
                } else if (each == 'R') {
                    replay();
                }
            }
            if (dark) {
                darkRender();
            } else {
                render();
            }
            if (play) {
                if (StdDraw.isMousePressed()) {
                    Font font = new Font("Monaco", Font.BOLD, 200);
                    StdDraw.setFont(font);
                    StdDraw.setPenColor(Color.YELLOW);

                    int x = (int) StdDraw.mouseX();
                    int y = (int) StdDraw.mouseY();
                    if (x >= 0 && x <= 99 && y >= 0 && y <= 60) {
                        TETile mouseHover;
                        if (!dark) {
                            mouseHover = world.getWorld()[x][y];
                        } else  {
                            mouseHover = world.getDark()[x][y];
                        }
                        if (mouseHover == Tileset.WALL) {
                            tip = "A stone brick wall.";
                        } else if (mouseHover == Tileset.FLOOR) {
                            tip = "A mossy stone floor. It's a little bit gross.";
                        } else if (mouseHover == Tileset.NOTHING) {
                            tip = "What's not real can't hurt you. Unless?";
                        } else if (mouseHover == Tileset.AVATAR) {
                            tip = "Hey! It's me!";
                        }
                    }
                    font = new Font("Monaco", Font.BOLD, 16);
                    StdDraw.setFont(font);
                    StdDraw.setPenColor(Color.WHITE);
                }
                if (StdDraw.hasNextKeyTyped()) {
                    moves(keyboard, false);
                }
            }
            if (off) {
                System.exit(0);
            }
        }
    }

    public void render() {
        TETile[][] frame = world.getWorld();
        ter.renderFrame(frame);
        StdDraw.setPenColor(Color.white);
        String time = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now());
        StdDraw.textLeft(90, 59, time);
        StdDraw.textLeft(90, 58, tip);
        StdDraw.show();
    }

    public void darkRender() {
        TETile[][] frame = world.getDark();
        ter.renderFrame(frame);
        StdDraw.setPenColor(Color.white);
        String time = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss").format(LocalDateTime.now());
        StdDraw.textLeft(90, 59, time);
        StdDraw.textLeft(90, 58, tip);
        StdDraw.show();
    }

    public void moves(InputSource keyboard, boolean render) {
        char each = Character.toUpperCase(keyboard.getNextKey());
        switch (each) {
            case 'W' -> {
                totalInputs.append('W');
                world.move('W');
                saved = false;
            }
            case 'A' -> {
                totalInputs.append('A');
                world.move('A');
                saved = false;
            }
            case 'S' -> {
                totalInputs.append('S');
                world.move('S');
                saved = false;
            }
            case 'D' -> {
                totalInputs.append('D');
                world.move('D');
                saved = false;
            }
            case 'P' -> dark = !dark;
            case 'O' -> {
                play = false;
                saved = false;
                openScreen = true;
                interactWithKeyboard();
            }
            case ':' -> save();
            case 'Q' -> {
                if (saved) {
                    off = true;
                    return;
                }
            }
            default -> {
            }
        }
        if (render) {
            world.draw();
            try {
                Thread.sleep(200);
            } catch (InterruptedException exception) {
                System.out.println("Interrupted Exception Occurred.");
            }
        }
    }

    public void titleScreen() {
        ter.initialize(WIDTH + 10, HEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        Font title = new Font("Monaco", Font.BOLD, 64);
        StdDraw.setFont(title);
        StdDraw.text(50, 45, "CS61B: The Game");
        Font context = new Font("Monaco", Font.BOLD, 24);
        StdDraw.setFont(context);
        StdDraw.text(50, 40, "New Game (N)");
        StdDraw.text(50, 38, "Load Game (L)");
        StdDraw.text(50, 36, "Replay Save (R)");
        StdDraw.text(50, 34, "Quit (Q)");
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
                Font font = new Font("Monaco", Font.BOLD, 16);
                StdDraw.setFont(font);
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
        saved = true;
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

    public void replay() {
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
            replayHelper(inputs);
        }
    }

    public TETile[][] replayHelper(String input) {
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
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 16));
            moves(keyboard, true);
        }
        if (world != null) {
            return world.getWorld();
        } else {
            return null;
        }
    }
    public boolean isOpenScreen() {
        return off;
    }
}
