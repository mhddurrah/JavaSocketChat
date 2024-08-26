/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m6do618.java.chat.server;

import com.m6do618.java.chat.messages.AccountLocked;
import com.m6do618.java.chat.messages.BroadcastMessage;
import com.m6do618.java.chat.messages.DirectMessage;
import com.m6do618.java.chat.messages.Login;
import com.m6do618.java.chat.messages.Logout;
import com.m6do618.java.chat.messages.Message;
import com.m6do618.java.chat.messages.OnlineRequest;
import com.m6do618.java.chat.messages.Register;
import com.m6do618.java.chat.messages.WrongPassword;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Durrah
 */
public class ClientHandler extends Thread {

  private String clientId;
  private final Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private final Server server;
  private int passwordTries = 1;

  public ClientHandler(Server server, Socket socket) {
    this.server = server;
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      in = new ObjectInputStream(socket.getInputStream());//to receive through socket form current client
      out = new ObjectOutputStream(socket.getOutputStream());// to send through socket to current client
      while (true) {
        Object message = in.readObject();
        System.out.println(message.getClass().getSimpleName());
        execute(message, in, out);
      }
    } catch (Exception ex) {
      System.out.println("Error: " + ex.getLocalizedMessage());
    }
  }

  private void execute(Object message, ObjectInputStream in, ObjectOutputStream out) throws Exception {
    if (message instanceof Register) {
      Register reg = (Register)message;
      Message response = server.register(reg);
      response(response);
      return;
    }

    if (message instanceof Login) {
      Login login = (Login)message;
      System.out.println("passwordTries: " + passwordTries);
      if (passwordTries == 3) {
        response(new AccountLocked());
        return;
      }
      Message serverLogin = server.login(login.loginID, login.loginPASS);
      if (serverLogin instanceof WrongPassword) {
        passwordTries++;
        response(serverLogin);
        return;
      }
      this.clientId = login.loginID;
      server.loggedIn(clientId, this.out);
      response(serverLogin);
      server.updateOnline(clientId);
      return;
    }
    if (message instanceof OnlineRequest) {
      response(server.onlineClients(clientId));
      return;
    }

    if (message instanceof DirectMessage) {
      DirectMessage msg = (DirectMessage)message;
      System.out.println("Message from: " + clientId + " To: " + msg.to);
      server.send(clientId, msg);
      return;
    }

    if (message instanceof BroadcastMessage) {
      BroadcastMessage msg = (BroadcastMessage)message;
      server.broadcast(clientId, msg);
      return;
    }

    if (message instanceof Logout) {
      server.logout(clientId);
      response((Logout)message);
    }
  }

  void response(Message response) throws IOException {
    out.writeObject(response);
    out.flush();
  }

}
