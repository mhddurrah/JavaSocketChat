/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m6do618.java.chat.messages;

import java.io.Serializable;

/**
 * Based on the ability to send serialized {@link Serializable} objects over sockets this base interface represents the
 * top level class hierarchy of all messages, events and actions sent between socket server and its clients or between
 * clients
 *
 * @author Durrah
 */
public interface Message extends Serializable {

}
