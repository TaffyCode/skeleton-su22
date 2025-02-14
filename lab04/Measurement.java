/**
 * A class that represents a measurement in feet and inches. The measurement
 * should be _well-formed_, that is, the number of inches should not be >= 12.
 */
public class Measurement {

    public int feet;
    public int inches;

    public Measurement() {
        feet = 0;
        inches = 0;
    }

    /**
     * Constructor: takes a number of feet as its single argument, using 0 as
     * the number of inches
     */
    public Measurement(int feet) {
        this.feet = feet;
        this.inches = 0;
    }

    /**
     * Constructor: takes the number of feet in the measurement and the number
     * of inches as arguments (in that order), and does the appropriate
     * initialization
     */
    public Measurement(int feet, int inches) {
        this.feet = feet;
        this.inches = inches;
    }

    /**
     * Returns the number of feet in this Measurement. For example, if the
     * Measurement has 1 foot and 6 inches, this method should return 1.
     */
    public int getFeet() { return this.feet; }

    /**
     * Returns the number of inches in this Measurement. For example, if the
     * Measurement has 1 foot and 6 inches, this method should return 6.
     */
    public int getInches() {
        return this.inches;
    }

    /**
     * Adds the argument m2 to the current measurement.
     *
     * @param m2 - Measurement to add. Should not change.
     * @return a new Measurement containing the sum of this and m2.
     */
    public Measurement plus(Measurement m2) {
        int new_inches = this.inches + m2.getInches();
        int new_feet = this.feet + m2.getFeet();
        while (new_inches >= 12) {
            new_feet += 1;
            new_inches -= 12;
        }
        return new Measurement(new_feet, new_inches);
    }

    /**
     * Subtracts the argument m2 from the current measurement. You may assume
     * that m2 will always be smaller than the current measurement.
     *
     * @param m2 - Measurement to subtract. Should not change.
     * @return a new Measurement containing the difference of this and m2.
     */
    public Measurement minus(Measurement m2) {
        int new_inches = 0;
        int new_feet = this.feet - m2.getFeet();
        if (this.inches < m2.getInches()) {
            new_feet--;
            new_inches += 12;
        }
        new_inches = new_inches + this.inches - m2.getInches();
        return new Measurement(new_feet, new_inches);
    }

    /**
     * Returns a new Measurement that is the current measurement multiplied by
     * n. For example, if this object represents a measurement of 7 inches,
     * this.multiple(3) should return an object that represents 1 foot,
     * 9 inches (which totals to 21 inches).
     *
     * The current measurement should not change.
     *
     * @param multipleAmount
     * @return a new Measurement containing this times multipleAmount
     */
    public Measurement multiple(int multipleAmount) {
        int new_inches = this.inches * multipleAmount;
        int new_feet = this.feet * multipleAmount;
        while (new_inches >= 12) {
            new_feet++;
            new_inches -= 12;
        }
        return new Measurement (new_feet, new_inches);
    }

    /**
     * Returns the String representation of this object in the form:
     *      f'i"
     * In other words, th number of feet followed by a single quote followed
     * by the number of inches less than 12 followed by a double quote (with no
     * blanks).
     *
     * For example, 0 foot 2 inches is formatted as 0'2"
     */
    @Override
    public String toString() {
        return this.feet + "\'" + this.inches + "\"";
    }

}