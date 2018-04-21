package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Random;
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

    /**
     * client information
     */
    class Client {

        public final String clientId;
        public final String name;
        public final String password;
        public ObjectOutputStream writeHandle;

        public Client(String username, String name, String password) {
            this.clientId = username;
            this.name = name;
            this.password = password;
        }
    }

    private static final int PORT = 9001;
    /**
     * holds all registered clients, for advanced scenarios it can be stored in
     * database
     */
    private final Map<String, Client> registeredClients = new ConcurrentHashMap<>();
    /**
     * holds currently connected clients
     */
    private final Map<String, Client> loggedClients = new ConcurrentHashMap<>();

    public Server() {
    }

    /**
     * register user
     *
     * @param reg
     * @return
     */
    public Message register(Register reg) {
        Random random = new Random(System.currentTimeMillis());
        if (!reg.passwordMatches()) {
            return new WrongPassword();
        }
        int rand = random.nextInt(1000);
        String clientId = reg.name.replaceAll("\\s+", ".").concat(String.valueOf(rand)).toLowerCase();

        Client client = new Client(clientId, reg.name, reg.pass);
        registeredClients.put(clientId, client);
        return new RegisterOk(clientId, reg.name);
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
        ObjectOutputStream targetOut = loggedClients.get(msg.to).writeHandle;
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
        writeAllExceptSender(from, toSend);
    }

    /**
     * write message to specific client from {@link #writeHandles}
     *
     * @param clientId
     * @param message
     * @throws Exception
     */
    void writeClient(String clientId, Message message) throws Exception {
        ObjectOutputStream targetOut = loggedClients.get(clientId).writeHandle;
        targetOut.writeObject(message);
        targetOut.flush();
    }

    /**
     * write message to all clients
     *
     * @param message
     * @throws Exception
     */
    void writeAll(Message message) throws Exception {
        for (String clientHandle : loggedClients.keySet()) {
            writeClient(clientHandle, message);
        }
    }

    /**
     * write message to all clients except sender, used for logout..etc
     *
     * @param sender
     * @param message
     * @throws Exception
     */
    void writeAllExceptSender(String sender, Message message) throws Exception {
        for (String clientHandle : loggedClients.keySet()) {
            if (!clientHandle.equals(sender)) {
                writeClient(clientHandle, message);
            }
        }
    }

    /**
     * user logged in, add its write handle to connected write handles
     *
     * @param clientId
     * @param out
     */
    void loggedIn(String clientId, ObjectOutputStream out) {
        loggedClients.get(clientId).writeHandle = out;
    }

    /**
     * whenever a user logged in or out this will update online client for all
     * except sender
     *
     * @param clientId
     * @throws Exception
     */
    void updateOnline(String clientId) throws Exception {
        for (String client : loggedClients.keySet()) {
            if (!clientId.equals(client)) {
                Message online = this.onlineClients(client);
                writeClient(client, online);
            }
        }
    }
}
