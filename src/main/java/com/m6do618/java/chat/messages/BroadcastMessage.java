/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m6do618.java.chat.messages;

/**
 * Broadcast message which will be sent to all connected clients
 *
 * @author Durrah
 */
public class BroadcastMessage implements Message {

  public final String content;

  public BroadcastMessage(String content) {
    this.content = content;
  }

}
