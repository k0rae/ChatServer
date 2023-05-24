package chat.server.commandHandlers;

import chat.server.NetCommand;

import java.util.HashMap;
import java.util.function.Function;

public class CommandHandlerFactory {
    private static final HashMap<String, Function<NetCommand, CommandHandler>> handlers = new HashMap<>();

    public static void Register() {
        handlers.put("login", LoginCommand::new);
        handlers.put("list", ListCommand::new);
        handlers.put("logout", LogoutCommand::new);
        handlers.put("message", MessageCommand::new);
    }
    public static CommandHandler GetByCommand(NetCommand command) {
        if (!handlers.containsKey(command.command())) return null;
        return handlers.get(command.command()).apply(command);
    }
}
