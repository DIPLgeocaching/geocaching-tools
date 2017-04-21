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
import java.util.Date;
import java.util.List;
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
import javax.servlet.http.HttpSession;
import org.geocachingtools.geoui.model.Gctuser;
import org.geocachingtools.geoui.model.Invitekey;
import org.geocachingtools.geoui.util.Dao;

/**
 *
 * @author 20120451
 */
@Named(value = "emailCon")
@SessionScoped
public class EmailController implements Serializable {

    /**
     * Creates a new instance of EmailController
     */
    private String messageToSent;
    private String emailAdresseString;
    private Dao dao;
    private Gctuser user;

    @Inject
    private LocaleController localeCon;
    @Inject
    private UserController userCon;

    private final String EMAIL = "informatik.gc@gmail.com";
    private final String PWD = "geocaching1234";

    @PostConstruct
    public void init() {
        messageToSent = "";
        emailAdresseString = "";
        HttpSession s = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        dao = (Dao) s.getAttribute("dao");
        user = userCon.getGctusr();
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

    public Gctuser getUser() {
        return user;
    }

    public void setUser(Gctuser user) {
        this.user = user;
    }

    public List<Invitekey> getInviteKeys() {
        List<Invitekey> keys = new ArrayList<>();
        for (Invitekey key : dao.getAllInvitekeys()) {
            try {
                if (key.getKeyowner().equals(user)) {
                    keys.add(key);
                }
            } catch (NullPointerException ex) {
            }
        }
        return keys;
    }

    public void sendMailTLS() {
        System.out.println("Sending mail to " + emailAdresseString);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PWD);
            }
        });

        try {
            for (String s : Arrays.asList(emailAdresseString.split(","))) {
                String key = createInviteKey();
                Message message = new MimeMessage(session);
                messageToSent += "<br/>------------------------------------------------------------<br/>"
                        + "<span style=\"font-weight: bold;\">Invite Key</span><br/>"
                        + key;

                message.setFrom(new InternetAddress("informatik.gc@gmail.com"));
                message.setSubject("Invitation to Geocaching Tools!");
                message.setContent(messageToSent, "text/html");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(s));
                Transport.send(message);
                //Save in DB
                Invitekey invitekey = new Invitekey(key, user);
                invitekey.setEmail(emailAdresseString);
                invitekey.setDate(new Date());
                dao.saveInvitekey(invitekey);
                System.out.println("Email Successfull sent");
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(localeCon.getI18n("emailSent")));
            emailAdresseString = "";
            messageToSent = "";
        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(localeCon.getI18n("emailError")));
            messageToSent = "";
            System.out.println("Fehler im EmailCon sendTLS");
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

    public String getEMAIL() {
        return EMAIL;
    }
}
