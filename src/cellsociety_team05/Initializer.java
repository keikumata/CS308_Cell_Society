package cellsociety_team05;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import simulations.Sim;
import utility.Reader;

/**
 * 
 * @author Kei Yoshikoshi Reads the XML File and stores them in separate
 *         variables
 *
 */
public class Initializer {
	private Reader reader = new Reader();

	/**
	 * Sets up the Sim object and initiates the map
	 * 
	 * @return
	 */
	public Sim setup() {
		Sim sim = reader.returnSim();
		sim.initMap();
		return sim;
	}

	/**
	 * Reads the XML file using the reader
	 * 
	 * @param s
	 *            : The file path of the XML file
	 * @throws SAXException
	 * @throws FileNotFoundException
	 */
	public void readXML(String s) throws SAXException, FileNotFoundException {
		XMLReader xml = XMLReaderFactory.createXMLReader();
		xml.setContentHandler(reader);
		try {
			xml.parse(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}