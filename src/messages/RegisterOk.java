/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

/**
 *
 * @author durrah
 */
public class RegisterOk implements AuthMessage {

    public final String name;
    public final String username;

    public RegisterOk(String name, String username) {
        this.name = name;
        this.username = username;
    }

}
