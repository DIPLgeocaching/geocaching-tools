/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author awesome
 */
@Named(value = "localeCon")
@SessionScoped
public class LocaleController implements Serializable {

    private Locale locale;
    
    @PostConstruct
    public void init() {
        setLocaleEN();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        FacesContext.getCurrentInstance()
                .getViewRoot().setLocale(locale);
    }

    public void setLocaleDE() {
        setLocale(Locale.GERMAN);
    }

    public void setLocaleEN() {
        setLocale(Locale.ENGLISH);
    }
}
