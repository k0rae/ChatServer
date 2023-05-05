package chat.server.commandHandlers;

import chat.server.NetCommand;

public interface CommandHandler {
    CommandHandlerResponse handle();
}
