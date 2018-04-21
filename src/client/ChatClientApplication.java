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
public class ChatClientApplication {

    ChatClient client;
    UserUI userUi;

    ChatClientApplication() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void start() {
        /**
         * set up the client
         */
        client = new ChatClient(this);
        userUi = new UserUI(this);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                userUi.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // set the chosen theme
        //MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
        MetalLookAndFeel.setCurrentTheme(new AquaTheme());
        // Show name of the theme as window title
        new ChatClientApplication().start();
    }

    /**
     * delegate the message process to the UI
     *
     * @param message
     */
    void process(Message message) {
        userUi.process(message);
    }
}
