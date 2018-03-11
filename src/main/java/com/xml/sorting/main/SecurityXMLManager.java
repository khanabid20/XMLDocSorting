package com.xml.sorting.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class SecurityXMLManager {
	static String key = "";
	static String value = "";
	static Document doc = null;
	static String[] keys;
	static String configPath = "";
	static String securityDataXML = "";
	static String securityDataXML_New = "";

	public static void main(String args[]) throws XPathExpressionException {

		SecurityXMLManager sm = new SecurityXMLManager();

		if (args.length != 0) {
			configPath = args[0];
			securityDataXML = args[1];
			securityDataXML_New = securityDataXML.replace(".xml", "_new.xml");
		} else {
			configPath = "Config.properties";
			securityDataXML = "SecurityDataXML.xml";

		}
		try {
			System.out.println("Please Wait...");
			sm.processXML(securityDataXML);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Exited");
	}

	public void processXML(String fileName) throws ParserConfigurationException, SAXException, IOException,
			TransformerException, XPathExpressionException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		doc = docBuilder.parse(fileName);
		doc.getDocumentElement().normalize();

		Properties prop = readProperties();
		Enumeration<?> enums = prop.propertyNames();

		StringTokenizer values;

		while (enums.hasMoreElements()) {
			key = (String) enums.nextElement();
			value = prop.getProperty(key);
			keys = key.split("\\.");
			values = new StringTokenizer(value, ",");
			int tokenCount = values.countTokens();
			if (doc != null) {
				NodeList nList = doc.getElementsByTagName(keys[1]);
				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					NodeList cnList = nNode.getChildNodes();
					for (int j = 0; j < cnList.getLength(); j++) {
						if (cnList.item(j).getNodeType() == 1) {
							if (cnList.item(j).getNodeName().equalsIgnoreCase("ns2:name")
									&& cnList.item(j).getTextContent().equalsIgnoreCase(keys[2]))

							{
								while (values.hasMoreTokens()) {
									passFunctionGroupValue(nNode, keys[0], values.nextElement().toString());
								}

							}
						}

					}
					sortChildNodes(nNode, 1, null);
				}
			}
		}
		printDocument(doc);
	}

	public static void printDocument(Document doc) throws IOException, TransformerException {

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "YES");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		OutputStream outputStream = new FileOutputStream(securityDataXML_New);
		transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(outputStream, "UTF-8")));

	}

	private Node passFunctionGroupValue(Node nNode, String operation, String value) {
		NodeList cnList = nNode.getChildNodes();

		Element subNode = doc.createElement("ns2:permitIds");

		for (int j = 0; j < cnList.getLength(); j++) {
			if (cnList.item(j).getNodeType() == 1) {
				if (operation.equalsIgnoreCase("ADD")
						&& cnList.item(j).getNodeName().equalsIgnoreCase("ns2:permitIds")) {
					System.out.println("Adding ... " + value);
					subNode.appendChild(doc.createTextNode(value));
					cnList.item(j).getParentNode().appendChild(subNode);
					break;
				}

				else if (operation.equalsIgnoreCase("REMOVE")
						&& cnList.item(j).getNodeName().equalsIgnoreCase("ns2:permitIds")
						&& cnList.item(j).getTextContent().equalsIgnoreCase(value))

				{
					System.out.println("Removing... " + cnList.item(j).getTextContent());
					cnList.item(j).getParentNode().removeChild(cnList.item(j));

				}
			}
		}
		return nNode;

	}

	public static Properties readProperties() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(configPath);
			if (input == null) {
				return null;
			}
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return prop;
	}

	public static void sortChildNodes(Node node, int depth, Comparator comparator) throws XPathExpressionException {

		List nodes = new ArrayList();
		NodeList childNodeList = node.getChildNodes();
		if (depth > 0 && childNodeList.getLength() > 0) {
			for (int i = 0; i < childNodeList.getLength(); i++) {
				Node tNode = childNodeList.item(i);

				if (tNode.getNodeType() == Node.ELEMENT_NODE) {

					sortChildNodes(tNode, depth - 1, comparator);
					// Remove empty text nodes
					if ((!(tNode instanceof Text))
							|| (tNode instanceof Text && ((Text) tNode).getTextContent().trim().length() > 1)) {
						nodes.add(tNode);
					}
				}
			}
			Comparator comp = (comparator != null) ? comparator : new DefaultNodeNameComparator();
			Collections.sort(nodes, comp);

			for (Iterator iter = nodes.iterator(); iter.hasNext();) {
				Node element = (Node) iter.next();
				node.appendChild(element);
			}

			XPathFactory xpathFactory = XPathFactory.newInstance();
			// XPath to find empty text nodes.
			XPathExpression xpathExp = xpathFactory.newXPath().compile("//text()[normalize-space(.) = '']");
			childNodeList = (NodeList) xpathExp.evaluate(doc, XPathConstants.NODESET);

			// Remove each empty text node from document.
			for (int i = 0; i < childNodeList.getLength(); i++) {
				Node emptyTextNode = childNodeList.item(i);
				emptyTextNode.getParentNode().removeChild(emptyTextNode);
			}
		}
	}

}

class DefaultNodeNameComparator implements Comparator {

	public int compare(Object arg0, Object arg1) {
		return ((Node) arg0).getTextContent().compareTo(((Node) arg1).getTextContent());
	}

}