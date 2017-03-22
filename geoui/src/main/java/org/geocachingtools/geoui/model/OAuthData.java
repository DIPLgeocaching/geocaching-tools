/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 *
 * @author 20120451
 */
@Embeddable
public class OAuthData implements Serializable {

    //These two variables identify one user.
    private String id;
    private String provider;

    //The tokens
    private String refreshToken;
    private String accessToken;

    private boolean activated = false;

    public OAuthData() {
    }
    
    public OAuthData(String id, String provider) {
        this.id = id;
        this.provider = provider;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getId() {
        return id;
    }

    public String getProvider() {
        return provider;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

}
