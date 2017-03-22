/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import java.io.IOException;
import org.geocachingtools.geoui.model.OAuthData;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.geocachingtools.geoui.auth.AuthProvider;
import org.geocachingtools.geoui.auth.UserData;
import org.geocachingtools.geoui.auth.provider.GithubAuthProvider;
import org.geocachingtools.geoui.auth.provider.GoogleAuthProvider;
import org.geocachingtools.geoui.model.Gctusr;
import org.geocachingtools.geoui.model.Invitekey;
import org.geocachingtools.geoui.util.Dao;

/**
 *
 * @author 20120451
 */
@Named(value = "userCon")
@SessionScoped
public class UserController implements Serializable {

    /**
     * TODO: rapi anschaffen das er das sch�ner l�sen soll anstatt �berall dao
     * objekte zu erzeugen.
     */
    private Dao dao;

    /**
     *
     */
    private String key;

    /**
     * The persistent part of the user (id, provider, access-token,
     * invite-key-validation)
     */
    private Gctusr user;
    /**
     * The transient part of the user (name, profile-pic, email). All things
     * which can change from one day to another. These informations are saved on
     * the corresponding providers server.
     */
    private UserData userdata;

    /**
     * The provider which is currently used (TODO: �ndern so das er nicht mehr
     * �ber mehrere requests hier gemerkt werden muss)
     */
    private AuthProvider provider;

    @PostConstruct
    public void init() {
        dao = new Dao();
    }

    public void initAuth(String name) throws OAuthSystemException, IOException {
        switch (name) {
            case "google":
                provider = new GoogleAuthProvider();
                break;
            case "github":
                provider = new GithubAuthProvider();
                break;
            default:
                provider = null;
                break;
        }
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        OAuthClientRequest request = provider.buildAuthRequest();
        externalContext.redirect(request.getLocationUri());
    }

    public void finishAuth(HttpServletRequest origin, HttpServletResponse response) throws OAuthSystemException, OAuthProblemException {
        String code = origin.getParameter("code");
        if (code == null || code.isEmpty()) {
            return;
        }
        OAuthJSONAccessTokenResponse token = this.provider.requestToken(provider.buildTokenRequestByCode(code));
        this.userdata = provider.loadUserData(token.getAccessToken());
        this.user = dao.getUserByOAuthData(userdata.getId(), provider.getClass().getCanonicalName());
        if (this.user == null) {
            this.user = new Gctusr("", userdata.getName(), false);
            this.user.setOauth(new OAuthData(userdata.getId(), provider.getClass().getCanonicalName()));
        }
        this.user.getOauth().setAccessToken(token.getAccessToken());
        this.user.getOauth().setRefreshToken(token.getRefreshToken());
        this.dao.saveOrUpdateGctusr(user);

    }

    public void activateUser() {
        if (!this.dao.activateUser(this.user, this.key)) {
            FacesContext.getCurrentInstance().addMessage("keyform:key", new FacesMessage("Ung�ltiger Key"));
        }
    }

    public void logoutRequest() {
        this.user = null;
        this.userdata = null;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public boolean isActivated() {
        return user != null && user.isActivated();
    }

    public Gctusr getGctusr() {
        return user;
    }
    public OAuthData getUser() {
        return user != null ? user.getOauth() : null;
    }

    public UserData getUserdata() {
        return userdata;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
