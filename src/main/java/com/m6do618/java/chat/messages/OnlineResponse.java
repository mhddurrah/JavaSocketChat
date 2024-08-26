/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m6do618.java.chat.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * the request of {@link OnlineRequest} contains map of clients (id, displayName)
 *
 * @author Durrah
 */
public class OnlineResponse implements Message {

  public final Map<String, Client> clients = new HashMap<>();
}
