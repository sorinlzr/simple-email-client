package com.emailclient.window;

import com.emailclient.email.ConnectionSettings;
import com.emailclient.email.User;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginDialog extends JDialog {
    private ConnSettingsDialog connSettingsDialog;
    private JTextField usernameTF;
    private JPasswordField passwordTF;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton cancelButton;
    private JButton settingsButton;
    private User user;
    private ConnectionSettings connectionSettings;
    private Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,7}");

    public LoginDialog (JFrame parent){
        super(parent, "Login", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        usernameLabel = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(usernameLabel, cs);

        usernameTF = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(usernameTF, cs);

        passwordLabel = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(passwordLabel, cs);

        passwordTF = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(passwordTF, cs);
        panel.setBorder(new LineBorder(Color.GRAY));

        loginButton = new JButton("Login");
        loginButton.addActionListener(e-> ok());

        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e-> settings());

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e->cancel());


        JPanel buttons = new JPanel();
        buttons.add(settingsButton);
        buttons.add(loginButton);
        buttons.add(cancelButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttons,BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    protected User getUser(){
        return this.user;
    }

    protected ConnectionSettings getConnectionSettings(){
        return this.connectionSettings;
    }

    private boolean isValidEmail(String email) {
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }

    private void ok(){
        if(isValidEmail(this.usernameTF.getText())){
            this.user = new User(this.usernameTF.getText(), new String(this.passwordTF.getPassword()));
            this.connectionSettings = new ConnectionSettings(user);
            dispose();
        }else{
            JOptionPane optionPane = new JOptionPane("Invalid e-mail address. Please check your typing", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Error");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            dialog.dispose();
        }
    }

    private void settings() {
        connSettingsDialog = new ConnSettingsDialog(this);
    }

    private void cancel(){
        dispose();
        JOptionPane optionPane = new JOptionPane("User canceled. Exiting", JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Abort");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        System.exit(0);
    }

}
