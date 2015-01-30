package cellsociety_team05;

import java.io.IOException;

import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * 
 * @author Kei Yoshikoshi
 * Reads the XML File and stores them in separate variables
 *
 */
public class Initializer {
    private Reader reader = new Reader();
    
	public Sim setup(){
		Sim sim = reader.returnSim();
		sim.initMap();
		return sim;
	}
	
	public void readXML() throws SAXException {
		XMLReader xml = XMLReaderFactory.createXMLReader();
		xml.setContentHandler(reader);
		try {
			xml.parse("src/cellsociety_team05/example.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
