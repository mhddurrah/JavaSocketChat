/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m6do618.java.chat.messages;

/**
 * Login message
 *
 * @author Durrah
 */
public class Login implements Message {

  public final String loginID;
  public final String loginPASS;

  public Login(String loginID, String loginPASS) {
    this.loginID = loginID;
    this.loginPASS = loginPASS;
  }

}
