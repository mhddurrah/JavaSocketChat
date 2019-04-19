/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import client.laf.AquaTheme;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import messages.Message;

/**
 * UI application for chat client
 *
 * @author Durrah
 */
public class ChatClientApplication extends ClientApplication{


    public static void main(String[] args) {
        // set the chosen theme
        //MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
        MetalLookAndFeel.setCurrentTheme(new AquaTheme());
        // Show displayName of the theme as window title
        new ChatClientApplication().start();
    }

}
