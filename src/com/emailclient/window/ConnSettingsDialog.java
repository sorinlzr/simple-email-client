package com.emailclient.window;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnSettingsDialog extends JDialog{

    private JButton okButton;
    private JButton cancelButton;

    private JLabel hostLabel;
    private JLabel portLabel;
    private JLabel authLabel;
    private JLabel starttlsLabel;

    private JTextField hostTF;
    private JTextField portTF;
    private JTextField authTF;
    private JTextField starttlsTF;

    public ConnSettingsDialog(JDialog parent){
        super(parent, "Connection settings", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.insets = new Insets(5, 7, 5, 7);
        cs.fill = GridBagConstraints.HORIZONTAL;

        hostLabel = new JLabel("Host: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(hostLabel, cs);

        hostTF = new JTextField();
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(hostTF, cs);

        portLabel = new JLabel("Port: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(portLabel, cs);

        portTF = new JTextField();
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(portTF, cs);

        authLabel = new JLabel("Auth: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(authLabel, cs);

        authTF = new JTextField();
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(authTF, cs);

        starttlsLabel = new JLabel("STARTTLS: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(starttlsLabel, cs);

        starttlsTF = new JTextField();
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(starttlsTF, cs);

        okButton = new JButton("OK");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(okButton, cs);

        cancelButton = new JButton("Cancel");
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(cancelButton, cs);

        Properties prop = new Properties();
        String propFileName = "config.properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            if (input != null) {
                prop.load(input);
            } else {
                throw new FileNotFoundException("Property File '" + propFileName + "' not found in the classpath");
            }
        } catch (IOException e) {
            System.out.println("IOException: "+ e.getMessage());
        }

        hostTF.setText(prop.getProperty("mail.smtp.host"));
        portTF.setText(prop.getProperty("mail.smtp.port"));
        authTF.setText(prop.getProperty("mail.smtp.auth"));
        starttlsTF.setText(prop.getProperty("mail.smtp.starttls.enable"));

        okButton.addActionListener(e -> {
            prop.setProperty("mail.smtp.host", hostTF.getText());
            prop.setProperty("mail.smtp.port", portTF.getText());
            prop.setProperty("mail.smtp.auth", authTF.getText());
            prop.setProperty("mail.smtp.starttls.enable", starttlsTF.getText());
            try {
                prop.store(new FileOutputStream("src/" + propFileName), null);
            } catch (IOException ex) {
                System.out.println("IOException: "+ ex.getMessage());
            }
            dispose();
        });

        cancelButton.addActionListener(e-> dispose());

        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);

    }
}
