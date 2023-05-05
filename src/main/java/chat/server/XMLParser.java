package chat.server;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
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
                    String argValue = argElement.getTextContent();
                    argMap.put(argName, argValue);
                }
            }
            return new NetCommand(commandName, argMap, from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
