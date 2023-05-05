package chat.server.chatMessages;

import chat.server.User;

public class UserMessage implements ChatMessage {
    public final User from;
    public final String message;
    public UserMessage(User from, String message) {
        this.from = from;
        this.message = message;
    }
    @Override
    public String getText() {
        return message;
    }
}
