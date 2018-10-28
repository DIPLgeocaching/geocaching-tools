/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This Class represents the User that can log into the checker and register
 * caches.
 *
 * @author Thomas
 */
@Entity
@Table(name = "gctuser")
public class Gctuser implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//a uniqe id for eache user

    @Column(name = "googleAccount")
    private String googleAccount;//The e-mail address from a user

    @Column(name = "gcUsername")
    private String gcUsername;//The username the user wants to have

    @Column(name = "isAdmin")
    private boolean isAdmin;//A flag that is set to true when the user is admin

    @OneToMany(mappedBy = "keyowner", fetch = FetchType.EAGER)
    private List<Invitekey> invitekeys;//all invtekey the user created

    @OneToMany(mappedBy = "cacheowner")
    private List<Cache> caches;//all caches a user had registered

    @Embedded
    private OAuthData oauth;

    @OneToOne(mappedBy = "usedby")
    Invitekey usedkey;

    /*
    Constructor
     */
    public Gctuser() {
        this("Unkown", "Unkown", false);
    }

    public Gctuser(String googleAccount, String gcUsername, boolean isAdmin) {
        this.googleAccount = googleAccount;
        this.gcUsername = gcUsername;
        this.isAdmin = isAdmin;
        invitekeys = new ArrayList<>();
    }

    /*
    Getter and Setter
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(String googleAccount) {
        this.googleAccount = googleAccount;
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

    public OAuthData getOauth() {
        return oauth;
    }

    public void setOauth(OAuthData oauth) {
        this.oauth = oauth;
    }

    public Invitekey getUsedkey() {
        return usedkey;
    }

    public void setUsedkey(Invitekey usedkey) {
        this.usedkey = usedkey;
    }

    public boolean isActivated() {
        return getUsedkey() != null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Gctuser other = (Gctuser) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
