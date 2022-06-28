import org.junit.Test;
import static org.junit.Assert.*;

public class MeasurementTest {
    @Test
    public void noConstructor() {
        Measurement m1 = new Measurement();
        try {
            assert (m1.feet == 0);
            assert (m1.inches == 0);
            System.out.println("true");
        } catch(Exception error) {
            System.out.println("false");
        }
    }

    @Test
    public void oneConstructor() {
        Measurement m1 = new Measurement(3);
        try {
            assert (m1.feet == 3);
            assert (m1.inches == 0);
            System.out.println("true");
        } catch (Exception error) {
            System.out.println("false");
        }
    }

    @Test
    public void twoConstructor() {
        Measurement m1 = new Measurement(3, 8);
        try {
            assert (m1.feet == 3);
            assert (m1.inches == 8);
            System.out.println("true");
        } catch (Exception error) {
            System.out.println("false");
        }
    }

    @Test
    public void plus() {
        Measurement m1 = new Measurement(3, 8);
        Measurement m2 = new Measurement (2, 9);
        Measurement m3 = m1.plus(m2);
        try {
            assert (m3.feet == 6);
            assert (m3.inches == 5);
            System.out.println("true");
        } catch (Exception error) {
            System.out.println("false");
        }
    }

    @Test
    public void minus() {
        Measurement m1 = new Measurement(3, 8);
        Measurement m2 = new Measurement (2, 9);
        Measurement m3 = m1.minus(m2);
        try {
            assert (m3.feet == 0);
            assert (m3.inches == 11);
            System.out.println("true");
        } catch (Exception error) {
            System.out.println("false");
        }
    }

    @Test
    public void times() {
        Measurement m1 = new Measurement(3, 8);
        System.out.println(m1.toString());
    }
}