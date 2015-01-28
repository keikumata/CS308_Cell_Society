package cellsociety_team05;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * 
 * @author Kei Yoshikoshi
 * Reads the XML File and stores them in a Map
 *
 */
public class Initializer extends DefaultHandler {
	private String tag = null;
	private int type;
	private int size;
	private int delay;
	private ArrayList<Integer> paramsList = new ArrayList<>();
	//	private Map<String, ArrayList<Integer>> myMap = new HashMap<>();

	public void startDocument() {
		System.out.println("Starting\n");
	}
	public void endDocument() {
		System.out.println("\nend parsing document..");
	}
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) {
		System.out.println("<"+qName+">");
		tag = qName;

	}
	public void endElement(String namespaceURI, String localName, String qName) {
		System.out.println("</"+qName+">");
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
		else if (tag.equals("delay")) {
			delay = Integer.parseInt(num);
		}
		else if (tag.equals("param")) {
			paramsList.add(Integer.parseInt(num));
		}
	}
	public static void main(String[] args) throws SAXException {
		XMLReader xml = XMLReaderFactory.createXMLReader();
		xml.setContentHandler(new Initializer());
		try {
			xml.parse("/Users/keiyoshikoshi/Documents/CS308/workspace/cellsociety_team05/src/cellsociety_team05/example.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public void readXML() throws SAXException {
	    	XMLReader xml = XMLReaderFactory.createXMLReader();
			xml.setContentHandler(new Initializer());
			try {
				xml.parse("/Users/keiyoshikoshi/Documents/CS308/workspace/cellsociety_team05/src/cellsociety_team05/example.xml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	public Sim setup(){
		return new Sim(type, size, delay, paramsList);
	}
}
