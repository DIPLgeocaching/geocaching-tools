/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.controller;

import org.geocachingtools.geoui.models.User;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

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
    private User user;

    public UserController() {
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void doAuth() throws OAuthSystemException {
        OAuthClientRequest request = OAuthClientRequest
                .authorizationProvider(OAuthProviderType.GOOGLE)
                .setClientId("your-facebook-application-client-id")
                .setRedirectURI("http://www.example.com/redirect")
                .buildQueryMessage();
    }

}
