/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * This class represents a cachsolvers input.
 * It is needed for the statistic. When a cachesolver checks coordinates there is
 * a new "SessionAttempts" saved. A SessionAttempts assigned to one cache and a
 * cache can have 0-n SessionAttempts.
 * @author Thomas
 */
@Entity
@Table(name = "sessionattempts")
public class SessionAttempt implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//Uniqe id (nessesary for table in DB)
    
    @Column(name = "userinputX")
    private double userinputX;//latidude from the input
    
    @Column(name = "userinputY")
    private double userinputY;//longitude from the input
            
    @Column(name = "solved")
    private boolean solved;//Flag to show if the cache got solved with these coordinates
    
    @Column(name = "timepstamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date timestamp;//Date when the check accoured
    
    @Column(name = "ipaddress")
    private String ipaddress;//Ipaddress to diversify the cachesolves
    
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "parentcache")
    private Cache parentcache;//The cache the attempt is assigned to

    /*
    Construcotr
    */
    
    public SessionAttempt() {
    }

    public SessionAttempt(double userinputX, double userinputY, boolean solved, Date timestamp, String ipaddress, Cache parentcache) {
        this.userinputX = userinputX;
        this.userinputY = userinputY;
        this.solved = solved;
        this.timestamp = timestamp;
        this.ipaddress = ipaddress;
        this.parentcache = parentcache;
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

    public double getUserinputX() {
        return userinputX;
    }

    public void setUserinputX(double userinputX) {
        this.userinputX = userinputX;
    }

    public double getUserinputY() {
        return userinputY;
    }

    public void setUserinputY(double userinputY) {
        this.userinputY = userinputY;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Cache getParentcache() {
        return parentcache;
    }

    public void setParentcache(Cache parentcache) {
        this.parentcache = parentcache;
    }

    @Override
    public String toString() {
        return "SessionAttempts{" + "id=" + id + ", userinputX=" + userinputX + ", userinputY=" + userinputY + ", solved=" + solved + ", timestamp=" + timestamp + ", ipaddress=" + ipaddress + ", cache=" + parentcache + '}';
    }
}
