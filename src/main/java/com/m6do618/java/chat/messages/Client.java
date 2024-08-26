package com.m6do618.java.chat.messages;

import java.io.Serializable;

/**
 * @author durrah
 */
public class Client implements Serializable {
  public final String username;
  public final String displayName;

  public Client(String username, String displayName) {
    this.username = username;
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Client)) {
      return false;
    }

    return username.equals(((Client)obj).username);
  }

  @Override
  public int hashCode() {
    return username.hashCode();
  }
}
