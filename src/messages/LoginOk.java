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
public class LoginOk implements AuthMessage {

    public final String user;

    public LoginOk(String user) {
        this.user = user;
    }

}
