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
public class Login implements Message {

    public final String loginID;
    public final String loginPASS;

    public Login(String loginID, String loginPASS) {
        this.loginID = loginID;
        this.loginPASS = loginPASS;
    }

}
