public class ArrayOperations {
    /**
     * Delete the value at the given position in the argument array, shifting
     * all the subsequent elements down, and storing a 0 as the last element of
     * the array.
     */
    public static void delete(int[] values, int pos) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        for (int x = 0; x < values.length; x++) {
            if (x < pos) {
                continue;
            } else {
                if (x != values.length - 1) {
                    values[x] = values[x + 1];
                } else {
                    values[x] = 0;
                }
            }
        }
    }

    /**
     * Insert newInt at the given position in the argument array, shifting all
     * the subsequent elements up to make room for it. The last element in the
     * argument array is lost.
     */
    public static void insert(int[] values, int pos, int newInt) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        for (int x = values.length - 1; x > pos; x--) {
            values[x] = values[x - 1];
        }
        values[pos] = newInt;
    }

    /** 
     * Returns a new array consisting of the elements of A followed by the
     *  the elements of B. 
     */
    public static int[] catenate(int[] A, int[] B) {
        int[] array = new int[A.length + B.length];
        int count = 0;
        for (int x = 0; x < A.length; x++) {
            array[x] = A[x];
            count++;
        }
        for (int x = 0; x < B.length; x++) {
            array[count + x] = B[x];
        }
        return array;
    }

}