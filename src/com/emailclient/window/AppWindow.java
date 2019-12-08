package com.emailclient.window;

import com.emailclient.email.PlainTextEmail;
import com.emailclient.email.User;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AppWindow extends JFrame {
    private LoginDialog loginDialog;
//    private ConnSettingsDialog connSettingsDialog;
//    private JButton settingsButton;
    private PlainTextEmail plainTextEmail;
    private JPanel panel;

    private JButton loginButton;
    private JButton sendButton;
    private JButton cancelButton;

    private JLabel fromLabel;
    private JLabel toLabel;
    private JLabel subjectLabel;
    private JLabel messageLabel;

    private JTextField fromTF;
    private JTextField toTF;
    private JTextField subjectTF;
    private JTextArea messageTA;

    private List<JComponent> buttons;

    private AppWindow() {
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png"));
        setIconImage(image);
        setTitle("LiteMail");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
        buttons = new ArrayList<>();

        panel = new JPanel(new GridBagLayout());
        add(panel);

        GridBagConstraints cs = new GridBagConstraints();
        cs.insets = new Insets(5, 7, 5, 7);
        cs.fill = GridBagConstraints.HORIZONTAL;

        loginButton = new JButton("Click to Log in");
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(loginButton, cs);

        fromLabel = new JLabel("From: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(fromLabel, cs);
        buttons.add(fromLabel);

        fromTF = new JTextField(20);
        fromTF.setEditable(false);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(fromTF, cs);
        buttons.add(fromTF);

        toLabel = new JLabel("To: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(toLabel, cs);
        buttons.add(toLabel);

        toTF = new JTextField();
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(toTF, cs);
        buttons.add(toTF);

        subjectLabel = new JLabel("Subject: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(subjectLabel, cs);
        buttons.add(subjectLabel);

        subjectTF = new JTextField();
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(subjectTF, cs);
        buttons.add(subjectTF);

        messageLabel = new JLabel("Message");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(messageLabel, cs);
        buttons.add(messageLabel);

        messageTA = new JTextArea(15, 35);
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 5;
        panel.add(messageTA, cs);
        buttons.add(messageTA);

        sendButton = new JButton("Send");
        cs.gridx = 3;
        cs.gridy = 6;
        cs.gridwidth = 1;
        panel.add(sendButton, cs);
        buttons.add(sendButton);

        cancelButton = new JButton("Cancel");
        cs.gridx = 4;
        cs.gridy = 6;
        cs.gridwidth = 1;
        panel.add(cancelButton, cs);
        buttons.add(cancelButton);

        cancelButton.addActionListener(e -> System.exit(0));

        for (JComponent comp : buttons) {
            comp.setVisible(false);
        }
        setVisible(true);
    }


    public static AppWindow getInstance() {
        AppWindow appWindow = new AppWindow();

        appWindow.loginButton.addActionListener(lb -> {
            appWindow.loginDialog = new LoginDialog(appWindow);
            appWindow.loginButton.setVisible(false);
            appWindow.fromTF.setText(appWindow.loginDialog.getUser().getUsername());

            for (JComponent comp : appWindow.buttons) {
                comp.setVisible(true);
            }

            appWindow.sendButton.addActionListener(f -> {
                new Thread(appWindow::sendEmail).start();
            });

            appWindow.setSize(new Dimension(500, 500));
            appWindow.pack();
            appWindow.setLocationRelativeTo(null);
        });
        return appWindow;
    }

    private void sendEmail() {
        plainTextEmail = new PlainTextEmail(
                this.loginDialog.getConnectionSettings(),
                this.toTF.getText(),
                this.subjectTF.getText(),
                this.messageTA.getText());
        try {
            plainTextEmail.sendEmail();
            JOptionPane.showMessageDialog(this, "Email sent!");
        } catch (MessagingException ex) {
            JOptionPane.showMessageDialog(this, "Unable to send message, please check the settings!\n"+ ex.getMessage());
        }
    }

}
