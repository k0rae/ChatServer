package chat.server.commandHandlers;

import chat.server.ChatServer;
import chat.server.NetCommand;
import chat.server.User;

import java.util.UUID;

public class LoginCommand implements CommandHandler {
    private final NetCommand netCommand;
    public LoginCommand(NetCommand netCommand) {
        this.netCommand = netCommand;
    }
    @Override
    public CommandHandlerResponse handle() {
        if (!netCommand.args().containsKey("name")) return new CommandHandlerResponse(ResponseType.Error, "You need to provide a username");
        String userName = netCommand.args().get("name");
        if (ChatServer.DoesUsernameExist(userName)) return new CommandHandlerResponse(ResponseType.Error, "Client with this username already connected");
        UUID uuid = UUID.randomUUID();
        ChatServer.AddUser(new User(userName, uuid, netCommand.from()));
        return new CommandHandlerResponse(ResponseType.Success, String.format("<session>%s</session>", uuid));
    }
}
