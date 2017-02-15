/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import org.geocachingtools.geoui.model.Gctusr;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.geocachingtools.geoui.util.Dao;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author 20120451
 */
@Named(value = "userCon")
@SessionScoped
public class UserController implements Serializable {

    private final String CLIENT_ID = "282017452229-165f3htsp7os10s9bk4689lunqm38euj.apps.googleusercontent.com";
    @Inject
    private LocaleController localCon;

    private Gctusr user;
    private boolean loggedIn = false;

    @PostConstruct
    public void init() {
        user = null;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Gctusr getUser() {
        return user;
    }

    public void setUser(Gctusr user) {
        this.user = user;
    }

    public String login() {

        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        Dao dao = (Dao) sc.getAttribute("dao");
        String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_token");

        try {
            // Set up the HTTP transport and JSON factory
            HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            // (Receive idTokenString by HTTPS POST)
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = payload.getEmailVerified();
                String name = (String) payload.get("name");
                String locale = (String) payload.get("locale");

                //Testausgabe
                System.out.println(email);

                if (emailVerified) {
                    Gctusr usr;
                    if ((usr = dao.getCertianGctusr(email)) != null) {
                        user = usr;
                        loggedIn = true;
                        if ("de".equals(locale)) {
                            localCon.setLocaleDE();
                        } else {
                            localCon.setLocaleEN();
                        }
                    } else {
                        System.out.println("--------");
                        return "error";
                    }
                }
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String logout() {
        System.out.println("Logout");
        loggedIn = false;
        return "home";
    }
}
