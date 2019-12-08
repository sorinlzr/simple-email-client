package com.emailclient.email;

import com.emailclient.email.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlainTextEmail {
    private ConnectionSettings settings;
    private String toAddress;
    private String subject;
    private String message;
    private Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,7}");

    public PlainTextEmail(ConnectionSettings settings, String toAddress, String subject, String message) {
        this.settings = settings;
        this.toAddress = toAddress;
        this.subject = subject;
        this.message = message;
    }

    public void sendEmail() throws MessagingException {
        Message email = new MimeMessage(this.settings.session);

        email.setFrom(new InternetAddress(this.settings.user.getUsername()));
        InternetAddress[] toAddresses = {new InternetAddress(this.toAddress)};
        email.setRecipients(Message.RecipientType.TO, toAddresses);
        email.setSubject(this.subject);
        email.setSentDate(new Date());
        email.setText(this.message);

        if(!this.isValidEmail(toAddress)){
            throw new MessagingException("Invalid Address");
        }else{
            Transport.send(email);
            System.out.println("E-mail sent successfully!");
        }

    }

    private boolean isValidEmail(String email) {
        Matcher mat = pattern.matcher(email);
        return mat.matches();
    }
}
