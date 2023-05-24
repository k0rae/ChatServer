package chat.server;

import chat.server.commandHandlers.*;

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
                Logger.log(Level.FINE, String.format("new message from %s: %s", socket.getInetAddress(), message));
                NetCommand cmd = XMLParser.parse(message, socket.getInetAddress());
                if (cmd == null) continue;
                CommandHandler handler = CommandHandlerFactory.GetByCommand(cmd);
                if (handler == null) continue;
                CommandHandlerResponse resp = handler.handle();
                if(resp.type() == ResponseType.Success) {
                    SendSuccessMessage(resp.message());
                } else {
                    SendErrorMessage(resp.message());
                }
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
        ChatServer.OnDisconnect(this);
    }
    public InetAddress GetIP() {
        return this.socket.getInetAddress();
    }
    public boolean SendSuccessMessage(String message) {
        StringBuilder xml = new StringBuilder();
        xml.append("<success>");
        xml.append(message);
        xml.append("</success>\n");
        try {
            this.out.write(xml.toString());
            this.out.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    public boolean SendErrorMessage(String message) {
        StringBuilder xml = new StringBuilder();
        xml.append("<error>");
        xml.append("<message>");
        xml.append(message);
        xml.append("</message>");
        xml.append("</error>\n");
        try {
            this.out.write(xml.toString());
            this.out.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    public boolean SendMessage(String message) {
        try {
            this.out.write(message + "\n");
            this.out.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
