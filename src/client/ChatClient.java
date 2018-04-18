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
    ChatClientController ui;
    public volatile boolean disconnected = false;

    public ChatClient(ChatClientController ui) {
        this.ui = ui;
    }

    public void connect() {
        try {
            int port = 9001;//server port to connect to server
            Socket soc = new Socket("localhost", port);//create socket with server
            System.out.println("Client Connected to server on port: " + soc.getLocalPort());
            oos = new ObjectOutputStream(soc.getOutputStream());//to send object
            ois = new ObjectInputStream(soc.getInputStream());//to recive object
            connected = true;
            listener = new Thread(new Listener());
            listener.start();
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Thread listener;

    void logout() throws Exception {
        write(new Logout());
    }

    class Listener implements Runnable {

        @Override
        public void run() {
            while (true) {
                if(disconnected)break;
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

    public void intiClient(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void login(String loginID, String loginPASS) throws Exception {
        if (!connected) {
            connect();
        }
        write(new Login(loginID, loginPASS));
    }

    void register(String name, String pass, String passConfirm) throws Exception {
        if (!connected) {
            connect();
        }
        write(new Register(name, pass, passConfirm));

    }

    void getOnline() throws Exception {
        write(new OnlineRequest());

    }

    void sendMessage(String selectedUser, String text) throws Exception {
        write(new DirectMessage(selectedUser, text));
    }

    void broadcast(String text) throws Exception {
        write(new BroadcastMessage(text));
    }

    void write(Message message) throws Exception {
        oos.writeObject(message);
        oos.flush();
    }

}
