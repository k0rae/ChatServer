package chat.server.commandHandlers;

import chat.server.NetCommand;

import java.util.HashMap;
import java.util.function.Function;

public class CommandHandlerFactory {
    private static final HashMap<String, Function<NetCommand, CommandHandler>> handlers = new HashMap<>();

    public static void Register() {
        handlers.put("login", LoginCommand::new);
    }
    public static CommandHandler GetByCommand(NetCommand command) {
        return handlers.get(command.command()).apply(command);
    }
}
