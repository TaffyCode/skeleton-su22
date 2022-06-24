/** A class that represents a path via pursuit curves. */
public class Path {
    Point curr;
    Point next;

    public Path(double x, double y) {
        curr = new Point(0, 0);
        next = new Point(x, y);
    }
    public double getCurrX() { return curr.getX(); }
    public double getCurrY() { return curr.getY(); }
    public double getNextX() { return next.getX(); }
    public double getNextY() { return next.getY(); }
    public Point getCurrentPoint() { return curr; }
    public void setCurrentPoint(Point point) { curr = point; }
    public void iterate(double dx, double dy) {
        curr = new Point(getNextX(), getNextY());
        next = new Point(getCurrX() + dx, getCurrY() + dy);
    }

}
