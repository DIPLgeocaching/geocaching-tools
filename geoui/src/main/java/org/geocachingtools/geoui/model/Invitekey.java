/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Schule
 */
@Entity
@Table(name = "invitekey")
public class Invitekey implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "invkey")
    private String invkey;
    
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "invkey_user")
    private Gctusr keyowner;

    public Invitekey() {
    }

    public Invitekey(String invkey, Gctusr keyowner) {
        this.invkey = invkey;
        this.keyowner = keyowner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvkey() {
        return invkey;
    }

    public void setInvkey(String invkey) {
        this.invkey = invkey;
    }

    public Gctusr getKeyowner() {
        return keyowner;
    }

    public void setKeyowner(Gctusr keyowner) {
        this.keyowner = keyowner;
    }
}
