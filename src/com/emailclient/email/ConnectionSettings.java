package com.emailclient.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionSettings {
    Properties prop;
    String propFileName;
    User user;
    Session session;

    public ConnectionSettings(User user){
        this.user = user;
        this.prop = new Properties();
        this.propFileName = "config.properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propFileName)) {
            if (input != null) {
                prop.load(input);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (IOException e) {
            System.out.println("IOException: "+ e.getMessage());
        }

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user.getUsername(), user.getPassword());
            }
        };

        this.session = Session.getInstance(prop, authenticator);

    }

}
