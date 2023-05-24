package chat.server.commandHandlers;

import chat.server.ChatServer;
import chat.server.NetCommand;
import chat.server.User;
import chat.server.Utils;

import java.util.HashMap;
import java.util.UUID;

public class MessageCommand implements CommandHandler {
    private final NetCommand netCommand;
    public MessageCommand(NetCommand netCommand) {
        this.netCommand = netCommand;
    }

    @Override
    public CommandHandlerResponse handle() {
        User user = Utils.GetUserFromNetCommand(netCommand);
        if (user == null) return new CommandHandlerResponse(ResponseType.Error, "Wrong token");
        if (!netCommand.args().containsKey("message") || netCommand.args().get("message").isBlank()) return new CommandHandlerResponse(ResponseType.Error, "Empty message or no");
        String message = netCommand.args().get("message");
        HashMap<String, String> args = new HashMap<>();
        args.put("message", message);
        args.put("name", user.name);
        ChatServer.SendToAllClients(Utils.generateXML("message", args));
        return new CommandHandlerResponse(ResponseType.Success, "");
    }
}
