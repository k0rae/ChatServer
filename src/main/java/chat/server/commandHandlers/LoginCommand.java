package chat.server.commandHandlers;

import chat.server.ChatServer;
import chat.server.Logger;
import chat.server.NetCommand;
import chat.server.User;

import java.util.UUID;
import java.util.logging.Level;

public class LoginCommand implements CommandHandler {
    private final NetCommand netCommand;
    public LoginCommand(NetCommand netCommand) {
        this.netCommand = netCommand;
    }
    @Override
    public CommandHandlerResponse handle() {
        if (!netCommand.args().containsKey("name")) return new CommandHandlerResponse(ResponseType.Error, "You need to provide a username");
        String userName = netCommand.args().get("name");
        if (userName.isBlank() || userName.length() < 3 || userName.length() > 16) return new CommandHandlerResponse(ResponseType.Error, "Username should be between 3 and 16 characters long");
        if (ChatServer.DoesUsernameExist(userName)) return new CommandHandlerResponse(ResponseType.Error, "Client with this username already connected");
        if (ChatServer.GetUserByIP(netCommand.from()) != null) return new CommandHandlerResponse(ResponseType.Error, "You can't login twice");
        String usedClient = "unspecified";
        if (netCommand.args().containsKey("type")) usedClient = netCommand.args().get("type");
        UUID uuid = UUID.randomUUID();
        ChatServer.AddUser(new User(userName, uuid, netCommand.from(), usedClient));
        Logger.log(Level.FINE, String.format("User %s joined the chat with '%s' client", userName, usedClient));
        return new CommandHandlerResponse(ResponseType.Success, String.format("<session>%s</session>", uuid));
    }
}
