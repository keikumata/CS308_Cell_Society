package cellsociety_team05;

import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * 
 * @author Kei Yoshikoshi
 * Reads the XML File and stores them in separate variables
 *
 */
public class Initializer {
	private String tag = null;
	public int type;
	public int size;
	public int delay;
	public ArrayList<Integer> paramsList = new ArrayList<>();
    private Reader reader = new Reader();


	public Sim setup(){
		System.out.println(reader.paramsList);
		return new Sim(type, size, delay, paramsList);
	}
	public void readXML() throws SAXException {
		XMLReader xml = XMLReaderFactory.createXMLReader();
		xml.setContentHandler(reader);
		try {
			xml.parse("test.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) throws SAXException {
//		XMLReader xml = XMLReaderFactory.createXMLReader();
//		Initializer init = new Initializer();
//		xml.setContentHandler(new Initializer());
//		try {
//			xml.parse("/Users/keiyoshikoshi/Documents/CS308/workspace/cellsociety_team05/src/cellsociety_team05/example.xml");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
