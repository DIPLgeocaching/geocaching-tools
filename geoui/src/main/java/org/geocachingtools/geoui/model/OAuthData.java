/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 *
 * @author 20120451
 */
@Embeddable
public class OAuthData implements Serializable {

    //These two variables identify one user.
    @Column
    private String oauthid;
    @Column
    private String provider;

    //The tokens
    @Column
    private String refreshToken;
    @Column
    private String accessToken;
    
    @Column
    private boolean activated = false;

    public OAuthData() {
    }

    public OAuthData(String id, String provider) {
        this.oauthid = id;
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

    public String getOauthid() {
        return oauthid;
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
