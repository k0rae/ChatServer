package chat.server;

import java.net.InetAddress;
import java.util.UUID;

public class User {
    public final String name;
    public final UUID uuid;
    public final String client;
    public final InetAddress ip;
    public User(String name, UUID uuid, InetAddress ip, String client) {
        this.name = name;
        this.uuid = uuid;
        this.ip = ip;
        this.client = client;
    }
}
