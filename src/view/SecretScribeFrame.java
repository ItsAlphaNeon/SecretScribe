/*
 * Created by JFormDesigner on Sun Mar 05 16:49:55 MST 2023
 */

package view;

import java.awt.*;
import javax.swing.*;

import model.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Neon
 */
public class SecretScribeFrame extends javax.swing.JFrame {
    public SecretScribeFrame() {
        initComponents();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Cason Rooks
        chatFrame = new JFrame();
        memberLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        memberList = new JTextArea();
        pinField = new JPasswordField();
        setPinButton = new JButton();
        scrollPane2 = new JScrollPane();
        messageDisplay = new JTextArea();
        sendButton = new JButton();
        messageBar = new JTextField();
        serverNameLabel = new JLabel();
        decryptChatButton = new JToggleButton();

        //======== chatFrame ========
        {
            chatFrame.setTitle("SecretScribe - CURRENT_USER");
            var chatFrameContentPane = chatFrame.getContentPane();

            //---- memberLabel ----
            memberLabel.setText("Members");
            memberLabel.setFont(memberLabel.getFont().deriveFont(memberLabel.getFont().getSize() + 8f));
            memberLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //======== scrollPane1 ========
            {

                //---- memberList ----
                memberList.setText("- Member 1");
                scrollPane1.setViewportView(memberList);
            }

            //---- pinField ----
            pinField.setText("1234");

            //---- setPinButton ----
            setPinButton.setText("Change Pin");

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(messageDisplay);
            }

            //---- sendButton ----
            sendButton.setText("Send");

            //---- messageBar ----
            messageBar.setToolTipText("Enter the message to send");

            //---- serverNameLabel ----
            serverNameLabel.setText("Chat - SERVER_NAME");
            serverNameLabel.setFont(serverNameLabel.getFont().deriveFont(serverNameLabel.getFont().getSize() + 8f));
            serverNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- decryptChatButton ----
            decryptChatButton.setText("Decrypt Chat");

            GroupLayout chatFrameContentPaneLayout = new GroupLayout(chatFrameContentPane);
            chatFrameContentPane.setLayout(chatFrameContentPaneLayout);
            chatFrameContentPaneLayout.setHorizontalGroup(
                chatFrameContentPaneLayout.createParallelGroup()
                    .addGroup(chatFrameContentPaneLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(chatFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(memberLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(chatFrameContentPaneLayout.createSequentialGroup()
                                .addComponent(pinField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(setPinButton))
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(chatFrameContentPaneLayout.createParallelGroup()
                            .addGroup(chatFrameContentPaneLayout.createSequentialGroup()
                                .addComponent(messageBar, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(chatFrameContentPaneLayout.createSequentialGroup()
                                .addComponent(serverNameLabel, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                                .addGap(41, 41, 41))
                            .addGroup(chatFrameContentPaneLayout.createSequentialGroup()
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(chatFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(sendButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(decryptChatButton, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                        .addGap(9, 9, 9))
            );
            chatFrameContentPaneLayout.setVerticalGroup(
                chatFrameContentPaneLayout.createParallelGroup()
                    .addGroup(chatFrameContentPaneLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(chatFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(memberLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                            .addComponent(serverNameLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(chatFrameContentPaneLayout.createParallelGroup()
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                            .addGroup(chatFrameContentPaneLayout.createSequentialGroup()
                                .addComponent(decryptChatButton)
                                .addGap(0, 332, Short.MAX_VALUE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(chatFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(pinField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(setPinButton)
                            .addComponent(sendButton)
                            .addComponent(messageBar, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))
            );
            chatFrame.pack();
            chatFrame.setLocationRelativeTo(chatFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Cason Rooks
    private JFrame chatFrame;
    private JLabel memberLabel;
    private JScrollPane scrollPane1;
    private JTextArea memberList;
    private JPasswordField pinField;
    private JButton setPinButton;
    private JScrollPane scrollPane2;
    private JTextArea messageDisplay;
    private JButton sendButton;
    private JTextField messageBar;
    private JLabel serverNameLabel;
    private JToggleButton decryptChatButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // ANYTHING BELOW THIS LINE IS NOT PROGRAM GENERATED

    public String getPinField() {
        char[] passwordChars = pinField.getPassword();
        return new String(passwordChars);
    }

    public void CodeOnClick() {
        // check if the pin is valid when the button is clicked
        setPinButton.addActionListener(e -> {
            // if the pin is invalid, display an error message popup
            if (!controller.UserInput.isValidPin(getPinField())) {
                JOptionPane.showMessageDialog(null, "Invalid Pin", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // if the pin is valid, display a success message popup
                JOptionPane.showMessageDialog(null, "Pin Accepted", "Success", JOptionPane.INFORMATION_MESSAGE);
                /*
                // set the pin to the user's pin
                controller.SecretScribe.setPin(getPinField());
                */
            }

        });
    }

    // create a method to change the editability of the given component
    public void setButtonClickable(JButton button, boolean clickable) {
        button.setEnabled(clickable);
    }

    public void setPasswordFieldEditable(JPasswordField field, boolean editable) {
        field.setEditable(editable);
    }

    public void setTextFieldEditable(JTextField field, boolean editable) {
        field.setEditable(editable);
    }

    public JLabel getChatLogName() {
        return serverNameLabel;
    }

    public JLabel getMemberLabel() {
        return memberLabel;
    }

    public JTextArea getMemberList() {
        return memberList;
    }

    public JTextField getMessageBar() {
        return messageBar;
    }

    public JTextArea getMessageDisplay() {
        return messageDisplay;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getPinButton() {
        return setPinButton;
    }

    public JPasswordField getPinFieldReference() {
        return pinField;
    }

    public boolean getCheckPinButtonClicked() {
        return setPinButton.getModel().isPressed();
    }

    public boolean ifSendButtonClicked() {
        return sendButton.getModel().isPressed();
    }

    public String getMessageField() {
        return messageBar.getText();
    }

    public void clearMessageField() {
        messageBar.setText("");
    }

    public void createToast(String pinIsValid, String title) {
        JOptionPane.showMessageDialog(null, pinIsValid, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void addMessage(Message msg) {
        if (msg == null) {
            System.out.println("Message is null. but who gives a fuck"); // TODO: remove this
            return;
        }
        // add the message to the message display
        messageDisplay.append(msg.toString());
        // add space between messages
        messageDisplay.append(" \n");
    }

    public void clearChatWindow() {
        //clear the message display
        messageDisplay.setText("");
    }

    public AbstractButton getCheckPinButton() {
        return setPinButton;
    }

    public Component getChatFrame() {
        return chatFrame;
    }
}
