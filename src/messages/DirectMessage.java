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
public class DirectMessage implements Message {

    public final String to;
    public final String content;

    public DirectMessage(String client, String content) {
        this.to = client;
        this.content = content;
    }

}
