package view;

import model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends javax.swing.JFrame {
    /*
        public ChatWindow() {
            initComponents();
        }

        private void initComponents() {

            //menu manipulation (scroll bars, etc...)
            jFrame1 = new javax.swing.JFrame();
            jFrame2 = new javax.swing.JFrame();
            jScrollBar1 = new javax.swing.JScrollBar();
            jScrollPane1 = new javax.swing.JScrollPane();
            jScrollPane2 = new javax.swing.JScrollPane();
            jScrollPane5 = new javax.swing.JScrollPane();
            jScrollPane6 = new javax.swing.JScrollPane();
            jTextArea1 = new javax.swing.JTextArea();
            jMenuBar1 = new javax.swing.JMenuBar();
            // top right options
            fileMenu = new javax.swing.JMenu();
            editMenu = new javax.swing.JMenu();
            jMenu1 = new javax.swing.JMenu();
            // message stuff
            messageDisplay = new javax.swing.JTextArea();
            messageBar = new javax.swing.JTextPane();
            sendButton = new javax.swing.JButton();
            // member stuff
            memberLabel = new javax.swing.JLabel();
            memberList = new javax.swing.JTextArea();
            // it's the chat log
            chatLogName = new javax.swing.JLabel();
            //pin stuff
            pinField = new javax.swing.JPasswordField();
            setPinButton = new javax.swing.JButton();

            javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
            jFrame1.getContentPane().setLayout(jFrame1Layout);
            jFrame1Layout.setHorizontalGroup(
                    jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 400, Short.MAX_VALUE)
            );
            jFrame1Layout.setVerticalGroup(
                    jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 300, Short.MAX_VALUE)
            );

            javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
            jFrame2.getContentPane().setLayout(jFrame2Layout);
            jFrame2Layout.setHorizontalGroup(
                    jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 400, Short.MAX_VALUE)
            );
            jFrame2Layout.setVerticalGroup(
                    jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 300, Short.MAX_VALUE)
            );

            jTextArea1.setColumns(20);
            jTextArea1.setRows(5);
            jScrollPane5.setViewportView(jTextArea1);

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            sendButton.setText("Send");
            sendButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    sendButtonActionPerformed(evt);
                }
            });

            jScrollPane1.setViewportView(messageBar);

            messageDisplay.setColumns(20);
            messageDisplay.setRows(5);
            jScrollPane2.setViewportView(messageDisplay);

            memberLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            memberLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            memberLabel.setText("Members");
            memberLabel.setAlignmentY(0.0F);

            chatLogName.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
            chatLogName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            chatLogName.setText("chatlogname");
            chatLogName.setAlignmentY(0.0F);

            jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            memberList.setColumns(20);
            memberList.setRows(5);
            jScrollPane6.setViewportView(memberList);

            pinField.setText("");

            setPinButton.setText("Set Key");

            fileMenu.setText("File");
            jMenuBar1.add(fileMenu);

            editMenu.setText("Edit");
            jMenuBar1.add(editMenu);

            jMenu1.setText("balls");
            jMenuBar1.add(jMenu1);

            setJMenuBar(jMenuBar1);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(memberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addComponent(pinField, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(setPinButton))
                                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 934, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(sendButton))
                                            .addComponent(chatLogName, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jScrollPane2)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap())
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(chatLogName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(memberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                                                                    .addGap(18, 18, 18))
                                                            .addGroup(layout.createSequentialGroup()
                                                                    .addGap(113, 113, 113)
                                                                    .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(sendButton)
                                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(pinField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(setPinButton))))
                                    .addContainerGap())
            );

            pack();
        }// </editor-fold>
    /*
        private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {

        }

        /**
         * @param args the command line arguments
         */
    /*
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
    /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
    /*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel chatLogName;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel memberLabel;
    private javax.swing.JTextArea memberList;
    private javax.swing.JTextPane messageBar;
    private javax.swing.JTextArea messageDisplay;
    private javax.swing.JPasswordField pinField;
    private javax.swing.JButton sendButton;
    private javax.swing.JButton setPinButton;    // End of variables declaration

    // get the password field and set it to a public string
*/
}
