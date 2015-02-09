package utility;

import java.util.Random;

public class RandomRange {
    public static int randInt(int min, int max, Random rand) {
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
