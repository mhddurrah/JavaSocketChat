package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import messages.BroadcastMessage;
import messages.DirectMessage;
import messages.DoRegister;
import messages.LoginOk;
import messages.Message;
import messages.OnlineResponse;
import messages.Register;
import messages.RegisterOk;
import messages.WrongPassword;

/**
 *
 * @author Durrah
 */
public class Server {

    class Client {

        public final String username;
        public final String name;
        public final String password;

        public Client(String username, String name, String password) {
            this.username = username;
            this.name = name;
            this.password = password;
        }

    }

    private static final int PORT = 9001;
    private final Map<String, Client> registeredClients = new ConcurrentHashMap<>();
    private final Map<String, Client> loggedClients = new ConcurrentHashMap<>();
    public final Map<String, ObjectOutputStream> writeHandles = new ConcurrentHashMap<>();

    public Server() {
    }

    public Message register(Register reg) {
        if (!reg.passwordMatches()) {
            return new WrongPassword();
        }
        String username = reg.name;
        Client client = new Client(username, reg.name, reg.pass);
        registeredClients.put(username, client);
        return new RegisterOk(reg.name, username);
    }

    public Message login(String username, String password) {
        if (!registeredClients.containsKey(username)) {
            return new DoRegister();
        }
        String savedPassword = registeredClients.get(username).password;

        if (!password.equals(savedPassword)) {
            return new WrongPassword();
        }

        loggedClients.put(username, registeredClients.get(username));
        return new LoginOk(registeredClients.get(username).name);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        try (ServerSocket listener = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(server, listener.accept()).start();
            }
        }
    }

    Message onlineClients(String except) {
        OnlineResponse online = new OnlineResponse();
        for (Map.Entry<String, Client> entry : loggedClients.entrySet()) {
            if (!entry.getKey().equals(except)) {
                online.clients.put(entry.getKey(), entry.getValue().name);
            }
        }
        return online;
    }

    void send(String from, DirectMessage msg) throws Exception {
        System.out.println("from: " + from + " to: " + msg.to);
        ObjectOutputStream targetOut = writeHandles.get(msg.to);
        DirectMessage toSend = new DirectMessage(from, msg.content);
        targetOut.writeObject(toSend);
        targetOut.flush();
    }

    void logout(String clientId) throws Exception {
        loggedClients.remove(clientId);
        updateOnline(clientId);
    }

    void broadcast(String from, BroadcastMessage msg) throws Exception {
        DirectMessage toSend = new DirectMessage(from, msg.content);
        for (String clientHandle : writeHandles.keySet()) {
            if (!clientHandle.equals(from)) {
                writeHandles.get(clientHandle).writeObject(toSend);
                writeHandles.get(clientHandle).flush();
            }
        }
    }

    void writeClient(String clientId, Message message) throws Exception {
        ObjectOutputStream targetOut = writeHandles.get(clientId);
        targetOut.writeObject(message);
        targetOut.flush();
    }

    void writeAll(Message message) throws Exception {
        for (String clientHandle : writeHandles.keySet()) {
            writeHandles.get(clientHandle).writeObject(message);
            writeHandles.get(clientHandle).flush();
        }
    }

    void writeAllExceptSender(String sender, Message message) throws Exception {
        for (String clientHandle : writeHandles.keySet()) {
            if (!clientHandle.equals(sender)) {
                writeHandles.get(clientHandle).writeObject(message);
                writeHandles.get(clientHandle).flush();
            }
        }
    }

    void loggedIn(String clientId, ObjectOutputStream out) {
        this.writeHandles.put(clientId, out);
    }

    void updateOnline(String clientId) throws Exception {
        for (String client : loggedClients.keySet()) {
            if (!clientId.equals(client)) {
                Message online = this.onlineClients(client);
                writeClient(client, online);
            }
        }
    }
}
