package chat.server.chatMessages;

public class ServerMessage implements ChatMessage {
    public final String message;
    public ServerMessage(String message) {
        this.message = message;
    }
    @Override
    public String getText() {
        return message;
    }
}
