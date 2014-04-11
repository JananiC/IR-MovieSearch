package com.irproject.search.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellResponse;
import org.xml.sax.SAXException;

import com.irproject.search.bean.NERBean;


public class ParseXMLHelper {
	

	
	public  List<NERBean> getTags(String xmlToParse) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		dbfactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder domparser = dbfactory.newDocumentBuilder();
		Document doc = domparser.parse(new File(xmlToParse));
		List<NERBean> nerBeanList = new ArrayList<NERBean>();
		
		Element rootElement = doc.getDocumentElement();
		System.out.println("===" + rootElement.getNodeName());

		System.out.println("\n\n\nRoot Elemet -->" + rootElement);
		NodeList tokenList = rootElement.getElementsByTagName("token");
		System.out.println("nodes-->" + tokenList.getLength());

		 if (tokenList != null && tokenList.getLength() > 0) {
             for (int i = 0; i < tokenList.getLength(); i++) {
            	 Node node = tokenList.item(i);
            	 if (node.getNodeType() == Node.ELEMENT_NODE) {
            		 
            		 NERBean nerBean = new NERBean();
            		 
                     System.out
                             .println("=====================");

                     Element e = (Element) node;
                     NodeList nodeList = e.getElementsByTagName("word");
                     System.out.println("word: "
                             + nodeList.item(0).getChildNodes().item(0)
                                     .getNodeValue());
                     String word =nodeList.item(0).getChildNodes().item(0)
                             .getNodeValue();

                     nodeList = e.getElementsByTagName("lemma");
                     System.out.println("lemma: "
                             + nodeList.item(0).getChildNodes().item(0)
                                     .getNodeValue());

                     nodeList = e.getElementsByTagName("NER");
                     System.out.println("NER: "
                             + nodeList.item(0).getChildNodes().item(0)
                                     .getNodeValue());
                     if(!nodeList.item(0).getChildNodes().item(0)
                             .getNodeValue().equalsIgnoreCase("O")){
                     nerBean.setNer(nodeList.item(0).getChildNodes().item(0)
                             .getNodeValue());
                     nerBean.setWord(word);
                     }
                     
                     nerBeanList.add(nerBean);
                 }
             }
         } else {
             System.out.println("error");
         }
		 return nerBeanList;
     }
	
	
	
	
}
