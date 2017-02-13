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
    @PostConstruct
    public void init() {

    }

    public EmailController() {
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
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("jakobgeringer@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");
            FacesContext.getCurrentInstance().addMessage("Email", new FacesMessage("Email erfolgreich gesendet"));

        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage("Email", new FacesMessage("Email gescheitert, Schule: Port nicht offen"));

            //throw new RuntimeException(e);
        }
    }

//    public void sendMailSSL() {
//        String to = "jakobgeringer@gmail.com";
//        String msg = "test test";
//        String from = "informatik.gc@gmail.com";
//        String password = "geocaching1234";
//        String subject = "You have been invited to Geocaching Tools!";
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//
//        Session session = Session.getDefaultInstance(props,
//                new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(from, password);
//            }
//        });
//
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject(subject);
//            message.setText(msg);
//
//            Transport.send(message);
//            System.out.println("Message successfully Sent");
//
//        } catch (MessagingException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

}
