/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Thomas
 */
@Entity
@Table(name = "gctusr")
public class Gctusr implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Embedded
    private OAuthData oauth;
    
    @Column(name = "gcUsername")
    private String gcUsername;
    
    @Column(name = "isAdmin")
    private boolean isAdmin;
    
    @OneToMany(mappedBy = "keyowner")
    private List<Invitekey> invitekeys;
    
    @OneToMany(mappedBy = "cacheowner")
    private List<Cache> caches;

    public Gctusr() {
    }

    public Gctusr(String googleAccount, String gcUsername, boolean isAdmin) {
        this.gcUsername = gcUsername;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OAuthData getOauth() {
        return oauth;
    }

    public void setOauth(OAuthData oauth) {
        this.oauth = oauth;
    }

    public String getGcUsername() {
        return gcUsername;
    }

    public void setGcUsername(String gcUsername) {
        this.gcUsername = gcUsername;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Invitekey> getInvitekeys() {
        return invitekeys;
    }

    public void setInvitekeys(List<Invitekey> invitekeys) {
        this.invitekeys = invitekeys;
    }

    public List<Cache> getCaches() {
        return caches;
    }

    public void setCaches(List<Cache> caches) {
        this.caches = caches;
    }
}
