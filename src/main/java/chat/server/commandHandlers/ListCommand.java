package chat.server.commandHandlers;

import chat.server.ChatServer;
import chat.server.NetCommand;
import chat.server.User;

import java.util.UUID;

public class ListCommand implements CommandHandler {
    private final NetCommand netCommand;
    public ListCommand(NetCommand netCommand) {
        this.netCommand = netCommand;
    }
    @Override
    public CommandHandlerResponse handle() {
        // todo
//        if (!netCommand.args().containsKey("session")) {
//            return new CommandHandlerResponse(ResponseType.Error, "Unauthorized");
//        }
//        UUID uuid = UUID.fromString(netCommand.args().get("session"));
//        User user = ChatServer.GetUserByUUID(uuid);
//        if (user == null) return new CommandHandlerResponse(ResponseType.Error, "Wrong token");

        return null;
    }
}
