package error;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import simulations.Schelling;
import simulations.Sim;

public class XMLNotFoundException extends FileNotFoundException {
	 // for serialization
    private static final long serialVersionUID = 1L;
    private int type = 1;
    private int length = 30;
    private int fps = 10;
    private int sides = 4;
    private List<Integer> params = new ArrayList<>();
    
	public XMLNotFoundException(String message) {
		super(String.format(message));
	}
	
	public Sim returnDefaultSim() {
		params.add(50);
		params.add(30);
		params.add(80);
		return new Schelling(type,length,fps,sides,params);
	}
	
}
