package koshechkin;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Objects;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Date;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
/**
	 * ����� ������ � �������� �������� HTTP-�������.
	 * �������������� ���������� � ������ ����� �� ��������� �� ��������� HTTP.
	 * ��� ������ ������ � HML-�����.
	 * ��� ������������� ������ � ��������� ����. 
	 * @author Vadim Koshechkin
	 * 
	 */
public class FileHandler {
	private final Path resourcePath;
	private final File xmlPath;
	private static SimpleDateFormat formatForDateNow = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z",Locale.ENGLISH);
	private HashMap<String, HashMap<Headers, String>> files = new HashMap<>();
	
	
	FileHandler(Path resourcePath){
		this.resourcePath = resourcePath;
		this.xmlPath = new File(System.getProperty("user.dir"), "src\\resurce.xml");
	}
	FileHandler(Path resourcePath,File xmlPath) throws IOException{
		this.resourcePath = resourcePath;
		if (xmlPath.exists() && xmlPath.getName().split("\\.")[1].toLowerCase().equals(".xml")) {
			this.xmlPath = xmlPath;
		}else {
			throw new IOException("XML-file missing");
		}
	}
	
	public void start() {
	//������ ���� xml, ���� �� �����������	
	if (!xmlPath.exists()) {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element root = document.createElement("rootFolder");
            root.setAttribute("Path", resourcePath.toString());

            document.appendChild(root);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult file = new StreamResult(xmlPath);
            transformer.transform(source, file);
            
            System.out.println("File created: " + xmlPath.toString());
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		}catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	
	try {
		Document root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlPath);

		goToApdateFolder(resourcePath.toFile(), root);
		
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(root);
        StreamResult file = new StreamResult(xmlPath);
        transformer.transform(source, file);
		
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
	} catch (TransformerConfigurationException e) {
		e.printStackTrace();
	} catch (TransformerFactoryConfigurationError e) {
		e.printStackTrace();
	} catch (TransformerException e) {
		e.printStackTrace();
	}
}
	/**
	 * ��������� ���������� � ������ � xml.
	 * @param folder ���������� ��� ����������� ������
	 * @param node ���� ��������� ����� 
	 * @param document �������� xml
	 * @author Vadim Koshechkin
	 */
	 void goToApdateFolder(File folder, Node node, Document document) {
		// ���������� ��� �������� � ���������� � ������������ ���������� � ������ � xml ���������� 
		for(File file : folder.listFiles()) {
			boolean exists = false;
			for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
				if (file.getName().equals(child.getNodeName().toString())) {
					exists = true;
					if (file.isFile()) {
						setHeaders(file, child);
					} else {
						// ���� ����� � xml ���������
						goToApdateFolder(new File(folder + File.separator + file.getName()), child, document);
					}
				}
			}
			
			//���� �� ����� ������� � xml, ������ �����
			if (!exists) {
		        Element newElement = document.createElement(file.getName());
		        node.appendChild(newElement);
				if (file.isFile()) {
						setHeaders(file, newElement);
					} else {
						goToApdateFolder(new File(folder + File.separator + file.getName()), newElement, document);
				}
			}
		}	
	}

	/**
	 *  ��������� ���������� � ������ � xml.
	 * @param folder ���������� ��� ����������� ������
	 * @param document �������� xml
	 */
	private void goToApdateFolder(File folder, Document document) {
		goToApdateFolder(folder, (Element) document.getFirstChild(), document);
		removeUslessNode(folder, document.getFirstChild(), document);
	}
	/**
	 * ������� ���� ��� ������� ��� ������ � ����������.
	 * @param folder ����������
	 * @param node ���� ��������� ����� 
	 * @param document �������� xml
	 * @author Vadim Koshechkin
	 */
	private void removeUslessNode(File folder, Node node, Document document) {
		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
			boolean exists = true;
			for(File file : folder.listFiles()) {
				if (file.getName().equals(child.getNodeName())) {
					if (!file.isFile()) {
						removeUslessNode(new File(folder + File.separator + file.getName()), child, document);
					}
					exists = false;
				}
			}
			if (exists) {
				//�������� ����
				node.getParentNode().removeChild(node);			
				}
		}
	}
	/**
	 * ��������� ����� ���������� ��� ������, �������� � �� ����, ��������� ���������� �� xml-����� 
	 * @param file ���� � �����
	 * @param node ����
	 */
	private void setHeaders(File file, Node node) {
		HashMap<Headers, String> headers = new HashMap<>();
		String value;
		for(Headers header : Headers.values()) {
			try {
				if (node.getAttributes() != null &&  node.getAttributes().getNamedItem(header.get()) != null) {
					value = setHeader(headers, header, node.getAttributes().getNamedItem(header.get()).toString(), file);
				} else {
					value = setHeader(headers, header, null, file);;
				}
				headers.put(header, value);
				((Element) node).setAttribute(header.get(), value);
			} catch (IOException e) {
			}
		}
		files.put("/" + resourcePath.relativize(file.toPath()).toString().replace("\\", "/"), headers);
	}
	
	private String setHeader(HashMap<Headers, String> headers, Headers header, String xmlAtribute, File file) throws IOException {
		switch (header) {
			case Content_Length:
				if (file.length() != 0) {
					return String.valueOf(file.length());
				} else {
					throw new IOException("empty file");
				}
			case Content_Type:
				return Files.probeContentType(file.toPath());
			case Last_Modified:
				return formatForDateNow.format(new Date(file.lastModified())).toString();
			default:
				throw new IOException("inappropriate title");
		}
	}
	public String getHeader(String path) {
		HashMap<Headers, String> header = files.get(path);
		String output = "";
		for (Entry<Headers, String> entry : header.entrySet()) {
			output = output + entry.getKey().get() + ": "  + entry.getValue() + "\n";
		}
		output = output + "\n";
		return output;
	}
}
