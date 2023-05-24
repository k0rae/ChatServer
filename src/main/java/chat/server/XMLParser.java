package chat.server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.HashMap;

public class XMLParser {
    public static NetCommand parse(String xmlMessage, InetAddress from){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xmlMessage));
            Document document = builder.parse(inputSource);
            Element root = document.getDocumentElement();
            String commandName = root.getAttribute("name");
            HashMap<String, String> argMap = new HashMap<>();
            NodeList argList = root.getChildNodes();

            for (int i = 0; i < argList.getLength(); i++) {
                if (argList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element argElement = (Element) argList.item(i);
                    String argName = argElement.getTagName();
                    String argValue = nodeToString(argElement);
                    argMap.put(argName, argValue);
                }
            }
            return new NetCommand(commandName, argMap, from);
        } catch (Exception e) {
            return null;
        }
    }
    private static String nodeToString(Node node) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            transformer.transform(new DOMSource(childNode), new StreamResult(writer));
        }
        return writer.toString();
    }
}
