public class TriangleDrawer {
    public static void main(String[] args) {
        int row = 0;
        int SIZE = 10;
        while (row < SIZE) {
            int col = 0;
            while (col < row) {
                col = col + 1;
                System.out.print('*');
            }
            row = row + 1;
            System.out.println('*');
        }
    }
}
