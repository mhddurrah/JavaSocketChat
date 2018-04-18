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
public class BroadcastMessage implements Message {

    public final String content;

    public BroadcastMessage(String content) {
        this.content = content;
    }

}
