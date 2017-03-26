/**
 * The MIT License (MIT)
 *
 * Copyright (c) [2016] [Geocaching-Tools: Stefan Kurzbauer, Jakob Geringer,
 * Thomas Rapberger, Lukas Wallenboeck, Simon Lehner-Dittenberger]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.geocachingtools.geoui.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.geocachingtools.geoui.model.Invitekey;
import org.geocachingtools.geoui.util.Dao;

/**
 *
 * @author 20120451
 */
@Named(value = "emailController")
@SessionScoped
public class EmailController implements Serializable {

    /**
     * Creates a new instance of EmailController
     */
    private String messageToSent;
    private String emailAdresseString;
    private Dao dao;

    @Inject
    private UserController controller;

    @PostConstruct
    public void init() {
        messageToSent = "";
        emailAdresseString = "";
        dao = new Dao();
    }

    public String getMessageToSent() {
        return messageToSent;
    }

    public void setMessageToSent(String messageToSent) {
        this.messageToSent = messageToSent;
    }

    public String getEmailAdresseString() {
        return emailAdresseString;
    }

    public void setEmailAdresseString(String emailAdresseString) {
        this.emailAdresseString = emailAdresseString;
    }

    public void sendMailTLS() {
        final String username = "informatik.gc@gmail.com";
        final String password = "geocaching1234";

        System.out.println("Sending mail to " + emailAdresseString);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            for (String s : Arrays.asList(emailAdresseString.split(","))) {
                String key = createInviteKey();
                Message message = new MimeMessage(session);
                messageToSent += "<br/>------------------------------------------------------------<br/>"
                        + "<span style=\"font-weight: bold;\">Invite Key</span><br/>"
                        + key;
                //TODO: save in DB
                message.setFrom(new InternetAddress("informatik.gc@gmail.com"));
                message.setSubject("Invitation to Geocaching Tools!");
                message.setContent(messageToSent, "text/html");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(s));
                Transport.send(message);
                dao.saveInvitekey(new Invitekey(key, controller.getGctusr()));
                System.out.println("Email Successfull sent");
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email erfolgreich gesendet"));
            emailAdresseString = "";
            messageToSent = "";
        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email senden gescheitert, Schule: Port nicht offen, Email Adresse nicht gefunden"));
            messageToSent = "";
            System.out.println("Fehler im EmailCon sendTLS");
            // throw new RuntimeException(e);
        }
    }

    private String createInviteKey() {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        String key = "";
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            key += chars[random.nextInt(chars.length)];
        }
        return key;
    }
}
