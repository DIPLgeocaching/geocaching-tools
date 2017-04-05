/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.models;

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
 * This class represents a subkoordinate from a cache.
 * A cache can have many subkoodintes but a subkoordinate needs to refer to only one Cache.
 * @author Thomas
 */
@Entity
@Table(name = "childwaypointold")
public class ChildwaypointOld implements Serializable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//A uinqe id for eache subkoordinate/childwaypoint
        
    @Column(name = "text")
    private String text;//The hint that is shown when the cachesolver inserts the coordinates
    
    @Column(name = "cooridnateX")
    private String cooridnateX;//Latitude from the subcoordinate
    
    @Column(name = "cooridnateY")
    private String cooridnateY;//Longitude from the subcoordinate
    
    @ManyToOne(optional=false, cascade = CascadeType.ALL)
    @JoinColumn(name = "child_parent_id")
    private CacheOld parentCache;

    /*
    Constructor
     */
    
    public ChildwaypointOld() {
    }

    public ChildwaypointOld(String text, String cooridnateX, String cooridnateY, CacheOld parentCache) {
        this.text = text;
        this.cooridnateX = cooridnateX;
        this.cooridnateY = cooridnateY;
        this.parentCache = parentCache;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCooridnateX() {
        return cooridnateX;
    }

    public void setCooridnateX(String cooridnateX) {
        this.cooridnateX = cooridnateX;
    }

    public String getCooridnateY() {
        return cooridnateY;
    }

    public void setCooridnateY(String cooridnateY) {
        this.cooridnateY = cooridnateY;
    }

    public CacheOld getParentCache() {
        return parentCache;
    }

    public void setParentCache(CacheOld parentCache) {
        this.parentCache = parentCache;
    }
}
