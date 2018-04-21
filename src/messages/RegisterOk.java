/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

/**
 * register response of {@link Register}
 *
 * @author Durrah
 */
public class RegisterOk implements AuthMessage {

    public final String clientId;
    public final String clientName;

    public RegisterOk(String name, String username) {
        this.clientId = name;
        this.clientName = username;
    }

}
