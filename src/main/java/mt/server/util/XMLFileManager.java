package mt.server.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import mt.Order;

public class XMLFileManager {

	public static void write(Order order) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = null;
			Element rootElement = null;

			File inputFile = new File("MicroTraderPersistence.xml");
			if (inputFile.exists()) {
				doc = docBuilder.parse(inputFile);
				rootElement = doc.getDocumentElement();
			} else {
				doc = docBuilder.newDocument();
				rootElement = doc.createElement("XML");
				doc.appendChild(rootElement);
			}

			// order element
			Element newOrder = doc.createElement("Order");
			rootElement.appendChild(newOrder);

			// set attribute to order element
			newOrder.setAttribute("Id", String.valueOf(order.getServerOrderID()));
			newOrder.setAttribute("Type", order.isBuyOrder() ? "Buy" : "Sell");
			newOrder.setAttribute("Stock", order.getStock());
			newOrder.setAttribute("Units", String.valueOf(order.getNumberOfUnits()));
			newOrder.setAttribute("Price", String.valueOf(order.getPricePerUnit()));

			Element customer = doc.createElement("Customer");
			customer.setTextContent(order.getNickname());
			newOrder.appendChild(customer);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(inputFile);

			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}