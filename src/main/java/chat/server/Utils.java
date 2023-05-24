package chat.server;

import chat.server.ChatServer;
import chat.server.NetCommand;
import chat.server.User;

import java.util.Map;
import java.util.UUID;

public class Utils {
    public static User GetUserFromNetCommand(NetCommand netCommand) {
        if (!netCommand.args().containsKey("session")) {
            return null;
        }
        try {
            UUID uuid = UUID.fromString(netCommand.args().get("session"));
            return ChatServer.GetUserByUUID(uuid);
        } catch (Exception e) {
            return null;
        }
    }
    public static String generateXML(String eventName, Map<String, String> args) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<event name=\"").append(eventName.toLowerCase()).append("\">");

        for (Map.Entry<String, String> entry : args.entrySet()) {
            xmlBuilder.append("<").append(entry.getKey()).append(">");
            xmlBuilder.append(entry.getValue());
            xmlBuilder.append("</").append(entry.getKey()).append(">");
        }

        xmlBuilder.append("</event>");
        return xmlBuilder.toString();
    }
}
