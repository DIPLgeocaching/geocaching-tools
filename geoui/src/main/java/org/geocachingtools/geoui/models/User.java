/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.models;

import java.io.Serializable;

/**
 *
 * @author 20120451
 */
public class User implements Serializable {

    //These two variables identify one user.
    private String id;
    private String provider;

    //The tokens
    private String refreshToken;
    private String accessToken;

    public User(String id, String provider) {
        this.id = id;
        this.provider = provider;
    }

    public User(String id, String provider, String refreshToken) {
        this.id = id;
        this.provider = provider;
        this.refreshToken = refreshToken;
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

}
