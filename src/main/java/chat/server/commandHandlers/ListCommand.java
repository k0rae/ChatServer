package chat.server.commandHandlers;

import chat.server.ChatServer;
import chat.server.NetCommand;
import chat.server.User;
import chat.server.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class ListCommand implements CommandHandler {
    private final NetCommand netCommand;
    public ListCommand(NetCommand netCommand) {
        this.netCommand = netCommand;
    }
    @Override
    public CommandHandlerResponse handle() {
        User user = Utils.GetUserFromNetCommand(netCommand);
        if (user == null) return new CommandHandlerResponse(ResponseType.Error, "Wrong token");
        StringBuilder XMLResp = new StringBuilder();
        ArrayList<User> users = ChatServer.GetUsers();
        XMLResp.append("<listusers>");
        users.forEach(u -> {
            XMLResp.append("<user>");
            XMLResp.append("<name>").append(u.name).append("</name>");
            XMLResp.append("<type>").append(u.client).append("</type>");
            XMLResp.append("</user>");
        });
        XMLResp.append("</listusers>");
        return new CommandHandlerResponse(ResponseType.Success, XMLResp.toString());
    }
}
