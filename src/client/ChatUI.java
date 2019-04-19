/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import messages.Client;
import messages.DirectMessage;
import messages.Logout;
import messages.Message;
import messages.OnlineResponse;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Durrah
 */
public class ChatUI extends javax.swing.JFrame {

    private ClientApplication clientUi;
    private DefaultListModel<Client> onlineList;
    private String selectedUser;

    /**
     * Creates new form ChatUI
     */
    public ChatUI(ClientApplication clientUi) throws Exception {
        initComponents();
        setResizable(false);
        this.clientUi = clientUi;

        clientUi.client.getOnline();

        /**
         * set current chat for selected user
         */
        onlineClients.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                sendBtn.setEnabled(true);
                selectedUser = onlineList.get(e.getFirstIndex()).username;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        messagesPanel = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        onlineClients = new javax.swing.JList<>();
        messageField = new javax.swing.JTextField();
        sendBtn = new javax.swing.JButton();
        broadcastBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(messagesPanel);

        jScrollPane2.setViewportView(onlineClients);

        sendBtn.setText("Send");
        sendBtn.setEnabled(false);
        sendBtn.addActionListener(this::sendBtnActionPerformed);

        broadcastBtn.setText("Broadcast");
        broadcastBtn.addActionListener(this::broadcastBtnActionPerformed);

        logoutBtn.setText("Logout");
        logoutBtn.addActionListener(this::logoutBtnActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(layout.createSequentialGroup()
                                      .addContainerGap()
                                      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                      .addGroup(layout.createSequentialGroup()
                                                                      .addComponent(sendBtn,
                                                                              javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                              97,
                                                                              javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                      .addGap(73, 73, 73)
                                                                      .addComponent(broadcastBtn,
                                                                              javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                              107,
                                                                              javax.swing.GroupLayout.PREFERRED_SIZE))
                                                      .addGroup(layout.createParallelGroup(
                                                              javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                      .addComponent(messageField)
                                                                      .addComponent(jScrollPane1,
                                                                              javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                              277,
                                                                              javax.swing.GroupLayout.PREFERRED_SIZE)))
                                      .addGap(18, 18, 18)
                                      .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 122,
                                              Short.MAX_VALUE)
                                      .addContainerGap())
                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                  .addContainerGap(
                                                                                          javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                          Short.MAX_VALUE)
                                                                                  .addComponent(logoutBtn)
                                                                                  .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                  .addContainerGap()
                                                                                  .addComponent(logoutBtn)
                                                                                  .addPreferredGap(
                                                                                          javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                          javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                          Short.MAX_VALUE)
                                                                                  .addGroup(layout.createParallelGroup(
                                                                                          javax.swing.GroupLayout.Alignment.LEADING,
                                                                                          false)
                                                                                                  .addGroup(layout
                                                                                                          .createSequentialGroup()
                                                                                                          .addComponent(
                                                                                                                  jScrollPane1)
                                                                                                          .addPreferredGap(
                                                                                                                  javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                          .addComponent(
                                                                                                                  messageField,
                                                                                                                  javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                  55,
                                                                                                                  javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                  .addComponent(
                                                                                                          jScrollPane2,
                                                                                                          javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                          298,
                                                                                                          javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                  .addPreferredGap(
                                                                                          javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                  .addGroup(layout.createParallelGroup(
                                                                                          javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                  .addComponent(sendBtn)
                                                                                                  .addComponent(
                                                                                                          broadcastBtn))
                                                                                  .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed

        // Swing Worker.. offload execution from UI thread
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try {
                    String message = messageField.getText();
                    clientUi.client.sendMessage(selectedUser, message);
                    messagesPanel
                            .setText(messagesPanel.getText() + "\n" +
                                    "You :\n\t" +
                                    message + "\n________________________\n");
                    messageField.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ChatUI.this, "Error Sending Message");
                }
                return null;
            }
        };
        worker.execute();
    }//GEN-LAST:event_sendBtnActionPerformed

    private void broadcastBtnActionPerformed(
            java.awt.event.ActionEvent evt) {//GEN-FIRST:event_broadcastBtnActionPerformed
        // TODO add your handling code here:
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try {
                    clientUi.client.broadcast(messageField.getText());
                    messageField.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ChatUI.this, "Error Sending Message");
                }
                return null;
            }
        };
        worker.execute();
    }//GEN-LAST:event_broadcastBtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        try {
            clientUi.client.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_logoutBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton broadcastBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JTextField messageField;
    private javax.swing.JTextPane messagesPanel;
    private javax.swing.JList<Client> onlineClients;
    private javax.swing.JButton sendBtn;
    // End of variables declaration//GEN-END:variables

    /**
     * process event messages based on type
     *
     * @param _message
     */
    void process(Message _message) {
        if (_message instanceof Logout) {
            dispose();
            clientUi.client.disconnected = true;
        }
        /**
         * display online users on chat panel
         */
        if (_message instanceof OnlineResponse) {
            try {
                OnlineResponse online = (OnlineResponse) _message;
                onlineList = new DefaultListModel<>();
                for (String key : online.clients.keySet()) {
                    onlineList.addElement(online.clients.get(key));
                }

                onlineClients.setModel(onlineList);
                addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        dispose();
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred");
            }
        }

        if (_message instanceof DirectMessage) {
            DirectMessage message = (DirectMessage) _message;
            String from = message.to;
            int idx =
                    Collections.list(onlineList.elements()).stream().map(e -> e.username).collect(Collectors.toList())
                               .indexOf(from);
            onlineClients.setSelectedIndex(idx);
            messagesPanel.setText(messagesPanel.getText() + "\n" +
                    message.to + ":\n\t" +
                    message.content + "\n________________________\n");
        }
    }
}
