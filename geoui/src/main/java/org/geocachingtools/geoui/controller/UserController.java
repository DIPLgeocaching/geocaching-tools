/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import org.geocachingtools.geoui.model.Gctusr;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author 20120451
 */
@Named(value = "userCon")
@SessionScoped
public class UserController implements Serializable {

    /**
     * Creates a new instance of UserController
     */
    private Gctusr user;

    public UserController() {
    }

    public boolean isLoggedIn() {
        return user != null;
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
}
