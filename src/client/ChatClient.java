/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import messages.Login;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.BroadcastMessage;
import messages.DirectMessage;
import messages.Logout;
import messages.Message;
import messages.OnlineRequest;
import messages.Register;

/**
 *
 * @author Durrah
 */
public class ChatClient {

    String name;
    String id;
    String password;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    boolean connected;
    public Map<String, String> onlineUsers;
    ChatClientApplication ui;
    public volatile boolean disconnected = false;
    Thread eventLoop;

    public ChatClient(ChatClientApplication ui) {
        this.ui = ui;
    }

    /**
     * connect to socket server and start listening to message streams
     */
    public void connect() {
        try {
            int port = 9001;//server port to connect to server
            Socket soc = new Socket("localhost", port);//create socket with server
            System.out.println("Client Connected to server on port: " + soc.getLocalPort());
            oos = new ObjectOutputStream(soc.getOutputStream());//to send object
            ois = new ObjectInputStream(soc.getInputStream());//to recive object
            connected = true;
            eventLoop = new Thread(new Listener());
            eventLoop.start();
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void logout() throws Exception {
        write(new Logout());
    }

    class Listener implements Runnable {

        @Override
        public void run() {
            while (!disconnected) {
                try {
                    if (ois != null) {
                        Object object = ois.readObject();
                        Message message = (Message) object;
                        ui.process(message);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ChatUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public boolean isConnected() {
        return connected;
    }

    /**
     * login to socket server, connect if necessary
     *
     * @param loginID
     * @param loginPASS
     * @throws Exception
     */
    public void login(String loginID, String loginPASS) throws Exception {
        if (!connected) {
            connect();
        }
        write(new Login(loginID, loginPASS));
    }

    /**
     * Register to socket server, connect if necessary
     *
     * @param name
     * @param pass
     * @param passConfirm
     * @throws Exception
     */
    void register(String name, String pass, String passConfirm) throws Exception {
        if (!connected) {
            connect();
        }
        write(new Register(name, pass, passConfirm));

    }

    /**
     * get online users
     *
     * @throws Exception
     */
    void getOnline() throws Exception {
        write(new OnlineRequest());
    }

    /**
     * send a message to specific user
     *
     * @param selectedUser
     * @param text
     * @throws Exception
     */
    void sendMessage(String selectedUser, String text) throws Exception {
        write(new DirectMessage(selectedUser, text));
    }

    /**
     * broadcast message
     *
     * @param text
     * @throws Exception
     */
    void broadcast(String text) throws Exception {
        write(new BroadcastMessage(text));
    }

    /**
     * used in all client send events, write message to socket output stream
     *
     * @param message
     * @throws Exception
     */
    void write(Message message) throws Exception {
        oos.writeObject(message);
        oos.flush();
    }

}
