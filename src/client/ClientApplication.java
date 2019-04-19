package client;

import messages.Message;

import javax.swing.*;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

/**
 * @author durrah
 */
public class ClientApplication {

    protected ChatClient client;
    protected UserUI userUi;

    ClientApplication() {
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

    /**
     * delegate the message process to the UI
     *
     * @param message
     */
    void process(Message message) {
        userUi.process(message);
    }
}
