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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    private List<String> emailAdress;

    @PostConstruct
    public void init() {
        messageToSent = "";
        emailAdresseString = "";
        emailAdress = new ArrayList<>();
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

    public List<String> getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(List<String> emailAdress) {
        this.emailAdress = emailAdress;
    }

    public void sendMailTLS() {
        final String username = "informatik.gc@gmail.com";
        final String password = "geocaching1234";

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

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("informatik.gc@gmail.com"));
            emailAdress = Arrays.asList(emailAdresseString.split(","));

            for (String s : emailAdress) {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(s));

                message.setSubject("Invitation to Geocaching Tools!");
                message.setText(messageToSent);

                Transport.send(message);

                System.out.println("Email Successfull sent");
            }
            FacesContext.getCurrentInstance().addMessage("Email", new FacesMessage("Email erfolgreich gesendet"));
            emailAdresseString = "";
            messageToSent = "";
        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage("Email", new FacesMessage("Email senden gescheitert, Schule: Port nicht offen, Email Adresse nicht gefunden"));

            throw new RuntimeException(e);
        }
    }

}