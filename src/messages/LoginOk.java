/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

/**
 * if the user was authenticated successfully this response will be returned
 *
 * @author Durrah
 */
public class LoginOk implements AuthMessage {

    public final String user;

    public LoginOk(String user) {
        this.user = user;
    }

}
