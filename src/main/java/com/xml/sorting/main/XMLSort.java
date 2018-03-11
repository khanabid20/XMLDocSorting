package com.xml.sorting.main;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLSort {

	static final String XML_DATA_FILE = "./xml/data.xml";
	static final String XML_OUTPUT_FILE = "./xml/data-new.xml";
	static final String XML_STYLESHEET = "./xsl/style.xsl";
	
	public static void main(String args[]) throws TransformerException{
		
	    transformXMLDoc();
	    System.out.println("Done");
	    
	}

	private static void transformXMLDoc()
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
	    Source stylesheetSource = new StreamSource(new File(XML_STYLESHEET).getAbsoluteFile());
	    Transformer transformer = factory.newTransformer(stylesheetSource);
	    Source inputSource = new StreamSource(new File(XML_DATA_FILE).getAbsoluteFile());
	    Result outputResult = new StreamResult(new File(XML_OUTPUT_FILE).getAbsoluteFile());
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
	    transformer.transform(inputSource, outputResult);
	}
}
