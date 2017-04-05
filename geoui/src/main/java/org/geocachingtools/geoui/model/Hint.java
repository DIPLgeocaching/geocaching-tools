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
 * @author Thomas
 */
@Entity
@Table(name = "hint")
public class Hint implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attempts")
    private int attempts;

    @Column(name = "helptest")
    private String helptext;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "hint_cache_id")
    private Cache cachehint;

    public Hint() {
    }

    public Hint(int attempts, String helptext, Cache cachehint) {
        this.attempts = attempts;
        this.helptext = helptext;
        this.cachehint = cachehint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getHelptext() {
        return helptext;
    }

    public void setHelptext(String helptext) {
        this.helptext = helptext;
    }

    public Cache getCachehint() {
        return cachehint;
    }

    public void setCachehint(Cache cachehint) {
        this.cachehint = cachehint;
    }
}
