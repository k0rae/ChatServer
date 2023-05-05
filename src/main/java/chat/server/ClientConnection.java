package chat.server;

import chat.server.commandHandlers.CommandHandlerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

public class ClientConnection extends Thread {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }
    @Override
    public void run() {
        Logger.log(Level.FINE, String.format("Client %s connected\n", socket.getInetAddress()));
        String message;
        try {
            while (true) {
                message = in.readLine();
                NetCommand cmd = XMLParser.parse(message, socket.getInetAddress());
                System.out.println(CommandHandlerFactory.GetByCommand(cmd).handle());
            }
        } catch (SocketException e) {
            disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void disconnect() {
        Logger.log(Level.FINE, String.format("Client %s disconnected\n", socket.getInetAddress()));
        try {
            socket.close();
        } catch (IOException e) {}
        ChatServer.connections.remove(this);
    }
    public InetAddress GetIP() {
        return this.socket.getInetAddress();
    }
    public void SendMessage(String message) {
        try {
            out.write(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
