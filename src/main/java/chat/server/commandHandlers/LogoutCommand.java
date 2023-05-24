package chat.server.commandHandlers;

import chat.server.ChatServer;
import chat.server.NetCommand;
import chat.server.User;
import chat.server.Utils;

public class LogoutCommand implements CommandHandler {
    private final NetCommand netCommand;
    public LogoutCommand(NetCommand netCommand) {
        this.netCommand = netCommand;
    }

    @Override
    public CommandHandlerResponse handle() {
        User user = Utils.GetUserFromNetCommand(netCommand);
        if (user == null) return new CommandHandlerResponse(ResponseType.Error, "Wrong token");
        ChatServer.LogoutUser(user);
        return new CommandHandlerResponse(ResponseType.Success, "");
    }
}
