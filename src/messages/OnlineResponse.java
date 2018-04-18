/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author durrah
 */
public class OnlineResponse implements Message {

    public final Map<String, String> clients = new HashMap<>();
}
