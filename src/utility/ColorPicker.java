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
        default:
            break;
        }
        return stateColorMap;
    }
}
