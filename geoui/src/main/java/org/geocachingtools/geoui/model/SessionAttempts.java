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
 *
 * @author Thomas
 */
@Entity
@Table(name = "sessionattempts")
public class SessionAttempts implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "attemptsQuantity")
    private int attemptsQuantity;
    
    @Column(name = "firstTry")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date firstTry;
    
    @Column(name = "solved")
    private boolean solved;
    
    @Column(name = "solvedOn")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date solvedOn;
    
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "ses_cache")
    private Cache cache;

    public SessionAttempts() {
    }

    public SessionAttempts(int attemptsQuantity, Date firstTry, boolean solved, Date solvedOn, Cache cache) {
        this.attemptsQuantity = attemptsQuantity;
        this.firstTry = firstTry;
        this.solved = solved;
        this.solvedOn = solvedOn;
        this.cache = cache;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAttemptsQuantity() {
        return attemptsQuantity;
    }

    public void setAttemptsQuantity(int attemptsQuantity) {
        this.attemptsQuantity = attemptsQuantity;
    }

    public Date getFirstTry() {
        return firstTry;
    }

    public void setFirstTry(Date firstTry) {
        this.firstTry = firstTry;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public Date getSolvedOn() {
        return solvedOn;
    }

    public void setSolvedOn(Date solvedOn) {
        this.solvedOn = solvedOn;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
