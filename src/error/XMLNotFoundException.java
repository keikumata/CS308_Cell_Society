package error;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import simulations.Schelling;
import simulations.Sim;

public class XMLNotFoundException extends FileNotFoundException {
	 // for serialization
    private static final long serialVersionUID = 1L;
    private static final int SIM_TYPE = 1;
    private static final int SIM_LENGTH = 30;
    private static final int SIM_FPS = 10;
    private static final int SIM_CELL_TYPES = 2;
    private static final int SIM_SIDES = 4;
    private static final int SIM_PARAM1 = 50;
    private static final int SIM_PARAM2 = 30;
    private static final int SIM_PARAM3 = 70;
    private List<Integer> params = new ArrayList<>();
    
	public XMLNotFoundException(String message) {
		super(String.format(message));
	}
	
	public Sim returnDefaultSim() {
		params.add(SIM_PARAM1);
		params.add(SIM_PARAM2);
		params.add(SIM_PARAM3);
		return new Schelling(SIM_TYPE,SIM_CELL_TYPES,SIM_LENGTH,SIM_FPS,SIM_SIDES,params);
	}
	
}
