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
		return reader.returnSim();
	}
	public void readXML() throws SAXException {
		XMLReader xml = XMLReaderFactory.createXMLReader();
		xml.setContentHandler(reader);
		try {
			xml.parse("/Users/keiyoshikoshi/Documents/CS308/workspace/cellsociety_team05/src/cellsociety_team05/example.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
