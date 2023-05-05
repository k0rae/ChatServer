package chat.server;

import java.net.InetAddress;
import java.util.HashMap;

public record NetCommand(String command, HashMap<String, String> args, InetAddress from) {}
