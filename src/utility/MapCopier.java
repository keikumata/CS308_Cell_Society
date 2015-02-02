package utility;

import java.util.Arrays;

public class MapCopier {
    public static int[][] copyOfArray(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original.length);
        }
        return copy;
    }
}
