package cellsociety_team05;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Reader extends DefaultHandler {
    private String tag;
    private int type;
    private int size;
    private int fps;
    private ArrayList<Integer> paramsList = new ArrayList<>();
    
    public void startDocument() {
    }
    public void endDocument() {
    }
    public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) {
        tag = qName;
    }
    public void endElement(String namespaceURI, String localName, String qName) {
    }
    public void characters(char[] ch, int start, int length) {
        String num = new String(ch, start, length).trim();
        if (num.length()==0) {
            return;
        }
        if (tag.equals("type")) {
            type = Integer.parseInt(num);
        }
        else if (tag.equals("length")) {
            size = Integer.parseInt(num);
        }
        else if (tag.equals("fps")) {
            fps = Integer.parseInt(num);
        }
        else if (tag.equals("param")) {
            paramsList.add(Integer.parseInt(num));
        }
    }
    
    public Sim returnSim(){
        Sim sim = null;
    	switch (type) {
    	case 1:
    	    sim=new Schelling(type,size,fps,paramsList);
    	    break;
    	case 2:
    	    sim=new Fire(type,size,fps,paramsList);
    	    break;
    	case 3:
    	    sim=new Wator(type,size,fps,paramsList);
    	    break;
        case 4:
            sim=new Life(type,size,fps,paramsList);
            break;
        }
    	return sim;
	}
    
}
