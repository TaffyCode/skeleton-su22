package gh2;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR/frequency);
        buffer = new LinkedListDeque<>();
        for (int x = 0; capacity != x; x++) {
            buffer.addFirst(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        int counter = buffer.size();
        while (!buffer.isEmpty()) {
            buffer.removeFirst();
        }
        while (counter != 0) {
            buffer.addLast(Math.random() - 0.5);
            counter -= 1;
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double removed = buffer.removeFirst();
        buffer.addLast(DECAY * (removed + buffer.get(0)) * 0.5);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        if (buffer.get(0) == null) {
            return 0.0;
        }
        else {
            return buffer.get(0);
        }
    }
}