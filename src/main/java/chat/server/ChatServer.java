package chat.server;

import chat.server.chatMessages.ChatMessage;
import chat.server.commandHandlers.CommandHandlerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public class ChatServer extends Thread {
    public static final LinkedList<ClientConnection> connections = new LinkedList<>();
    private static final ArrayList<ChatMessage> messages = new ArrayList<>();
    private static final ArrayList<User> users = new ArrayList<>();
    private static final int MAX_CLIENTS = 16;
    private static final int PORT = 1234;
    private boolean IsIPConnected(InetAddress ip) {
        AtomicBoolean found = new AtomicBoolean(false);
        connections.forEach(clientConnection -> { if (clientConnection.GetIP().equals(ip)) found.set(true); });
        return found.get();
    }
    public static boolean DoesUsernameExist(String userName) {
        AtomicBoolean found = new AtomicBoolean(false);
        users.forEach(user -> { if (user.name.equals(userName)) found.set(true); });
        return found.get();
    }
    public static void AddUser(User user) {
        users.add(user);
    }
    public static User GetUserByUUID(UUID uuid) {
        AtomicReference<User> found = null;
        users.forEach(user -> { if (user.uuid == uuid) found.set(user); });
        return found.get();
    }
    public static User GetUserByIP(InetAddress ip) {
        AtomicReference<User> found = null;
        users.forEach(user -> { if (user.ip == ip) found.set(user); });
        return found.get();
    }
    @Override
    public void run()  {
        CommandHandlerFactory.Register();
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket socket;
                boolean shouldConnect = true;
                if (connections.size() == MAX_CLIENTS) {
                    continue;
                }
                try {
                    socket = server.accept();
                    if (IsIPConnected(socket.getInetAddress())) {
                        socket.close();
                        Logger.log(Level.WARNING, String.format("Client %s already connected, closing the new socket\n", socket.getInetAddress()));
                        shouldConnect = false;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (shouldConnect) connections.add(new ClientConnection(socket));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
