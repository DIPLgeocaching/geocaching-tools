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
@Table(name = "childwaypoint")
public class Childwaypoint implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    @Column(name = "text")
    private String text;
    
    @Column(name = "cooridnateX")
    private double cooridnateX;
    
    @Column(name = "cooridnateY")
    private double cooridnateY;
    
    @ManyToOne(optional=false, cascade = CascadeType.ALL)
    @JoinColumn(name = "child_parent_id")
    private Cache parentCache;

    public Childwaypoint() {
    }

    public Childwaypoint(String text, double cooridnateX, double cooridnateY, Cache parentCache) {
        this.text = text;
        this.cooridnateX = cooridnateX;
        this.cooridnateY = cooridnateY;
        this.parentCache = parentCache;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getCooridnateX() {
        return cooridnateX;
    }

    public void setCooridnateX(double cooridnateX) {
        this.cooridnateX = cooridnateX;
    }

    public double getCooridnateY() {
        return cooridnateY;
    }

    public void setCooridnateY(double cooridnateY) {
        this.cooridnateY = cooridnateY;
    }

    public Cache getParentCache() {
        return parentCache;
    }

    public void setParentCache(Cache parentCache) {
        this.parentCache = parentCache;
    }
}
