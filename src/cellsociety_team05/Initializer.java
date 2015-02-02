package cellsociety_team05;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.InputSource;
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
	public void readXML(String s) throws SAXException, FileNotFoundException {
//	public void readXML(Inputinputsource) throws SAXException, FileNotFoundException {
		XMLReader xml = XMLReaderFactory.createXMLReader();
		xml.setContentHandler(reader);
		try {
//			xml.parse("src/cellsociety_team05/example.xml");
			xml.parse(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
