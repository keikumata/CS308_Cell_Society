package utility;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class ColorPicker {
    public static HashMap<Integer, Color> setColors(int type) {
        HashMap<Integer, Color> stateColorMap = new HashMap<>();
        switch(type) {
        case 1:
            stateColorMap.put(0, Color.WHITE);
            stateColorMap.put(1, Color.BLUE);
            stateColorMap.put(2, Color.RED);
            break;
        case 2:
            stateColorMap.put(0, Color.FORESTGREEN);
            stateColorMap.put(1, Color.TOMATO);
            stateColorMap.put(2, Color.YELLOW);
            break;
        case 3:
            stateColorMap.put(0, Color.SKYBLUE);
            stateColorMap.put(1, Color.LIMEGREEN);
            stateColorMap.put(2, Color.RED);
            break;
        case 4:
            stateColorMap.put(0, Color.WHITE);
            stateColorMap.put(1, Color.BLACK);
            break;
        case 5:
        	stateColorMap.put(0, Color.BLUE);
        	stateColorMap.put(1, Color.GREEN);
            break;
        case 6:
            stateColorMap.put(0, Color.GREEN);
            stateColorMap.put(1, Color.BURLYWOOD);
            stateColorMap.put(2, Color.WHITE);
            stateColorMap.put(3, Color.BLACK);
            break;
        case 7:
            stateColorMap.put(0, Color.WHITE);
            stateColorMap.put(1, Color.BISQUE);
            stateColorMap.put(2, Color.LIGHTSALMON);
            stateColorMap.put(3, Color.SALMON);
            stateColorMap.put(4, Color.RED);
            stateColorMap.put(5, Color.BLUE);
            break;
        default:
            break;
        }
        return stateColorMap;
    }
}
