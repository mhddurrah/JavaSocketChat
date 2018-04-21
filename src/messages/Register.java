/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

/**
 * Client register request
 *
 * @author Durrah
 */
public class Register implements Message {

    public final String name;
    public final String pass;
    public final String passConfirm;

    public Register(String name, String pass, String passConfirm) {
        this.name = name;
        this.pass = pass;
        this.passConfirm = passConfirm;
    }

    public boolean passwordMatches() {
        return pass.equals(passConfirm);
    }

}
